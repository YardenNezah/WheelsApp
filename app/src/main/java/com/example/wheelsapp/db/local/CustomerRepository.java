package com.example.wheelsapp.db.local;

import com.example.wheelsapp.interfaces.OnWheelsBookingsListener;
import com.example.wheelsapp.interfaces.WheelsLocalListener;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class CustomerRepository {

    private final CustomerDao dao;

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    public CustomerRepository(CustomerDao dao) {
        this.dao = dao;
    }


    public void deleteCustomer(WheelsCustomer customer) {
        executor.execute(() -> dao.deleteCustomer(customer));
    }
    public void updateCustomer(WheelsCustomer customer) {
        executor.execute(() -> dao.updateCustomer(customer));
    }
}
