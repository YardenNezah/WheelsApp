package com.example.wheelsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.wheelsapp.fragments.BookingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new BookingFragment()).commit();



    }
}