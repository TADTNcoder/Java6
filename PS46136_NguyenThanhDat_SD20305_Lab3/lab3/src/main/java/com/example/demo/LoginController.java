package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login/{action}")
    public String login(Model model, @PathVariable("action") String action) {
        switch (action) {
            case "form":
                model.addAttribute("message", "Vui lòng đăng nhập");
                break;
            case "success":
                model.addAttribute("message", "Đăng nhập thành công");
                break;
            case "failure":
                model.addAttribute("message", "Đăng nhập thất bại");
                break;
            case "exit":
                model.addAttribute("message", "Đăng xuất thành công");
                break;
            default:
                model.addAttribute("message", "Vui lòng đăng nhập");
                break;
        }
        return "login";
    }
}