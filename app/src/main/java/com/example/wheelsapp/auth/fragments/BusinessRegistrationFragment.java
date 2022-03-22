package com.example.wheelsapp.auth.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.wheelsapp.db.external.FirebaseManager;
import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsFragment;
import com.example.wheelsapp.interfaces.OnWheelsBusinessExternalListener;
import com.example.wheelsapp.models.LatLng;
import com.example.wheelsapp.models.WheelsBusiness;

public class BusinessRegistrationFragment extends WheelsFragment {
    EditText emailEt,passEt,businessNameEt,businessPhoneEt;
    Button registerBtn;
    Button moveToClientRegistrationBtn;

    Uri selectedImageUri;
    ImageView businessLogo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_business,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEt = view.findViewById(R.id.email_field_register_business);
        passEt = view.findViewById(R.id.password_field_register_business);
        moveToClientRegistrationBtn = view.findViewById(R.id.move_to_client_registration_btn);
        businessNameEt = view.findViewById(R.id.businessName_field_register);
        businessPhoneEt = view.findViewById(R.id.phone_field_register_business);
        businessLogo = view.findViewById(R.id.business_logo_pick);

        registerBtn = view.findViewById(R.id.btn_signup_business_action);


        businessLogo.setOnClickListener((v) -> {
            openGallery();
        });
        registerBtn.setOnClickListener((v) -> {

            if(selectedImageUri==null) {
                showToast("Please pick a logo");
            }
            else if(isValidFields(new EditText[] { emailEt,passEt,businessPhoneEt,businessNameEt})) {
                showLoading("Signing up...");
                //TODO: coordinates & image
                FirebaseManager.instance.createNewBusiness(emailEt.getText().toString(), passEt.getText().toString(),
                        businessNameEt.getText().toString(),"Kfar Saba", businessPhoneEt.getText().toString(), new LatLng(5,5),
                        selectedImageUri, new OnWheelsBusinessExternalListener() {
                            @Override
                            public void onSuccess(WheelsBusiness business) {
                                //TODO: Cache business locally
                                stopLoading();
                                    NavHostFragment.findNavController(BusinessRegistrationFragment.this).popBackStack();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                stopLoading();
                                    showToast(e.getMessage());
                            }
                        });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null) {
            Uri iData  = data.getData();
            selectedImageUri = iData;
            businessLogo.setImageURI(iData);
        }
    }
}
