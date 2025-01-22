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
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        String salt = PasswordUtil.generateSalt(); // Generate a salt
        String hashedPassword = PasswordUtil.hashPassword(userDto.getPassword(), salt); // Hash the password with salt

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(hashedPassword); // Save hashed password
        user.setSalt(salt); // Save salt for verification

        userRepository.save(user);
        return "User registered successfully!";
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public boolean verifyPassword(String enteredPassword, User user) {
        String hashedEnteredPassword = PasswordUtil.hashPassword(enteredPassword, user.getSalt());
        return hashedEnteredPassword.equals(user.getPassword());
    }
    public boolean authenticateUser(LoginDto loginDto) {
        Optional<User> optionalUser = userRepository.findByEmail(loginDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return verifyPassword(loginDto.getPassword(), user);
        }
        return false;
    }

}
