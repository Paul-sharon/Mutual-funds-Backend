package com.gtl.Mutualfunds.service;  

import com.gtl.Mutualfunds.dto.LoginDto;  
import com.gtl.Mutualfunds.dto.UserRegistrationDto;  
import com.gtl.Mutualfunds.model.User;  
import com.gtl.Mutualfunds.repository.UserRepository;  
import com.gtl.Mutualfunds.util.PasswordUtil;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

import java.util.List;  
import java.util.Optional;  

@Service  
public class UserService {  

    @Autowired  
    private UserRepository userRepository;  

    // Register a user  
    public String registerUser(UserRegistrationDto userDto) {
        // Check for null fields
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty.");
        }
        if (userDto.getName() == null || userDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty.");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password must not be null or empty.");
        }
        // Check for phone number
        if (userDto.getPhoneNumber() == null || userDto.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number must not be null or empty.");
        }

        // Check if email is already registered
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        // Generate salt and hash the password
        String salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(userDto.getPassword(), salt);

        // Create and save the user
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        user.setPhoneNumber(userDto.getPhoneNumber()); // Set phone number

        userRepository.save(user);
        return "User registered successfully!";
    }

    // Get user by ID  
    public User getUserById(Long id) {  
        return userRepository.findById(id).orElse(null);  
    }  

    // Get user by Email  
    public Optional<User> getUserByEmail(String email) {  
        return userRepository.findByEmail(email);  
    }  

    // Get all users  
    public List<User> getAllUsers() {  
        return userRepository.findAll();  
    }  

    // Verify password  
    public boolean verifyPassword(String enteredPassword, User user) {  
        String hashedEnteredPassword = PasswordUtil.hashPassword(enteredPassword, user.getSalt());  
        return hashedEnteredPassword.equals(user.getPassword());  
    }  

    // Authenticate user and return User object if successful  
    public Optional<User> authenticateUser(LoginDto loginDto) {
        // Validate input
        if (loginDto.getEmail() == null || loginDto.getEmail().isEmpty() ||
                loginDto.getPassword() == null || loginDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Email and password must not be null or empty.");
        }

        // Retrieve user by email
        Optional<User> optionalUser = userRepository.findByEmail(loginDto.getEmail()); //Using Optional avoids returning null directly, preventing NullPointerException
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Verify the password
            if (verifyPassword(loginDto.getPassword(), user)) {
                return Optional.of(user); // Return the user if authentication is successful
            } else {
                return Optional.empty(); // Return empty if password verification fails
            }
        }
        // No user found; return empty
        return Optional.empty();
    }

    // Delete user  
    public boolean deleteUser(Long id) {  
        Optional<User> user = userRepository.findById(id);  
        if (user.isPresent()) {  
            userRepository.delete(user.get()); // Delete the user  
            return true;  
        }  
        return false;  
    }  
}