package com.example.wheelsapp.models;

public class Booking {

   private String id;
   private String time;
   private String customerId;
   private String customerName;
   private String serviceType;

    public Booking(String time, String customerId, String customerName, String serviceType) {
        this.time = time;
        this.customerId = customerId;
        this.customerName = customerName;
        this.serviceType = serviceType;
    }

    // firebase
    public Booking() {}

    public void setId(String id) {
        this.id = id;
    }

    public void setServiceType(String serviceType) {
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

