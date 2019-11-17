package com.example.daily_issue.login.controller;

import com.example.daily_issue.login.dto.MemberDto;
import com.example.daily_issue.login.service.JoinService;
import com.example.daily_issue.login.service.LoginService;
import com.example.daily_issue.login.util.HttpSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Column;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JoinService joinService;

    @PostMapping("/login")
    public String login(MemberDto memberDto, HttpSession httpSession, Model model) {

        if (loginService.login(memberDto)) {
            httpSession.setAttribute(HttpSessionUtil.USER_SESSION_KEY, memberDto.toMember());
        }

        model.addAttribute("message", memberDto.getName() + "loginOK");

        return "redirect:/index";
    }

    @PostMapping("/join")
    public String join(MemberDto memberDto, Model model) {
        joinService.add(memberDto.toMember());

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(HttpSessionUtil.USER_SESSION_KEY);
        return "redirect:/";
    }

}
