package com.example.buddyapplication.model;

import java.io.Serializable;

public class Buddy implements Serializable {
    private int id;
    private String name;
    private String gender;
    private String dob; //date of birth
    private String phone;
    private String email;
    private String imagePath; // Path to buddy's profile image

    public Buddy() {}

    public Buddy(int id, String name, String gender, String dob, String phone, String email) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.imagePath = null;
    }

    public Buddy(String name, String gender, String dob, String phone, String email) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.imagePath = null;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
