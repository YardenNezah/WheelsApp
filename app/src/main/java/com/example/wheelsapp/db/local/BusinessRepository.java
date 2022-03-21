package com.example.wheelsapp.db.local;

import com.example.wheelsapp.interfaces.WheelsLocalListener;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class BusinessRepository {

    private final BusinessDao dao;

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    public BusinessRepository(BusinessDao dao) {
        this.dao = dao;
    }



    public void getBusinessById(String bid, WheelsLocalListener<WheelsBusiness> listener) {
        executor.execute(() -> {
            WheelsBusiness c =  dao.getBusinessById(bid);
            listener.onSuccess(c);
        });
    }

    public void getAllBusinesses(WheelsLocalListener<List<WheelsBusiness>> listener) {
        executor.execute(() -> {
            List<WheelsBusiness> businesses = dao.getAllBusinesses();
            listener.onSuccess(businesses);
        });
    }


    public void deleteBusiness(WheelsBusiness business) {
        executor.execute(() -> dao.deleteBusiness(business));
    }
    public void updateBusiness(WheelsBusiness business) {
        executor.execute(() -> dao.updateBusiness(business));
    }
}
