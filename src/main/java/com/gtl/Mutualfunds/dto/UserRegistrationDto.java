package com.gtl.Mutualfunds.dto;

public class UserRegistrationDto {
    private Long id;  // Add this
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    // Getters and Setters
    public Long getId() { // Add this
        return id;
    }

    public void setId(Long id) { // Add this
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
