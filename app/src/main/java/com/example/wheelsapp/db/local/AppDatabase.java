package com.example.wheelsapp.db.local;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;


@Database(entities = {Booking.class, WheelsBusiness.class, WheelsCustomer.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    abstract BookingsDao bookingsDao();
    abstract BusinessDao businessDao();
    abstract CustomerDao customerDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance==null) {
            instance = Room.databaseBuilder(context,AppDatabase.class,"wheelsDB").build();
        }
        return instance;
    }


}
