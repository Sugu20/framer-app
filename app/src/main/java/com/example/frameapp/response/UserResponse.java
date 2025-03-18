package com.example.frameapp.response;

public class UserResponse {
    private String status;
    private String message;
    private User user;

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public User getUser() { return user; }

    public class User {
        private String id;
        private String name;
        private String phone_number;
        private String image_url;

        public String getId() { return id; }
        public String getName() { return name; }
        public String getPhoneNumber() { return phone_number; }
        public String getImageUrl() { return image_url; }
    }
}

