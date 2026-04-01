package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "@/ => home()");
        return "page";
    }

    @RequestMapping("/poly/url0")
    public String url0(Model model) {
        model.addAttribute("message", "@/poly/url0 => url0()");
        return "page";
    }

    @RequestMapping("/poly/url1")
    public String url1(Model model) {
        model.addAttribute("message", "@/poly/url1 => url1()");
        return "page";
    }

    @RequestMapping("/poly/url2")
    public String url2(Model model) {
        model.addAttribute("message", "@/poly/url2 => url2()");
        return "page";
    }

    @RequestMapping("/poly/url3")
    public String url3(Model model) {
        model.addAttribute("message", "@/poly/url3 => url3()");
        return "page";
    }

    @RequestMapping("/poly/url4")
    public String url4(Model model) {
        model.addAttribute("message", "@/poly/url4 => url4()");
        return "page";
    }
}