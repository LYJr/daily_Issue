package com.example.daily_issue.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "index"})
    public String index(Model model)
    {
        model.addAttribute("hello", "Hello");
        model.addAttribute("user", "Resian");

        return "index";
    }
}
