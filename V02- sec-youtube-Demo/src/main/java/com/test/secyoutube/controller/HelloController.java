package com.test.secyoutube.controller;

import com.test.secyoutube.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private JdbcUserDetailsManager userDetailsManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String test() {
        return "hello";
    }

    @PostMapping("/user")
    public void addUser(@RequestBody User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userDetailsManager.createUser(user);
    }
}
