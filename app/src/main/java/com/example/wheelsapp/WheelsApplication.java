package com.example.wheelsapp;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class WheelsApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
