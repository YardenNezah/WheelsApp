package com.example.wheelsapp.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsFragment;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassFragment extends WheelsFragment {



    Button sendLink;

    EditText sendPasswordResetEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgotpass,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendLink = view.findViewById(R.id.btn_sendForgotLink);
        sendPasswordResetEmail = view.findViewById(R.id.email_field_forgotpass);
        sendLink.setOnClickListener((v) -> {
            showLoading("Sending link..");
            if(isValidFields(new EditText[] { sendPasswordResetEmail })) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(sendPasswordResetEmail.getText().toString())
                        .addOnSuccessListener(unused -> stopLoading())
                        .addOnFailureListener(e ->  {
                            stopLoading();
                            showToast(e.getMessage());
                        });
            }
        });
    }

}
