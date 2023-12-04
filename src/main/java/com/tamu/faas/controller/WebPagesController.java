package com.tamu.faas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebPagesController {


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String home(){
        return "Index";
    }

    @RequestMapping("/home")
    public String index(){
        return "Homepage";
    }

    @RequestMapping("/funcpage")
    public String func(){
        return "Funcs";
    }

    @RequestMapping("/taskspage")
    public String tasks(){
        return "Tasks";
    }

    @RequestMapping("/debug")
    public String debug(String funcID, Model model){
        System.out.println(funcID);
        model.addAttribute("fid", funcID);
        return "Debug";
    }

    @RequestMapping("/data")
    public String data(){
        return "Data";
    }

}
