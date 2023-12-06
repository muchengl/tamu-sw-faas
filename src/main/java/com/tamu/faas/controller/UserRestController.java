package com.tamu.faas.controller;


import com.tamu.faas.dao.TaskRepository;
import com.tamu.faas.dao.UserRepository;
import com.tamu.faas.pojo.Task;
import com.tamu.faas.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @RequestMapping("/user/info")
    public User user(HttpServletRequest request){


        String uid= (String)request.getSession().getAttribute("userID");
        User user=userRepository.findByUserId(uid);

        return user;
    }

    @RequestMapping("/add/user")
    public String adduser(String username,String email, String password){
        System.out.println(username+" "+email+" "+password);

        User user=new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        return "ok";
    }

    @RequestMapping("/user/tasks")
    public List<Task> tasks(HttpServletRequest request){
        String uid= (String)request.getSession().getAttribute("userID");
        List<Task> list=taskRepository.findByUserID(uid);
        return list;
    }

    @RequestMapping("/login/user")
    public User VerifyUser(String username, String password, HttpServletRequest request){
        System.out.println("VerifyUser:"+username+" "+password);

        User user=userRepository.findByEmail(username);

        if(user==null){
            return null;
        }
        System.out.println(user.toString());

        if(user.getPassword().equals(password)) {
            request.getSession().setAttribute("username",user.getUsername());
            request.getSession().setAttribute("userID",user.getUserId());
            return user;
        }
        else  return null;
    }

}
