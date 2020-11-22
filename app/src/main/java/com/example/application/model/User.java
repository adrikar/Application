package com.example.application.model;

public class User {

    private String name, address, phoneNumber;

    public User(){}
    public User(String name, String address, String phoneNumber){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;

    }

    public java.lang.String getName() { return name; }

    public void setName(java.lang.String name) { this.name = name; }

    public java.lang.String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(java.lang.String phoneNumber) { this.phoneNumber = phoneNumber; }

    public java.lang.String getAddress() { return address; }

    public void setAddress(java.lang.String address) {
        this.address = address;
    }
}
