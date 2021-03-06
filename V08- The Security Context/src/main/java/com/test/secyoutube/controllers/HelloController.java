package com.test.secyoutube.controllers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class HelloController {
//    @GetMapping("/hello")
//    public String hello(Authentication authentication) {
//        return "hello... ! " + authentication.getName();
//    }

    @GetMapping("/hello")
    //@Async
    public String hello() {
        Runnable r = () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication);
        };
        ExecutorService service = Executors.newSingleThreadExecutor();
        //DelegatingSecurityContextRunnable dr = new DelegatingSecurityContextRunnable(r);
        DelegatingSecurityContextExecutorService dService = new DelegatingSecurityContextExecutorService(service);
        dService.submit(r);
        dService.shutdown();
        return "hello... ! ";
    }

}
