package com.jsstech.pushnotificationapp;

public class userModel {
    String email,token;

    public userModel() {
    }

    public userModel(String email,String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
