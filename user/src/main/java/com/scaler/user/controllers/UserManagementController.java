package com.scaler.user.controllers;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-management")
public class UserManagementController {

    @PostMapping("/register")
    public String createUser(){
        return "User Created";
    }

    @GetMapping("/get-user")
    public String getUser(){
        return "User Fetched";
    }

    @GetMapping("/get-user/{id}")
    public String getUserById(@PathVariable String id){
        return "User  with id: "+id+" Fetched";
    }

    @PutMapping("/update-user/{id}")
    public String updateUser(@PathVariable String id){
        return "User Updated with id: "+id+" Fetched";
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable String id){
        return "User Deleted with id: "+id+" Fetched";
    }
}
