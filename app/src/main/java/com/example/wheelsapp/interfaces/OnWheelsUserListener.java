package com.example.wheelsapp.interfaces;

import com.example.wheelsapp.models.WheelsUser;

public interface OnWheelsUserListener extends WheelsListener<WheelsUser> {

    @Override
    void onFailure(Exception e);

    @Override
    void onSuccess(WheelsUser user);
}
