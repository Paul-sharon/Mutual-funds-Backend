package com.gtl.Mutualfunds.controller;  

import com.gtl.Mutualfunds.dto.LoginDto;  
import com.gtl.Mutualfunds.dto.UserRegistrationDto;  
import com.gtl.Mutualfunds.model.User;  
import com.gtl.Mutualfunds.service.UserService;  
import com.gtl.Mutualfunds.util.JwtUtil; // Import your JWT utility class  
import jakarta.servlet.http.Cookie;  
import jakarta.servlet.http.HttpServletResponse;  
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

    @Autowired  
    private JwtUtil jwtUtil; // Inject the JWT utility class  

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

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {//involves session management // it may require additional HTTP-level handling like setting cookies or headers (e.g., for authentication).
        Map<String, String> res = new HashMap<>();
        System.out.println("Login attempt for email: " + loginDto.getEmail());

        try {
            Optional<User> user = userService.authenticateUser(loginDto);

            if (user.isPresent()) {
                String token = jwtUtil.generateToken(user.get().getEmail());
                Cookie cookie = new Cookie("jwt_token", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(24 * 60 * 60); // 1 day
                response.addCookie(cookie); //allows the client (browser) to store the JWT  token and send it back with future requests to authenticate the user.
                //response here is an HttpServletResponse object
                res.put("message", "Login successful!");
                res.put("token", token);
                System.out.println("Login successful for: " + loginDto.getEmail());
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                System.out.println("Invalid login attempt for email: " + loginDto.getEmail());
                res.put("error", "Invalid email or password");
                return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.out.println("Error during login for email: " + loginDto.getEmail() + " - Exception: " + e.getMessage());
            e.printStackTrace();
            res.put("error", "An internal error occurred. Please try again later.");
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
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

    // Get current logged-in user  
    @GetMapping("/currentUser")  
    public ResponseEntity<Map<String, Object>> getCurrentUser(@CookieValue(value = "jwt_token", defaultValue = "") String token) {  
        Map<String, Object> response = new HashMap<>();  
        if (token.isEmpty() || jwtUtil.isTokenExpired(token)) {  
            response.put("error", "Unauthorized");  
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);  
        }  

        String username = jwtUtil.extractUsername(token);  
        Optional<User> user = userService.getUserByEmail(username);  
        if (user.isPresent()) {  
            response.put("user", user.get());  
            return new ResponseEntity<>(response, HttpStatus.OK);  
        } else {  
            response.put("error", "User not found");  
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);  
        }  
    }  

    // Logout user  
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
        Map<String, String> res = new HashMap<>();
        try {
            // Clear the JWT cookie
            Cookie cookie = new Cookie("jwt_token", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0); // Delete the cookie
            response.addCookie(cookie);

            res.put("message", "Logout successful!");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.put("error", "An error occurred: " + e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
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