package com.example.wheelsapp.models;

public class WheelsBusiness {

    private String businessId;
    private String name;
    private LatLng latLng;
    private String image;
    private String email;
    private String ownerId;

    private String phoneNumber;

    public WheelsBusiness(String phoneNumber, String businessName, LatLng latLng, String image,
                          String email,String ownerId) {
        this.name = businessName;
        this.latLng = latLng;
        this.image = image;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ownerId = ownerId;
    }

    // firebase constructor
    public WheelsBusiness(){

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getEmail() {
        return email;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setName(String name) {
        this.name = name;
    }
}
