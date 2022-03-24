package com.example.wheelsapp.main;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wheelsapp.db.external.FirebaseManager;
import com.example.wheelsapp.db.local.AppDatabase;
import com.example.wheelsapp.db.local.BookingRepository;
import com.example.wheelsapp.db.local.ThemeDao;
import com.example.wheelsapp.interfaces.LocalDatabaseInitializer;
import com.example.wheelsapp.interfaces.OnWheelsBusinessBookingsExternalListener;
import com.example.wheelsapp.interfaces.WheelsExternalListener;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.Theme;
import com.example.wheelsapp.models.WheelsBusiness;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class BusinessViewModel extends BookingViewModel {

    private MutableLiveData<WheelsBusiness> businessLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Booking>> bookingListLiveData = new MutableLiveData<>();
    private MutableLiveData<Exception> exceptionsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> bookingStatusLiveData = new MutableLiveData<>();

    private LiveData<Theme> themeLiveData = new MutableLiveData<>();
    private ThemeDao themeDao;
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

    public LiveData<Theme> getThemeLiveData() {
        return themeLiveData;
    }

    public ThemeDao getThemeDao(Application context) {
        if (themeDao == null)
            themeDao = AppDatabase.getInstance(context).themeDao();
        return themeDao;
    }

    public void listenForThemeUpdate(Application context) {
        new Thread(() -> {
            themeLiveData = getThemeDao(context).getTheme();
        }).start();

    }

    public void updateTheme(Application context, int color) {
        new Thread(() -> getThemeDao(context).updateTheme(color)).start();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        firebaseManager.removeBusinessBookingsListener();
    }


    public void declineBooking(Booking booking) {
        firebaseManager.declineBooking(booking, new WheelsExternalListener<String>() {
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

    public void approveBooking(Booking booking) {
        firebaseManager.approveBooking(booking, new WheelsExternalListener<String>() {
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
