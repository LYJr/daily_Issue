package com.example.daily_issue.login.controller;

import com.example.daily_issue.login.dto.UserDto;
import com.example.daily_issue.login.service.JoinService;
import com.example.daily_issue.login.service.LoginService;
import com.example.daily_issue.login.util.HttpSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JoinService joinService;

    @PostMapping("/login")
    public String login(UserDto userDto, HttpSession httpSession) {

        if (loginService.login(userDto)) {
            httpSession.setAttribute(HttpSessionUtil.USER_SESSION_KEY, userDto.toUser());
        }
        return "redirect:/index";
    }

    @PostMapping("/join")
    public String join(UserDto userDto) {
        joinService.add(userDto.toUser());
        return "redirect:/";
    }

}
