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



    public void getCustomerById(String cid, WheelsLocalListener<WheelsCustomer> listener) {
        executor.execute(() -> {
            WheelsCustomer c =  dao.getCustomerById(cid);
            listener.onSuccess(c);
        });
    }
    public void getAllCustomers(WheelsLocalListener<List<WheelsCustomer>> listener) {
        executor.execute(() -> {
            List<WheelsCustomer> customers = dao.getAllCustomers();
            listener.onSuccess(customers);
        });
    }

    public void deleteCustomer(WheelsCustomer customer) {
        executor.execute(() -> dao.deleteCustomer(customer));
    }
    public void updateCustomer(WheelsCustomer customer) {
        executor.execute(() -> dao.updateCustomer(customer));
    }
}
