package com.example.frameapp.response;


public class LoginRequest {
    private String phone_number;
    private String password;

    public LoginRequest(String phone_number, String password) {
        this.phone_number = phone_number;
        this.password = password;
    }

    // Getters and Setters
}
