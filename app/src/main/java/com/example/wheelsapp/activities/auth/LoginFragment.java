package com.example.wheelsapp.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wheelsapp.MainActivity;
import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsFragment;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends WheelsFragment {



    EditText emailEt,passEt;
    Button loginBtn,toSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        loginBtn = view.findViewById(R.id.btn_signin);
        emailEt = view.findViewById(R.id.email_field_login);
        passEt = view.findViewById(R.id.password_field_login);

        toSignUp = view.findViewById(R.id.btn_to_signup);
        toSignUp.setOnClickListener((v) -> startActivity(new Intent(this, getContext())));
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
