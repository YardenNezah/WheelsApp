package com.example.wheelsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wheelsapp.main.fragments.BookingFragment;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.WheelsBusiness;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BusinessesFragment extends Fragment {


    RecyclerView rvBusinessList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_businesses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    class BusinessListRvAdapter extends RecyclerView.Adapter<BusinessesFragment.BusinessListRvAdapter.BusinessListViewHolder> {

        private List<WheelsBusiness> businesses;
        public BusinessListRvAdapter(List<WheelsBusiness> allBusinesses) {

            this.businesses = allBusinesses;
        }
        @NonNull
        @Override
        public BusinessesFragment.BusinessListRvAdapter.BusinessListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BusinessesFragment.BusinessListRvAdapter.BusinessListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull BusinessesFragment.BusinessListRvAdapter.BusinessListViewHolder holder, int position) {
            WheelsBusiness b = businesses.get(position);
            holder.bind(b);
        }

        @Override
        public int getItemCount() {
            return businesses.size();
        }

        class BusinessListViewHolder extends RecyclerView.ViewHolder  {
            ImageView businessLogo;
            TextView businessLocation;
            TextView businessName;
            public BusinessListViewHolder(@NonNull View itemView) {
                super(itemView);

            }


            public void bind(WheelsBusiness business) {


            }
        }
    }
}