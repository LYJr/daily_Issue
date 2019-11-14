package com.example.daily_issue.login.controller;

import com.example.daily_issue.login.domain.Member;
import com.example.daily_issue.login.util.HttpSessionUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, HttpSession session) {

        String a = "test";

        if(HttpSessionUtil.isLoginUser(session)){
            a = HttpSessionUtil.getSessiondUser(session).getName();
        }

        model.addAttribute("message", a);
        return "/index";
    }

    @GetMapping("/join")
    public String join() {
        return "/join";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
