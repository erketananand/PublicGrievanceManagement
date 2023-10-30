package com.scaler.user.controllers;

import com.scaler.user.dto.UserDTO;
import com.scaler.user.exceptionsHandler.NotFoundException;
import com.scaler.user.models.User;
import com.scaler.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    Registering the user
//    Method: POST
//    Path: /api/v1/users/register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            // Perform validation and business logic checks if necessary
            if (userDTO == null || userDTO.getUsername() == null || userDTO.getPassword() == null) {
                return ResponseEntity.badRequest().body("Invalid user data");
            }

            // Call the UserService to register the user
            User registeredUser = userService.registerUser(userDTO);

            if (registeredUser != null) {
                return ResponseEntity.ok("User registered successfully");
            } else {
                return ResponseEntity.badRequest().body("User registration failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during user registration");
        }
    }
// Getting all the users
// Method: GET
// Path: /api/v1/users
    @GetMapping("/")
    public ResponseEntity<List<User>> getUser(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

//    Getting the user by id
//    Method: GET
//    Path: /api/v1/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws NotFoundException {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

// Updating the user
// Method: PATCH
// Path: /api/v1/users/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) throws NotFoundException {
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.badRequest().body("User not found");
        }
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok("User updated successfully");
    }

//    Deleting the user
//    Method: DELETE
//    Path: /api/v1/users/{id}
    @DeleteMapping("/{id}")

    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws NotFoundException {
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.badRequest().body("User not found");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
