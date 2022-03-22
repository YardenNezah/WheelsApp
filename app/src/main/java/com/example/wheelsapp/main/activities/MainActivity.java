package com.example.wheelsapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.wheelsapp.R;
import com.example.wheelsapp.main.fragments.BookingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container,new BookingFragment()).commit();



    }
}