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
import com.example.wheelsapp.interfaces.OnWheelsCustomerExternalListener;
import com.example.wheelsapp.models.WheelsCustomer;

public class ClientRegistrationFragment extends WheelsFragment {



    EditText emailEt,passEt,fullNameEt,phoneEt;
    Button registerBtn;
    Button moveToBusinessRegistration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_client,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEt = view.findViewById(R.id.email_field_register);
        passEt = view.findViewById(R.id.password_field_register);
        fullNameEt = view.findViewById(R.id.fullName_field_login);
        registerBtn = view.findViewById(R.id.btn_signup_client_action);
        phoneEt = view.findViewById(R.id.phone_field_register);
        moveToBusinessRegistration = view.findViewById(R.id.move_to_business_registration_btn);
        moveToBusinessRegistration.setOnClickListener((v) -> {

        });
        registerBtn.setOnClickListener((v) -> {
            if(isValidFields(new EditText[] { emailEt,passEt,fullNameEt,phoneEt})) {
                showLoading("Signing up...");

                FirebaseManager.instance.createNewCustomer(fullNameEt.getText().toString(),
                        emailEt.getText().toString(), phoneEt.getText().toString(), passEt.getText().toString(), new OnWheelsCustomerExternalListener() {
                            @Override
                            public void onFailure(Exception e) {
                                stopLoading();
                                showToast(e.getMessage());
                            }

                            @Override
                            public void onSuccess(WheelsCustomer user) {
                                stopLoading();
                                NavHostFragment.findNavController(ClientRegistrationFragment.this).popBackStack();
                            }
                        });

            }
        });
    }
}
