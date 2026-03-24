package com.lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login/form")
    public String login() {
        return "login";
    }

    @GetMapping("/login/success")
    public String success() {
        return "success";
    }

    @GetMapping("/login/failure")
    public String failure() {
        return "failure";
    }

    @GetMapping("/login/exit")
    public String exit() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String denied() {
        return "access-denied";
    }

    @GetMapping("/poly/url1")
    public String url1() {
        return "url1";
    }

    @GetMapping("/poly/url2")
    public String url2() {
        return "url2";
    }

    @GetMapping("/poly/url3")
    public String url3() {
        return "url3";
    }

    @GetMapping("/poly/url4")
    public String url4() {
        return "url4";
    }
}