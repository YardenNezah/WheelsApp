package com.example.wheelsapp.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wheelsapp.MainActivity;
import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends WheelsActivity {



    EditText emailEt,passEt;
    Button loginBtn,toSignUp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        loginBtn = findViewById(R.id.btn_signin);
        emailEt = findViewById(R.id.email_field_login);
        passEt = findViewById(R.id.password_field_login);

toSignUp = findViewById(R.id.btn_to_signup);
toSignUp.setOnClickListener((v) -> startActivity(new Intent(this,RegisterActivity.class)));
        loginBtn.setOnClickListener((v) -> {
            if(isValidFields(new EditText[] {emailEt,passEt})) {

               showLoading("Signing in...");
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEt.getText().toString(),passEt.getText()
                .toString()).addOnSuccessListener(authResult ->  {
                    stopLoading();
                    startActivity(new Intent(this, MainActivity.class));
                })
                        .addOnFailureListener(e -> {
                            stopLoading();
                            showToast(e.getMessage()); });
            }
    });
    }
}
