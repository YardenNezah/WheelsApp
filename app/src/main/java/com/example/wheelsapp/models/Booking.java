package com.example.wheelsapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookings")
public class Booking {

    public static final String APPROVED = "approved";
    public static final String WAITING = "waiting";
    public static final String DECLINED = "declined";

    @PrimaryKey
    @NonNull
    private String id;
    private String time;
    private String date;
    private String customerId;
    private String customerName;
    private String serviceType;
    private String businessId;
    private String imageUrl;
    private String status;


    public Booking(String businessId, String customerId, String time, String date, String imageUrl, String customerName, String serviceType) {
        this.time = time;
        this.imageUrl = imageUrl;
        this.date = date;
        this.businessId = businessId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.serviceType = serviceType;
        this.status = WAITING;
    }

    public Booking(String businessId, String customerId, String date, String time, String customerName, String serviceType) {
        this(businessId, customerId, date, time, "", customerName, serviceType);
    }

    // firebase
    public Booking() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBusinessId() {
        return businessId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


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

