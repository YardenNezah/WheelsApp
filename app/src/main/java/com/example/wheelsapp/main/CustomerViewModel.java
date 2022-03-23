package com.example.wheelsapp.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wheelsapp.db.external.FirebaseManager;
import com.example.wheelsapp.interfaces.WheelsExternalListener;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;

import java.util.List;

public class CustomerViewModel extends ViewModel {
    private MutableLiveData<WheelsCustomer> customerLiveData = new MutableLiveData<>();
    private MutableLiveData<Exception> exceptionsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WheelsBusiness>> businessesLiveData = new MutableLiveData<>();
    private final FirebaseManager firebaseManager = FirebaseManager.instance;

    public CustomerViewModel() {
        // loads businesses
         getAllBusinesses();
    }

    public MutableLiveData<WheelsCustomer> getCustomerLiveData() {
        return customerLiveData;
    }


    public MutableLiveData<Exception> getExceptionsLiveData() {
        return exceptionsLiveData;
    }

    public MutableLiveData<List<WheelsBusiness>> getBusinessesLiveData() {
        return businessesLiveData;
    }


    private void getAllBusinesses() {
        firebaseManager.getBusinessList(new WheelsExternalListener<List<WheelsBusiness>>() {
            @Override
            public void onSuccess(List<WheelsBusiness> businesses) {
                businessesLiveData.postValue(businesses);
            }

            @Override
            public void onFailure(Exception e) {
                exceptionsLiveData.postValue(e);
            }
        });
    }

    //cache
    public void setCustomer(WheelsCustomer customer) {
        customerLiveData.postValue(customer);
    }
}
