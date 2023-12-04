package com.tamu.faas.controller;

import com.tamu.faas.dao.FuncRepository;
import com.tamu.faas.pojo.Function;
import com.tamu.faas.utils.HdfsUtil;
import org.apache.zookeeper.server.SessionTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FuncController {

    @Autowired
    FuncRepository funcRepository;

    @RequestMapping("/func/upload")
    public String funcUpload(@RequestParam(value = "file") MultipartFile file, String filename, String description, HttpServletRequest request){
        try {

            String uid= (String)request.getSession().getAttribute("userID");
            String path = "/color-faas"+"/"+uid+"/"+filename;
            System.out.println("file >> "+path);
            HdfsUtil.uploadFileToHDFS(file,path);

            // WriteDB
            Function function=new Function();
            function.setFuncId(UUID.randomUUID().toString());
            function.setFuncPath(path);
            function.setFuncName(filename);
            function.setUserId(uid);
            function.setFuncExplanation(description);
            funcRepository.save(function);

        } catch (IOException e) {
            e.printStackTrace();
            return "dataStoreFail";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Funcs";
    }
}
