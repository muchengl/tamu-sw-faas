package com.tamu.faas.controller;

import com.alibaba.fastjson.JSON;
import com.tamu.faas.dao.FuncRepository;
import com.tamu.faas.dao.TaskRepository;
import com.tamu.faas.pojo.Function;
import com.tamu.faas.pojo.Task;
import com.tamu.faas.utils.HdfsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
public class FuncRestController {

    @Autowired
    FuncRepository funcRepository;

    @Autowired
    TaskRepository taskRepository;

    @RequestMapping("/funcs")
    public List<Function> getFuncs(HttpServletRequest request){
        String uid= (String)request.getSession().getAttribute("userID");
        return funcRepository.findByUserId(uid);
    }

    @RequestMapping("/update/func")
    public String updateFuncs(String ID,String name, String des){

        System.out.println(ID+" "+name+" "+des);
        Function function=funcRepository.findByFuncId(ID);

        if(function!=null){
            function.setFuncName(name);
            function.setFuncExplanation(des);
            funcRepository.save(function);
        }

        return "ok";
    }

    @RequestMapping("/func/invoke")
    // http://localhost:8080/func/invoke?funcID=2b145ec8-0b52-41b4-a734-482616fc0751&taskInput=hello
    public String invoke(
            String funcID,
            String taskInput,
            HttpServletRequest request){
        System.out.println(funcID+" "+taskInput);
        String uid= (String)request.getSession().getAttribute("userID");

        String tid=UUID.randomUUID().toString();
        // get func info
        Function function=funcRepository.findByFuncId(funcID);

        Task task=new Task();
        task.setFuncName(function.getFuncName());
        task.setTaskFuncPath(function.getFuncPath());
        task.setTaskID(tid);
        task.setTaskInput(taskInput);

        System.out.println(JSON.toJSONString(task));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


        // send req
        String host="http://127.0.0.1:9090/run";
        long startTime = System.currentTimeMillis();

        String out = sendPostRequest(host, JSON.toJSONString(task));

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        task.setOutput(out);
        task.setHost(host);
        task.setUserID(uid);

        LocalDateTime startDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault());
        LocalDateTime endDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(endTime), ZoneId.systemDefault());
        task.setBeginTime(
                startDateTime.format(formatter)
        );
        task.setEndTime(
                endDateTime.format(formatter)
        );


        task.setDuration(String.valueOf(duration));

        taskRepository.save(task);

        return out;
    }

    public String sendPostRequest(String url, String jsonRequestBody) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
