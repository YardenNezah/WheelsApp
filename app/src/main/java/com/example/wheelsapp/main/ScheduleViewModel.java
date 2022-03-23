package com.example.wheelsapp.main;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wheelsapp.db.external.FirebaseManager;
import com.example.wheelsapp.interfaces.WheelsExternalListener;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.WheelsBusiness;

public class ScheduleViewModel extends ViewModel {

    public ScheduleViewModel() {

    }
    private WheelsBusiness business;
    private String scheduleBookingDate;
    private Uri scheduleBookingImageData;
    private final MutableLiveData<Exception> exceptionsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> statusLiveData = new MutableLiveData<>();
    private final FirebaseManager firebaseManager = FirebaseManager.instance;

    public MutableLiveData<Exception> getExceptionsLiveData() {
        return exceptionsLiveData;
    }

    public MutableLiveData<String> getStatusLiveData() {
        return statusLiveData;
    }

    public void setBusiness(WheelsBusiness business) {
        this.business = business;
    }

    public WheelsBusiness getBusiness() {
        return business;
    }

    public void setScheduleBookingImageData(Uri scheduleBookingImageData) {
        this.scheduleBookingImageData = scheduleBookingImageData;
    }

    public Uri getScheduleBookingImageData() {
        return scheduleBookingImageData;
    }

    public void setScheduleBookingDate(String scheduleBookingDate) {
        this.scheduleBookingDate = scheduleBookingDate;
    }

    public String getScheduleBookingDate() {
        return scheduleBookingDate;
    }

    public void bookTreatment(String bid, Booking booking) {
        firebaseManager.saveBooking(bid, booking,getScheduleBookingImageData(), new WheelsExternalListener<String>() {
            @Override
            public void onSuccess(String status) {
                statusLiveData.postValue(status);
            }

            @Override
            public void onFailure(Exception e) {
                exceptionsLiveData.postValue(e);
            }
        });
    }
}
