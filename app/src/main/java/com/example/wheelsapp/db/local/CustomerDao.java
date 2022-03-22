package com.example.wheelsapp.db.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;

import java.util.List;

@Dao
public interface CustomerDao {
    @Query("SELECT * from customers WHERE customerId =:id")
    WheelsCustomer getCustomer(String id);

    @Insert
    void insertCustomer(WheelsCustomer customer);

    @Delete
    void deleteCustomer(WheelsCustomer customer);


    @Update
    void updateCustomer(WheelsCustomer customer);
}
