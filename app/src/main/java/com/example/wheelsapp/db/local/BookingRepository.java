package com.example.wheelsapp.db.local;

import com.example.wheelsapp.interfaces.OnWheelsBookingsListener;
import com.example.wheelsapp.models.Booking;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class BookingRepository {

    private final BookingsDao dao;

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    public BookingRepository(BookingsDao dao) {
        this.dao = dao;
    }

    public void getAllBusinessBookings(String bid,OnWheelsBookingsListener listener) {
        executor.execute(() -> {
            List<Booking> bBookings = dao.getAllBookingsForBusiness(bid);
            listener.onSuccess(bBookings);
        });
    }

    public void getAllCustomerBookings(String cid, OnWheelsBookingsListener listener) {
        executor.execute(() ->  {
         List<Booking> cBookings =   dao.getAllBookingsForCustomer(cid);
         listener.onSuccess(cBookings);
        });
    }
    public void deleteBooking(Booking booking) {
        executor.execute(() -> dao.deleteBooking(booking));
    }
    public void updateBooking(Booking booking) {
        executor.execute(() -> dao.updateBooking(booking));
    }
}
