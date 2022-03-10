package com.example.wheelsapp.models;

public class LatLng {

    private double lat;
    private double lng;

    // firebase constructor
    public LatLng() {}
    public LatLng(double lat,double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
