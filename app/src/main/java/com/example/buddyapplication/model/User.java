package com.example.buddyapplication.model;

public class User {
    private int id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() { return id; }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
}
