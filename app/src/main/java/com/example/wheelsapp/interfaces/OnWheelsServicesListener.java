package com.example.wheelsapp.interfaces;

import com.example.wheelsapp.models.WheelsService;

import java.util.List;

public interface OnWheelsServicesListener extends  WheelsListener<List<WheelsService>> {
    @Override
    void onSuccess(List<WheelsService> services);

    @Override
    void onFailure(Exception e);
}
