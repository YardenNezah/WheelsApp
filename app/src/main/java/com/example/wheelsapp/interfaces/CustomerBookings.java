package com.example.wheelsapp.interfaces;

import com.example.wheelsapp.models.Booking;

import java.util.List;

public class CustomerBookings {


    public CustomerBookings(List<Booking> waiting, List<Booking> reviewed) {
        this.waiting = waiting;
        this.reviewed = reviewed;
    }

    private List<Booking> waiting;
    private List<Booking> reviewed;

    public List<Booking> getReviewed() {
        return reviewed;
    }


    public List<Booking> getWaiting() {
        return waiting;
    }
}
