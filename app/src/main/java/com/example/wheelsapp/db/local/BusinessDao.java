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
public interface BusinessDao {

    @Query("SELECT * from businesses")
    List<WheelsBusiness> getAllBusinesses();

    @Insert
    void insertBusiness(WheelsBusiness business);

    @Delete
    void deleteBusiness(WheelsBusiness business);


    @Query("SELECT * from businesses WHERE businessId =:id")
    WheelsBusiness getBusinessById(String id);

    @Update
    void updateBusiness(WheelsBusiness business);
}
