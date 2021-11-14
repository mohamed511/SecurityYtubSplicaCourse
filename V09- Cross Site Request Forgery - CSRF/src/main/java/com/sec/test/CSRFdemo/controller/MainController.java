package com.sec.test.CSRFdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @GetMapping
    public String main() {
        System.out.println("=========> in main method");
        return "main.html";
    }

    @PostMapping("/change")
    public String doAny() {
        System.out.println("=========> in post method (doAny)");
        return "main.html";
    }
}
