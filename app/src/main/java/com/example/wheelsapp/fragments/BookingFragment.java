package com.example.wheelsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheelsapp.FirebaseManager;
import com.example.wheelsapp.R;
import com.example.wheelsapp.interfaces.OnWheelsBusinessBookingsListener;
import com.example.wheelsapp.models.WheelsBusiness;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookingFragment extends Fragment {


    private RecyclerView rvBusinessList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_business_list,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBusinessList = view.findViewById(R.id.rvBusinessList);
        rvBusinessList.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseManager.instance.getAllWheelsBusinesses(new OnWheelsBusinessBookingsListener() {
            @Override
            public void onSuccess(List<WheelsBusiness> allBusinesses) {
                BusinessListRvAdapter adapter = new BusinessListRvAdapter(allBusinesses);
                rvBusinessList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }



    public void moveToBusiness(WheelsBusiness business) {
      //@TODO : Move to Business
    }

    class BusinessListRvAdapter extends RecyclerView.Adapter<BusinessListRvAdapter.BusinessListViewHolder> {



        private List<WheelsBusiness> businessList;
        public BusinessListRvAdapter(List<WheelsBusiness> allBusinesses) {

            this.businessList = allBusinesses;
        }
        @NonNull
        @Override
        public BusinessListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BusinessListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.business_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull BusinessListViewHolder holder, int position) {


            WheelsBusiness b = businessList.get(position);

            holder.bind(b);
        }

        @Override
        public int getItemCount() {
            return businessList.size();
        }

        class BusinessListViewHolder extends RecyclerView.ViewHolder  {



             ImageView businessLogo;
             TextView businessName;
             Button showMoreDetails;
            public BusinessListViewHolder(@NonNull View itemView) {
                super(itemView);
                businessLogo = itemView.findViewById(R.id.business_logo);
                businessName = itemView.findViewById(R.id.business_name);
                showMoreDetails = itemView.findViewById(R.id.btn_show_details_business);
            }


            public void bind(WheelsBusiness business) {
                businessName.setText(business.getName());
                Picasso.get().load(business.getImage()).into(businessLogo);
                showMoreDetails.setOnClickListener(view -> moveToBusiness(business));
            }
        }
    }
}
