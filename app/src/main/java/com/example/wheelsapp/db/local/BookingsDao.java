package com.example.wheelsapp.db.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;

import java.util.List;

@Dao
public interface BookingsDao {

    @Query("SELECT * from bookings WHERE businessId = :bid")
    List<Booking> getAllBookingsForBusiness(String bid);


    @Query("SELECT * from bookings WHERE customerId=:cid")
    List<Booking> getAllBookingsForCustomer(String cid);

    @Insert
    void insertBooking(Booking booking);

    @Query("SELECT * from bookings WHERE id =:bid")
    Booking getBooking(String bid);
    @Delete
    void deleteBooking(Booking booking);


    @Update
    void updateBooking(Booking booking);
}
