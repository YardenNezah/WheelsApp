package com.example.wheelsapp.interfaces;

import com.example.wheelsapp.models.Booking;

import java.util.List;

public interface OnWheelsBookingsListener extends  WheelsLocalListener<List<Booking>> {
    @Override
    void onSuccess(List<Booking> bookings);

}
