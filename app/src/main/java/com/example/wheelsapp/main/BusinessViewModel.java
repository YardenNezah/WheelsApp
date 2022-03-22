package com.example.wheelsapp.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wheelsapp.db.external.FirebaseManager;
import com.example.wheelsapp.interfaces.OnWheelsBusinessBookingsExternalListener;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.WheelsBusiness;

import java.util.List;

public class BusinessViewModel extends ViewModel {

    private MutableLiveData<WheelsBusiness> businessLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Booking>> bookingListLiveData = new MutableLiveData<>();

    private MutableLiveData<Exception> exceptionsLiveData = new MutableLiveData<>();
    private final FirebaseManager firebaseManager = FirebaseManager.instance;


    public BusinessViewModel() {

    }


    public MutableLiveData<Exception> getExceptionsLiveData() {
        return exceptionsLiveData;
    }

    public MutableLiveData<WheelsBusiness> getBusinessLiveData() {
        return businessLiveData;
    }

    public MutableLiveData<List<Booking>> getBookingListLiveData() {
        return bookingListLiveData;
    }

    public void getAllBusinessBookings(String bid) {
        firebaseManager.getAllBusinessBookings(bid, new OnWheelsBusinessBookingsExternalListener() {
            @Override
            public void onSuccess(List<Booking> bookings) {
                bookingListLiveData.postValue(bookings);
            }

            @Override
            public void onFailure(Exception e) {
            exceptionsLiveData.postValue(e);
            }
        });
    }

    //cache
    public void setBusiness(WheelsBusiness business) {
        businessLiveData.postValue(business);
    }
}
