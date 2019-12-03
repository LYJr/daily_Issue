package com.example.daily_issue.login.controller;

import com.example.daily_issue.login.domain.DailyMember;
import com.example.daily_issue.login.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/join")
    public String join(DailyMember member, Model model) {
        memberService.createNew(member);

        return "redirect:/";
    }
}
