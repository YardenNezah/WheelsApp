package com.example.wheelsapp.db.local;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.Theme;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;


@Database(entities = {Booking.class, WheelsBusiness.class, WheelsCustomer.class, Theme.class},version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract BookingsDao bookingsDao();
    public abstract ThemeDao themeDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance==null) {
            instance = Room.databaseBuilder(context,AppDatabase.class,"wheelsDB")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}
