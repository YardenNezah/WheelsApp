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

    public Booking(@NonNull String id,String businessId, String time, String customerId, String customerName, String serviceType) {
        this.id = id;
        this.time = time;
        this.businessId = businessId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.serviceType = serviceType;
    }

    // firebase
    public Booking() {}

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
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

