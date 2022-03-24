package com.example.wheelsapp.db.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wheelsapp.models.Booking;

import java.util.List;

@Dao
public interface BookingsDao {

    @Query("SELECT * from bookings WHERE customerId = :bid AND status = 'declined' ")
    List<Booking> getCustomerDeclinedBookings(String bid);

    @Query("SELECT * from bookings WHERE customerId = :bid AND status = 'approved' ")
    List<Booking> getCustomerApprovedBookings(String bid);

    @Insert
    void insertBooking(Booking booking);

    @Insert
    void insertBookings(Booking... booking);


    @Query("SELECT * from bookings WHERE id =:bid")
    Booking getBooking(String bid);

    @Delete
    void deleteBooking(Booking booking);

    @Update
    void updateBooking(Booking booking);
}
