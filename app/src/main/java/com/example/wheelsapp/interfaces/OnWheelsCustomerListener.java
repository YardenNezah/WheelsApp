package com.example.wheelsapp.interfaces;

import com.example.wheelsapp.models.WheelsCustomer;

public interface OnWheelsCustomerListener extends WheelsListener<WheelsCustomer> {

    @Override
    void onFailure(Exception e);

    @Override
    void onSuccess(WheelsCustomer user);
}
