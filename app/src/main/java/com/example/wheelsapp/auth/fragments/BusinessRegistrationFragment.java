package com.example.wheelsapp.auth.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.wheelsapp.db.external.FirebaseManager;
import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsFragment;
import com.example.wheelsapp.interfaces.OnWheelsBusinessExternalListener;
import com.example.wheelsapp.models.WheelsBusiness;

public class BusinessRegistrationFragment extends WheelsFragment {
    EditText emailEt,passEt,businessNameEt,businessPhoneEt;
    Button registerBtn;
    Button moveToBusinessRegistration;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_business,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEt = view.findViewById(R.id.email_field_register);
        passEt = view.findViewById(R.id.password_field_register);
        businessNameEt = view.findViewById(R.id.businessName_field_register);
        registerBtn = view.findViewById(R.id.btn_signup_business_action);
        businessPhoneEt = view.findViewById(R.id.phone_field_register_business);
        businessPhoneEt = view.findViewById(R.id.phone_field_register_business);
        registerBtn.setOnClickListener((v) -> {
            if(isValidFields(new EditText[] { emailEt,passEt,businessPhoneEt,businessNameEt})) {
                showLoading("Signing up...");
                //TODO: coordinates & image
                FirebaseManager.instance.createNewBusiness(emailEt.getText().toString(), passEt.getText().toString(),
                        businessNameEt.getText().toString(), businessPhoneEt.getText().toString(), null,
                        null, new OnWheelsBusinessExternalListener() {
                            @Override
                            public void onSuccess(WheelsBusiness business) {
                                //TODO: Cache business locally
                                    NavHostFragment.findNavController(BusinessRegistrationFragment.this).popBackStack();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                    showToast(e.getMessage());
                            }
                        });
            }
        });
    }
}
