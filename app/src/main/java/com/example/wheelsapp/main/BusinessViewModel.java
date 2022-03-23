package com.example.wheelsapp.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wheelsapp.db.external.FirebaseManager;
import com.example.wheelsapp.interfaces.OnWheelsBusinessBookingsExternalListener;
import com.example.wheelsapp.interfaces.WheelsExternalListener;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.WheelsBusiness;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class BusinessViewModel extends ViewModel {

    private MutableLiveData<WheelsBusiness> businessLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Booking>> bookingListLiveData = new MutableLiveData<>();

    private MutableLiveData<Exception> exceptionsLiveData = new MutableLiveData<>();

    private MutableLiveData<String> bookingStatusLiveData = new MutableLiveData<>();

    private final FirebaseManager firebaseManager = FirebaseManager.instance;


    public BusinessViewModel() {
        firebaseManager.getAllBusinessBookings(FirebaseAuth.getInstance().getUid(), new OnWheelsBusinessBookingsExternalListener() {
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

    @Override
    protected void onCleared() {
        super.onCleared();
        firebaseManager.removeBusinessBookingsListener();
    }


    public void declineBooking(Booking booking) {
        firebaseManager.removeBooking(booking, new WheelsExternalListener<String>() {
            @Override
            public void onSuccess(String bookingStatus) {
                bookingStatusLiveData.postValue(bookingStatus);
            }

            @Override
            public void onFailure(Exception e) {
                exceptionsLiveData.postValue(e);
            }
        });
    }

    public MutableLiveData<String> getBookingStatusLiveData() {
        return bookingStatusLiveData;
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
    //cache
    public void setBusiness(WheelsBusiness business) {
        businessLiveData.postValue(business);
    }
}
