package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Lab4Controller {

    @GetMapping("/rest-client")
    public String restClient() {
        return "rest-client";
    }

    @GetMapping("/student-crud")
    public String studentCrud() {
        return "student-crud";
    }
}