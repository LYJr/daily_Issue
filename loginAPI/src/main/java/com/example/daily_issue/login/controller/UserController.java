package com.example.daily_issue.login.controller;

import com.example.daily_issue.login.domain.User;
import com.example.daily_issue.login.domain.repository.UserRepository;
import com.example.daily_issue.login.dto.UserDto;
import com.example.daily_issue.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequestMapping("/user")
public class UserController {

    @Autowired
    private LoginService loginService;

    @PostMapping ("/login")
    public String login (UserDto userDto) {
        Optional<User> originUser = loginService.findByUserId(userDto.getUserId());

        // TODO: 2019-10-14 변경중
        if(originUser.isPresent()){
            loginService.login(originUser.get(), userDto);
        }

        return "/index";
    }
}
