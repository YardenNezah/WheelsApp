package com.example.wheelsapp.interfaces;

import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.WheelsBusiness;

import java.util.List;

public interface OnWheelsBusinessBookingsListener extends WheelsListener<List<Booking>> {
    @Override
    void onSuccess(List<Booking> bookings);

    @Override
    void onFailure(Exception e);
}