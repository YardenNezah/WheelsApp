package com.example.wheelsapp.models;

public class WheelsService {
    private long serviceDate;
    private String serviceType;
    private double servicePrice;

    public WheelsService(long serviceDate, String serviceType, double servicePrice) {
        this.serviceDate = serviceDate;
        this.serviceType = serviceType;
        this.servicePrice = servicePrice;
    }
    //firebase constructor
    public WheelsService() {
    }
    public void setServiceDate(long serviceDate) {
        this.serviceDate = serviceDate;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }


    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public long getServiceDate() {
        return serviceDate;
    }

}
