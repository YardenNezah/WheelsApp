package com.example.wheelsapp.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.example.wheelsapp.FirebaseManager;
import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsActivity;
import com.example.wheelsapp.models.WheelsUser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends WheelsActivity {



    EditText emailEt,passEt,fullNameEt;
    Button registerBtn;
    RadioGroup clientType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailEt = findViewById(R.id.email_field_register);
        passEt = findViewById(R.id.password_field_register);
        fullNameEt = findViewById(R.id.fullName_field_login);
        registerBtn = findViewById(R.id.btn_signup);
        clientType = findViewById(R.id.userType_rg);

        registerBtn.setOnClickListener((v) -> {
            if(isValidFields(new EditText[] { emailEt,passEt,fullNameEt})) {
                showLoading("Signing up...");
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(emailEt.getText().toString(),passEt.getText().toString())
                        .addOnSuccessListener(authResult -> {
                            WheelsUser newUser = new WheelsUser(emailEt.getText().toString(),fullNameEt.getText().toString(),clientType.getCheckedRadioButtonId() == R.id.userType_businessOwner);
                            FirebaseManager.instance.saveUser(newUser);
                            stopLoading();
                            finish();
                        }).addOnFailureListener(e -> showToast(e.getMessage()));
            }
        });
    }
}
