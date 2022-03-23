package com.example.wheelsapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookings")
public class Booking {

    @PrimaryKey
    @NonNull
   private String id;
   private String time;
   private String customerId;
   private String customerName;
   private String serviceType;
   private String businessId;
   private String imageUrl;

    public Booking(String businessId,String customerId, String time,String imageUrl, String customerName, String serviceType) {
        this.time = time;
        this.imageUrl = imageUrl;
        this.businessId = businessId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.serviceType = serviceType;
    }
    public Booking(String businessId,String customerId, String time, String customerName, String serviceType) {
        this(businessId,customerId,time,"",customerName,serviceType);
    }

    // firebase
    public Booking() {}

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }


    public void setServiceTypeId(String serviceType) {
        this.serviceType = serviceType;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getTime() {
        return time;
    }
}

