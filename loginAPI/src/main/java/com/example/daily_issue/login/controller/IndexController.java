package com.example.daily_issue.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

//    @GetMapping("/")
//    public String index(Model model, Principal principal) {
//
//        String message = "test";
//
//        if(principal != null){
//            message = principal.getName();
//        }
//
//        model.addAttribute("message", message);
//        return "/index";
//    }

    @GetMapping("/join")
    public String join() {
        return "/join";
    }

//    @GetMapping("/login")
//    public String login() {
//        return "/login";
//    }
}
