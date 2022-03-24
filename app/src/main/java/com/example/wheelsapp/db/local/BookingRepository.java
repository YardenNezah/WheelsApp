package com.example.wheelsapp.db.local;

import com.example.wheelsapp.interfaces.OnWheelsBookingsListener;
import com.example.wheelsapp.models.Booking;

import java.util.List;
public class BookingRepository extends Repository {

    private final BookingsDao dao;

    public BookingRepository(BookingsDao dao) {
        this.dao = dao;
    }

    public void getAllReviewedCustomerBookings(String cid, OnWheelsBookingsListener listener) {
        executor.execute(() ->  {
         List<Booking> cBookings =   dao.getCustomerApprovedBookings(cid);
         listener.onSuccess(cBookings);
        });
    }



    public void saveBookings(Booking... bookings) {
        executor.execute(() -> dao.insertBookings(bookings));
    }

    public void deleteBooking(Booking booking) {
        executor.execute(() -> dao.deleteBooking(booking));
    }
    public void updateBooking(Booking booking) {
        executor.execute(() -> dao.updateBooking(booking));
    }
}
