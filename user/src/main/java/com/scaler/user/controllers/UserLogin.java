package com.scaler.user.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserLogin {

    @GetMapping("/login")
    public String login(){
        return "Login Successful";
    }
}
