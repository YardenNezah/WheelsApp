package com.example.wheelsapp.models;

public class WheelsUser {
    private String email;
    private String fullName;
    private boolean businessOwner;

    public WheelsUser(String email, String fullName, boolean businessOwner) {
        this.email = email;
        this.fullName = fullName;
        this.businessOwner = businessOwner;
    }


    //firebase constructor
    public WheelsUser() {}

    public void setEmail(String email) {
        this.email = email;
    }


    public void setBusinessOwner(boolean businessOwner) {
        this.businessOwner = businessOwner;
    }

    public boolean isBusinessOwner() {
        return businessOwner;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }
}
