package com.ecommerce.model;

public class User {
    private String username;
    private String phoneNumber;
    private boolean isAdmin;

    public User(String username, String phoneNumber, boolean isAdmin) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

}