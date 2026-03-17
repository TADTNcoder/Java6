package com.lab1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login/form")
    public String form(Model model) {
        model.addAttribute("message", "Vui lòng đăng nhập");
        return "login";
    }

    @GetMapping("/login/success")
    public String success(Model model) {
        model.addAttribute("message", "Đăng nhập thành công");
        return "login";
    }

    @GetMapping("/login/failure")
    public String failure(Model model) {
        model.addAttribute("message", "Đăng nhập thất bại");
        return "login";
    }

    @GetMapping("/login/exit")
    public String exit(Model model) {
        model.addAttribute("message", "Đăng xuất thành công");
        return "login";
    }
}