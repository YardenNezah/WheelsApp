package com.example.wheelsapp.interfaces;

import com.example.wheelsapp.models.Booking;

import java.util.List;

public interface OnWheelsBusinessBookingsExternalListener extends WheelsExternalListener<List<Booking>> {
    @Override
    void onSuccess(List<Booking> bookings);

    @Override
    void onFailure(Exception e);
}