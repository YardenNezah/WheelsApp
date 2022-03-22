package com.example.wheelsapp.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheelsapp.R;
import com.example.wheelsapp.models.Booking;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookingFragment extends Fragment {


    private RecyclerView rvBookingList;
    private BookingsListRvAdapter bookingsListRvAdapter;

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
            public BookingsListViewHolder(@NonNull View itemView) {
                super(itemView);
                bookingDate = itemView.findViewById(R.id.booking_date);
                bookingCustomerName = itemView.findViewById(R.id.booking_customer_name);
                serviceType = itemView.findViewById(R.id.booking_service_type);
                bookingImageView = itemView.findViewById(R.id.booking_image_view);
            }


            public void bind(Booking booking) {
                bookingCustomerName.setText(booking.getCustomerName());
                serviceType.setText(booking.getServiceType());
                bookingDate.setText(booking.getTime());
                Picasso.get().load(booking.getImageUrl()).into(bookingImageView);

            }
        }
    }
}
