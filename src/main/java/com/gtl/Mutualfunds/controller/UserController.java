package com.gtl.Mutualfunds.controller;

import com.gtl.Mutualfunds.dto.LoginDto;
import com.gtl.Mutualfunds.dto.UserRegistrationDto;
import com.gtl.Mutualfunds.model.User;
import com.gtl.Mutualfunds.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRegistrationDto userDto) {
        Map<String, String> response = new HashMap<>();
        try {
            String result = userService.registerUser(userDto);  // Password hashing happens here in the service
            response.put("message", result);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            response.put("error", "Validation Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", "An error occurred: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get user details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                response.put("user", user);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("error", "User not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("error", "An error occurred: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<User> users = userService.getAllUsers();
            if (!users.isEmpty()) {
                response.put("users", users);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "No users found");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            response.put("error", "An error occurred: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        try {
            boolean isAuthenticated = userService.authenticateUser(loginDto);
            if (isAuthenticated) {
                Optional<User> user = userService.getUserByEmail(loginDto.getEmail());
                user.ifPresent(u -> session.setAttribute("user", u)); // Store user object in session
                response.put("message", "Login successful!");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("error", "Invalid email or password");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            response.put("error", "An error occurred: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get current logged-in user
    @GetMapping("/currentUser")
    public ResponseEntity<Map<String, Object>> getCurrentUser(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            response.put("user", user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("error", "Unauthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    // Logout user
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        Map<String, String> response = new HashMap<>();
        try {
            session.invalidate(); // Invalidate the session
            response.put("message", "Logout successful!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "An error occurred: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            boolean isDeleted = userService.deleteUser(id);
            if (isDeleted) {
                response.put("message", "User deleted successfully!");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("error", "User not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("error", "An error occurred: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
