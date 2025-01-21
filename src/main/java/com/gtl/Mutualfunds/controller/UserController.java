package com.gtl.Mutualfunds.controller;

import com.gtl.Mutualfunds.dto.UserRegistrationDto;
import com.gtl.Mutualfunds.model.User;
import com.gtl.Mutualfunds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;  // Import List class here

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")  // Correctly mapped endpoint
    public ResponseEntity<String> register(@RequestBody UserRegistrationDto userDto) {
        try {
            String result = userService.registerUser(userDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Validation Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get user details by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all users
    @GetMapping("/users")  // This is the correct endpoint for getting all users
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers(); // Service method to fetch all users
            if (!users.isEmpty()) {
                return ResponseEntity.ok(users);  // Return all users with status 200 OK
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // No content found
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Handle any server error
        }
    }
}

