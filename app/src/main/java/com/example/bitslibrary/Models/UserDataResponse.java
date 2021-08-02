package com.example.bitslibrary.Models;

public class UserDataResponse {
    private int id;
    private String email;
    private String name;
    private String mobile;
    private String address;
    private String password;
    private String createdAt;
    private String updatedAt;

    public int getID() { return id; }
    public void setID(int value) { this.id = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getMobile() { return mobile; }
    public void setMobile(String value) { this.mobile = value; }

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String value) { this.createdAt = value; }
}
