package com.example.bitslibrary.Models;

public class UserRequest {
    private String email, name, mobile, address,password;

    public UserRequest(String email, String name, String mobile, String address, String password) {
        this.email = email;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.password = password;
    }

    public UserRequest() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
