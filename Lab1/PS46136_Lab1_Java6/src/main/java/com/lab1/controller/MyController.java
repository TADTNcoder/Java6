package com.lab1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Trang chủ");
        return "page";
    }

    @RequestMapping("/poly/url0")
    public String url0(Model model) {
        model.addAttribute("message", "Đây là URL0");
        return "page";
    }

    @RequestMapping("/poly/url1")
    public String url1(Model model) {
        model.addAttribute("message", "Đây là URL1");
        return "page";
    }

    @RequestMapping("/poly/url2")
    public String url2(Model model) {
        model.addAttribute("message", "Đây là URL2");
        return "page";
    }

    @RequestMapping("/poly/url3")
    public String url3(Model model) {
        model.addAttribute("message", "Đây là URL3");
        return "page";
    }

    @RequestMapping("/poly/url4")
    public String url4(Model model) {
        model.addAttribute("message", "Đây là URL4");
        return "page";
    }
}