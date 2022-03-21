package com.example.wheelsapp.interfaces;

import com.example.wheelsapp.models.WheelsBusiness;

public interface OnWheelsBusinessExternalListener extends WheelsExternalListener<WheelsBusiness> {
    @Override
    void onSuccess(WheelsBusiness business);

    @Override
    void onFailure(Exception e);
}
