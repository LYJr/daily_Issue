package com.example.daily_issue.login.controller;

import com.example.daily_issue.login.domain.Member;
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
//
//    @PostMapping("/login")
//    public String login(MemberDto memberDto, Model model) {
//
//       memberService.loadUserByUsername(memberDto.getUserId());
//
//        model.addAttribute("message", memberDto.getName() + "loginOK");
//
//        return "redirect:/index";
//    }
//
    @PostMapping("/join")
    public String join(Member member, Model model) {
        memberService.add(member);

        return "redirect:/";
    }

//  //  @GetMapping("/logout")
//  //  public String logout(){
////
//   //     return "redirect:/";
//  //  }

}
