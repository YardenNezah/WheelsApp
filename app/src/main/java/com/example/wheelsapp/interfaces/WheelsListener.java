package com.example.wheelsapp.interfaces;

public interface WheelsListener<T>{

    void onSuccess(T object);
    void onFailure(Exception e);
}
