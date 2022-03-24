package com.example.wheelsapp.main;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.wheelsapp.db.local.AppDatabase;
import com.example.wheelsapp.db.local.BookingRepository;
import com.example.wheelsapp.interfaces.LocalDatabaseInitializer;

public abstract class BookingViewModel extends ViewModel implements LocalDatabaseInitializer {
    protected BookingRepository bookingRepository;
    protected boolean isDatabaseInitialized;


    @Override
    public void initializeRepo(Application context) {
        bookingRepository = new BookingRepository(AppDatabase.getInstance(context).bookingsDao());
        isDatabaseInitialized = true;
    }


}
