package com.example.wheelsapp.interfaces;

import com.example.wheelsapp.models.WheelsBusiness;

import java.util.List;

public interface OnWheelsAllBusinessesListener extends WheelsListener<List<WheelsBusiness>> {
    @Override
    void onSuccess(List<WheelsBusiness> allBusinesses);

    @Override
    void onFailure(Exception e);
}