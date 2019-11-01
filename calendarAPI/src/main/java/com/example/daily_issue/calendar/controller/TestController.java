package com.example.daily_issue.calendar.controller;

import com.example.daily_issue.calendar.security.service.SecurityService;
import com.example.daily_issue.login.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("calendarTest")
public class TestController {

    @Autowired
    SecurityService securityService;

    @GetMapping("user")
    public Optional<UserDetails> user()
    {
        Optional<UserDetails> user = securityService.getPrincipal();

        return user;
    }


    @GetMapping("account")
    public Optional<Account> account()
    {
        return Optional.ofNullable(securityService.getAccount());
    }

}
