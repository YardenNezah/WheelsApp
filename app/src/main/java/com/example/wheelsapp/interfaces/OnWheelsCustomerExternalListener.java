package com.example.wheelsapp.interfaces;

import com.example.wheelsapp.models.WheelsCustomer;

public interface OnWheelsCustomerExternalListener extends WheelsExternalListener<WheelsCustomer> {

    @Override
    void onFailure(Exception e);

    @Override
    void onSuccess(WheelsCustomer user);
}
