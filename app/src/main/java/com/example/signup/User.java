package com.example.signup;

public class User {

     String email, username, phoneNo, city,imageUri, uid;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(String email, String username, String phoneNo, String city, String imageUri, String uid) {
        this.email = email;
        this.username = username;
        this.phoneNo = phoneNo;
        this.city = city;
        this.imageUri = imageUri;
        this.uid = uid;
    }


    public User() {
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}





