package com.applocstion.womensafetyapplication;

import java.util.ArrayList;

// Class for the phone numbers
public class Phone_numbers {
    private int id;
    private String phone_numbers;
    private boolean isExpanded;

    // Constructor
    public Phone_numbers(int id, String phone_numbers) {
        this.id = id;
        this.phone_numbers = phone_numbers;
        this.isExpanded = false;
    }

    // Empty Constructor
    public Phone_numbers() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_numbers;
    }

    public void setPhone_numbers(String phone_numbers) {
        this.phone_numbers = phone_numbers;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    // toString method
    @Override
    public String toString() {
        return "Phone_numbers{" +
                "id=" + id +
                ", phone_numbers='" + phone_numbers + '\'' +
                '}';
    }
}
