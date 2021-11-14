package com.sec.test.CSRFdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @GetMapping
    public String main() {
        System.out.println("=========> in main method");
        return "main.html";
    }

    @PostMapping("/test")
    @ResponseBody
    //@CrossOrigin("*")
    public String test() {
        System.out.println("=========> in post method (test)");
        return "test";
    }
}
