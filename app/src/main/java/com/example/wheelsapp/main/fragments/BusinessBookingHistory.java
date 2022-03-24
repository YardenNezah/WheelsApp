package com.example.wheelsapp.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsFragment;
import com.example.wheelsapp.main.BusinessViewModel;
import com.example.wheelsapp.models.Booking;

import java.util.List;

public class BusinessBookingHistory extends WheelsFragment {


    RecyclerView rvBookingHistory;
    BusinessViewModel businessViewModel;
    HistoryFragment.HistoryBookingsListRvAdapter rvAdapter;
    LinearLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_business_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBookingHistory = view.findViewById(R.id.rvBusinessBookingsHistory);
        layout = view.findViewById(R.id.layout_business_history_bookingList);
        rvBookingHistory.setLayoutManager(new LinearLayoutManager(getContext()));


        if (getActivity() != null) {
            businessViewModel = new ViewModelProvider(getActivity()).get(BusinessViewModel.class);

            businessViewModel.getThemeLiveData()
                    .observe(getViewLifecycleOwner(), theme ->

                    {
                        if (theme != null)
                            layout.setBackgroundColor(theme.getColor());
                    });
            businessViewModel.getBookingListLiveData()
                    .observe(getViewLifecycleOwner(), bookings -> {

                        if(bookings!=null && bookings.size() >0) {
                            view.findViewById(R.id.no_bookings_history_business).setVisibility(View.GONE);
                        }
                        rvAdapter = new HistoryFragment.HistoryBookingsListRvAdapter(bookings, false);
                        rvBookingHistory.setAdapter(rvAdapter);
                    });
        }
    }
}
