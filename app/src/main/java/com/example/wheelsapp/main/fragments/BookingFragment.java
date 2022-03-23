package com.example.wheelsapp.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsFragment;
import com.example.wheelsapp.db.external.FirebaseManager;
import com.example.wheelsapp.main.BusinessViewModel;
import com.example.wheelsapp.main.CustomerViewModel;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;
import com.example.wheelsapp.utilities.JsonHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookingFragment extends WheelsFragment {


    private RecyclerView rvBookingList;
    private BookingsListRvAdapter bookingsListRvAdapter;

    private BusinessViewModel businessViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_business_bookings,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBookingList = view.findViewById(R.id.rvBookingList);
        rvBookingList.setLayoutManager(new LinearLayoutManager(getContext()));
        showLoading("Loading bookings..");
        if(getActivity()!= null) {

            businessViewModel = new ViewModelProvider(getActivity()).get(BusinessViewModel.class);
            businessViewModel.getBusinessLiveData()
                    .observe(getViewLifecycleOwner(), business -> {
                     showToast("Welcome " + business.getName());
                    });

            businessViewModel.getExceptionsLiveData()
                    .observe(getViewLifecycleOwner(), e -> {
                            stopLoading();
                            showToast(e.getMessage());
                    });

            businessViewModel.getBookingStatusLiveData()
                    .observe(getViewLifecycleOwner(), this::showToast);

            businessViewModel.getBookingListLiveData()
                    .observe(getViewLifecycleOwner(), bookings -> {
                        bookingsListRvAdapter = new BookingsListRvAdapter(bookings);
                        rvBookingList.setAdapter(bookingsListRvAdapter);
                        stopLoading();
                    });

        }


        if(getActivity()!=null && getActivity().getIntent()!=null && getActivity().getIntent().getStringExtra("business")!=null) {
            businessViewModel.setBusiness(JsonHelper.fromJson(getActivity().getIntent().getStringExtra("business"), WheelsBusiness.class));
        }else {
            stopLoading();
        }


    }
    class BookingsListRvAdapter extends RecyclerView.Adapter<BookingsListRvAdapter.BookingsListViewHolder> {


        private List<Booking> bookings;
        public BookingsListRvAdapter(List<Booking> allBookings) {

            this.bookings = allBookings;
        }
        @NonNull
        @Override
        public BookingsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookingsListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull BookingsListViewHolder holder, int position) {
            Booking b = bookings.get(position);
            holder.bind(b);
        }

        @Override
        public int getItemCount() {
            return bookings.size();
        }

        class BookingsListViewHolder extends RecyclerView.ViewHolder  {
             ImageView bookingImageView;
             TextView bookingDate;
             TextView bookingCustomerName;
             TextView serviceType;
             Button decline; // 'Delete crud action'
             Button approve; // @TODO
            public BookingsListViewHolder(@NonNull View itemView) {
                super(itemView);
                bookingDate = itemView.findViewById(R.id.booking_date);
                bookingCustomerName = itemView.findViewById(R.id.booking_customer_name);
                serviceType = itemView.findViewById(R.id.booking_service_type);
                bookingImageView = itemView.findViewById(R.id.booking_image_view);
                decline = itemView.findViewById(R.id.decline_btn);
                approve = itemView.findViewById(R.id.approve_btn);
            }


            public void bind(Booking booking) {
                bookingCustomerName.setText(booking.getCustomerName());
                serviceType.setText(booking.getServiceType());
                bookingDate.setText(booking.getTime());
                Picasso.get().load(booking.getImageUrl()).into(bookingImageView);

                decline.setOnClickListener((v) -> businessViewModel.declineBooking(booking));

            }// stil here
        }
    }
}
