package com.example.wheelsapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends WheelsActivity {



    Button sendLink;

    EditText sendPasswordResetEmail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgotpass);

        sendLink = findViewById(R.id.btn_sendForgotLink);
        sendPasswordResetEmail = findViewById(R.id.email_field_forgotpass);
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
