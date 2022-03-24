package com.example.wheelsapp.main;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wheelsapp.db.external.FirebaseManager;
import com.example.wheelsapp.db.local.AppDatabase;
import com.example.wheelsapp.db.local.ThemeDao;
import com.example.wheelsapp.interfaces.CustomerBookings;
import com.example.wheelsapp.interfaces.WheelsExternalListener;
import com.example.wheelsapp.interfaces.WheelsLocalListener;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.Theme;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class CustomerViewModel extends BookingViewModel {
    private MutableLiveData<WheelsCustomer> customerLiveData = new MutableLiveData<>();
    private MutableLiveData<Exception> exceptionsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WheelsBusiness>> businessesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Booking>> reviewedBookings = new MutableLiveData<>();
    private MutableLiveData<List<Booking>> waitingBookings = new MutableLiveData<>();
    private final FirebaseManager firebaseManager = FirebaseManager.instance;
    private ThemeDao themeDao;
    private LiveData<Theme> themeLiveData = new MutableLiveData<>();

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

    public MutableLiveData<List<Booking>> getReviewedBookings() {
        return reviewedBookings;
    }


    public MutableLiveData<List<Booking>> getWaitingBookings() {
        return waitingBookings;
    }

    public MutableLiveData<List<WheelsBusiness>> getBusinessesLiveData() {
        return businessesLiveData;
    }

    public void getAllReviewedBookings(String cid) {
        if (isDatabaseInitialized)
            bookingRepository.getAllReviewedCustomerBookings(cid, bookings -> reviewedBookings.postValue(bookings));
    }

    public void getAllCustomerBookings(String cid) {
        firebaseManager.getCustomerBookingList(cid, new WheelsExternalListener<CustomerBookings>() {
            @Override
            public void onSuccess(CustomerBookings customerBookings) {

                CustomerViewModel.this.waitingBookings.postValue(customerBookings.getWaiting());
                CustomerViewModel.this.reviewedBookings.postValue(customerBookings.getReviewed());
            }

            @Override
            public void onFailure(Exception e) {
                exceptionsLiveData.postValue(e);
            }
        });
    }

    public ThemeDao getThemeDao(Application context) {
        if (themeDao == null)
            themeDao = AppDatabase.getInstance(context).themeDao();
        return themeDao;
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

    public LiveData<Theme> getThemeLiveData() {
        return themeLiveData;
    }

    //cache
    public void setCustomer(WheelsCustomer customer) {
        customerLiveData.postValue(customer);
    }

    public void listenForThemeUpdate(Application context, WheelsLocalListener<Theme> immediate) {
        new Thread(() -> {
            themeLiveData = getThemeDao(context).getTheme();
            Theme theme = themeDao.getThemeSingleValue();
            immediate.onSuccess(theme);
        }).start();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        firebaseManager.removeBusinessListValueEventListener();
        firebaseManager.removeCustomerBookingsListener();
    }

}
