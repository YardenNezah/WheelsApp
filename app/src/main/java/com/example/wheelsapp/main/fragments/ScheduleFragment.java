package com.example.wheelsapp.main.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsFragment;
import com.example.wheelsapp.main.ScheduleViewModel;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.utilities.JsonHelper;
import com.google.firebase.auth.FirebaseAuth;

public class ScheduleFragment extends WheelsFragment {


    CalendarView calendarView;

    TextView scheduleDateTv,businessNameTv;
    EditText scheduleType;
    ImageView scheduleImage;
    Button scheduleBtn;
    ScheduleViewModel scheduleViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView = view.findViewById(R.id.calendarView);
        businessNameTv = view.findViewById(R.id.business_name_booking);
        scheduleDateTv = view.findViewById(R.id.schedule_date_tv);
        scheduleImage = view.findViewById(R.id.schedule_photo);
        scheduleType = view.findViewById(R.id.schedule_type_et);
        scheduleBtn = view.findViewById(R.id.schedule_submit_btn);
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        if(getArguments()!=null) {
            WheelsBusiness business = JsonHelper.fromJson(getArguments().getString("business"),WheelsBusiness.class);
            scheduleViewModel.setBusiness(business);
            businessNameTv.setText("Book treatment at: " + business.getName());
        }
        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            String d = day + "/" + month + "/" + year;
            scheduleDateTv.setText(d);
            scheduleViewModel.setScheduleBookingDate(d);
        });
        scheduleImage.setOnClickListener(v -> openGallery());


        scheduleViewModel.getStatusLiveData()
                .observe(getViewLifecycleOwner(), s -> {
                    stopLoading();
                    showToast(s);
                    NavHostFragment.findNavController(this)
                            .popBackStack();
                });
        scheduleViewModel.getExceptionsLiveData()
                .observe(getViewLifecycleOwner(), e -> {
                    stopLoading();
                    showToast(e.getMessage());
                });
        scheduleBtn.setOnClickListener((v) -> {
            if(scheduleViewModel.getBusiness() ==null || FirebaseAuth.getInstance().getCurrentUser()==null || FirebaseAuth.getInstance().getCurrentUser().getEmail()==null) {
                showToast("Unknown error has occurred please try again later");
                return;
            }
            if(scheduleViewModel.getScheduleBookingDate() == null) {
                showToast("Please pick a date");
                return;
            }
            if(scheduleViewModel.getScheduleBookingImageData() == null) {
                showToast("Please upload photo of your vehicle");
                return;
            }

            if(isValidFields(new EditText[] {scheduleType})) {
                showLoading("Booking treatment with " + scheduleViewModel.getBusiness().getName() + "..");
                scheduleViewModel.bookTreatment(scheduleViewModel.getBusiness().getOwnerId(),
                        new Booking(scheduleViewModel.getBusiness().getOwnerId(),
                                FirebaseAuth.getInstance().getUid(),
                                "15:30"
                                ,FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0],""));
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            Uri imageData = data.getData();
            scheduleImage.setImageURI(imageData);
            scheduleViewModel.setScheduleBookingImageData(imageData);
        }
    }
}
