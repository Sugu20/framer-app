package com.example.frameapp.response;


public class LoginResponse {
    private String status;
    private String message;
    private int user_id;

    // Getter for status
    public String getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Setter for message
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter for user_id
    public int getUser_id() {
        return user_id;
    }

    // Setter for user_id
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
