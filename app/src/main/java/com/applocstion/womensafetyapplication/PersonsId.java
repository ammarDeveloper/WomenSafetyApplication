package com.applocstion.womensafetyapplication;

import java.util.ArrayList;

public class PersonsId {
    // properties of person getting register
    private int id;
    private String name;
    private ArrayList<Phone_numbers> phone_numbers;

    // Constructor
    public PersonsId(int id, String name, ArrayList<Phone_numbers> phone_numbers) {
        this.id = id;
        this.name = name;
        this.phone_numbers = phone_numbers;
    }

    // Constructor
    public PersonsId() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Phone_numbers> getPhone_numbers() {
        return phone_numbers;
    }

    public void setPhone_numbers(ArrayList<Phone_numbers> phone_numbers) {
        this.phone_numbers = phone_numbers;
    }

    // toString method
    @Override
    public String toString() {
        return "PersonsId{" +
                "id=" + id +
                ", phone_numbers=" + phone_numbers +
                '}';
    }
}
