package com.example.wheelsapp.models;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "businesses")
public class WheelsBusiness {


    @PrimaryKey
    @NonNull
    private String businessId;
    private String name;
    private double lat;
    private double lng;
    private String image;
    private String email;
    private String ownerId;

    private String phoneNumber;

    public WheelsBusiness(String phoneNumber, String businessName, LatLng latLng, String image,
                          String email,String ownerId) {
        this.name = businessName;
        this.lat = latLng.getLat();
        this.lng = latLng.getLng();
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


    @NonNull
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(@NonNull String businessId) {
        this.businessId = businessId;
    }

    public String getEmail() {
        return email;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
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


    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setName(String name) {
        this.name = name;
    }
}
