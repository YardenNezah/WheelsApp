package com.example.wheelsapp.interfaces;

public interface WheelsExternalListener<T>{

    void onSuccess(T object);
    void onFailure(Exception e);
}
