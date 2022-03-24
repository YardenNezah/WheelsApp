package com.example.wheelsapp.main.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsFragment;
import com.example.wheelsapp.main.CustomerViewModel;
import com.example.wheelsapp.main.SettingsDialog;
import com.example.wheelsapp.models.Booking;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryFragment extends WheelsFragment {
    private CustomerViewModel customerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    Button settings;
    RecyclerView rvWaiting;
    RecyclerView rvReviewed;

    LinearLayout layout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null)
            customerViewModel = new ViewModelProvider(getActivity()).get(CustomerViewModel.class);
        rvReviewed = view.findViewById(R.id.rvReviewed);
        rvWaiting = view.findViewById(R.id.rvWaiting);
        layout = view.findViewById(R.id.layout_customer_history_bookingList);
        rvWaiting.setLayoutManager(new LinearLayoutManager(getContext()));
        rvReviewed.setLayoutManager(new LinearLayoutManager(getContext()));
        settings = view.findViewById(R.id.settings_button);

        settings.setOnClickListener(v -> {
            SettingsDialog settingsDialog = new SettingsDialog();
            if (getActivity() != null && getActivity().getApplication() != null) {
                settingsDialog.initializeRepo(getActivity().getApplication());
                settingsDialog.show(getChildFragmentManager(), "Dialog");
            }

        });


        customerViewModel.getReviewedBookings()
                .observe(getViewLifecycleOwner(), bookings -> {
                    HistoryBookingsListRvAdapter reviewedAdapter = new HistoryBookingsListRvAdapter(bookings, true);
                    rvReviewed.setAdapter(reviewedAdapter);
                });

        customerViewModel.getWaitingBookings()
                .observe(getViewLifecycleOwner(), bookings -> {
                    HistoryBookingsListRvAdapter waitingAdapter = new HistoryBookingsListRvAdapter(bookings, true);
                    rvWaiting.setAdapter(waitingAdapter);
                });

        customerViewModel.getThemeLiveData()
                .observe(getViewLifecycleOwner(), theme ->

                {
                    if (theme != null)
                        layout.setBackgroundColor(theme.getColor());
                });
        customerViewModel.getExceptionsLiveData()
                .observe(getViewLifecycleOwner(), e -> showToast(e.getMessage()));
        customerViewModel.getAllCustomerBookings(FirebaseAuth.getInstance().getUid());
    }


    public static class HistoryBookingsListRvAdapter extends RecyclerView.Adapter<HistoryFragment.HistoryBookingsListRvAdapter.HistoryBookingsListViewHolder> {


        private List<Booking> bookings;
        private boolean isCustomer;

        public HistoryBookingsListRvAdapter(List<Booking> allBookings, boolean Customer) {
            this.bookings = allBookings;
            this.isCustomer = Customer;
        }

        @NonNull
        @Override
        public HistoryFragment.HistoryBookingsListRvAdapter.HistoryBookingsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HistoryFragment.HistoryBookingsListRvAdapter.HistoryBookingsListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryFragment.HistoryBookingsListRvAdapter.HistoryBookingsListViewHolder holder, int position) {
            Booking b = bookings.get(position);
            holder.bind(b);
        }

        @Override
        public int getItemCount() {
            return bookings.size();
        }

        class HistoryBookingsListViewHolder extends RecyclerView.ViewHolder {
            ImageView bookingImageView;
            TextView bookingDate;
            TextView bookingCustomerName;
            TextView serviceType;
            TextView status;

            public HistoryBookingsListViewHolder(@NonNull View itemView) {
                super(itemView);
                bookingDate = itemView.findViewById(R.id.history_booking_date);
                bookingCustomerName = itemView.findViewById(R.id.history_customer_name);
                serviceType = itemView.findViewById(R.id.history_service_type);
                bookingImageView = itemView.findViewById(R.id.history_booking_image_view);
                status = itemView.findViewById(R.id.status_text);
            }


            public void bind(Booking booking) {
                bookingCustomerName.setText(booking.getCustomerName());
                serviceType.setText(booking.getServiceType());
                bookingDate.setText("Treatment date: "  + "\n" + booking.getDate() + "\n" +  booking.getTime());
                Picasso.get().load(booking.getImageUrl()).into(bookingImageView);
                if (isCustomer)
                    bookingCustomerName.setVisibility(View.GONE);
                status.setText(booking.getStatus());
                status.setTextColor(booking.getStatus().equals(Booking.DECLINED) ? Color.RED : booking.getStatus().equals(Booking.APPROVED) ? Color.GREEN : Color.BLUE);
            }
        }
    }

}
