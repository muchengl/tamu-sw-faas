package com.tamu.faas.controller;


import com.tamu.faas.dao.UserRepository;
import com.tamu.faas.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user")
    public String VerifyUser(String username, String password, HttpServletRequest request){
        System.out.println("VerifyUser:"+username+" "+password);

        User user=userRepository.findByEmail(username);

        if(user==null){
            return "redirect:/login";
        }
        System.out.println(user.toString());

        if(user.getPassword().equals(password)) {
            request.getSession().setAttribute("username",user.getUsername());
            request.getSession().setAttribute("userID",user.getUserId());
            return "redirect:/index";
        }
        else  return "redirect:/login";
    }
}
