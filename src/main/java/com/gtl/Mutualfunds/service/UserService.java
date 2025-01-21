package com.gtl.Mutualfunds.service;

import com.gtl.Mutualfunds.dto.UserRegistrationDto;
import com.gtl.Mutualfunds.model.User;
import com.gtl.Mutualfunds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;  // Import List

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a user
    public String registerUser(UserRegistrationDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // Use hashing in production

        userRepository.save(user);
        return "User registered successfully!";
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();  // Return all users from the repository
    }
}
