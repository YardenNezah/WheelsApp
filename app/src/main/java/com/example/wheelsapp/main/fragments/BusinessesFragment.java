package com.example.wheelsapp.main.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsFragment;
import com.example.wheelsapp.db.external.FirebaseManager;
import com.example.wheelsapp.interfaces.WheelsExternalListener;
import com.example.wheelsapp.main.CustomerViewModel;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;
import com.example.wheelsapp.utilities.JsonHelper;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BusinessesFragment extends WheelsFragment {


    RecyclerView rvBusinessList;
    BusinessListRvAdapter rvAdapter;

    private CustomerViewModel customerViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_businesses, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBusinessList = view.findViewById(R.id.rvBusinessList);
        rvBusinessList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        showLoading("Loading all businesses..");
        if (getActivity() != null) {
            customerViewModel = new ViewModelProvider(getActivity()).get(CustomerViewModel.class);
            customerViewModel.getCustomerLiveData()
                    .observe(getViewLifecycleOwner(), customer -> {
                        // do whatever with customer
                        Toast.makeText(getContext(), "Welcome " + customer.getCustomerName(), Toast.LENGTH_SHORT).show();
                    });

            customerViewModel.getExceptionsLiveData()
                    .observe(getViewLifecycleOwner(), e -> showToast(e.getMessage()));
            customerViewModel.getBusinessesLiveData()
                    .observe(getViewLifecycleOwner(), businesses -> {
                        rvAdapter = new BusinessListRvAdapter(businesses);
                        rvBusinessList.setAdapter(rvAdapter);
                        stopLoading();
                    });
        }
        if (getActivity() != null && getActivity().getIntent() != null && getActivity().getIntent().getStringExtra("customer") != null) {
            customerViewModel.setCustomer(JsonHelper.fromJson(getActivity().getIntent().getStringExtra("customer"), WheelsCustomer.class));
        }

    }


    class BusinessListRvAdapter extends RecyclerView.Adapter<BusinessesFragment.BusinessListRvAdapter.BusinessListViewHolder> {

        private List<WheelsBusiness> businesses;

        public BusinessListRvAdapter(List<WheelsBusiness> allBusinesses) {
            this.businesses = allBusinesses;
        }

        @NonNull
        @Override
        public BusinessesFragment.BusinessListRvAdapter.BusinessListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BusinessesFragment.BusinessListRvAdapter.BusinessListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.business_item, parent, false));
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

        class BusinessListViewHolder extends RecyclerView.ViewHolder {
            ImageView businessLogo;
            TextView businessLocation;
            TextView businessName;

            Button bookTreatment;

            public BusinessListViewHolder(@NonNull View itemView) {
                super(itemView);
                businessLogo = itemView.findViewById(R.id.business_image_view_list);
                businessLocation = itemView.findViewById(R.id.business_location_tv_list);
                businessName = itemView.findViewById(R.id.business_name_tv_list);
                bookTreatment = itemView.findViewById(R.id.book_treatment_btn);

            }


            public void bind(WheelsBusiness business) {
                Picasso.get().load(business.getImage()).into(businessLogo);
                businessName.setText(business.getName());
                businessLocation.setText(business.getLocation());
                bookTreatment.setOnClickListener((v) -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("business", JsonHelper.toJson(business));
                    NavHostFragment.findNavController(BusinessesFragment.this)
                            .navigate(R.id.action_businessesFragment_to_scheduleFragment,bundle);

                });
            }
        }
    }
}