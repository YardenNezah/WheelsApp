package com.example.wheelsapp.auth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.wheelsapp.db.external.FirebaseManager;
import com.example.wheelsapp.interfaces.WheelsExternalListener;
import com.example.wheelsapp.main.activities.MainActivity;
import com.example.wheelsapp.R;
import com.example.wheelsapp.WheelsFragment;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;
import com.example.wheelsapp.utilities.JsonHelper;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends WheelsFragment {

    EditText emailEt,passEt;
    Button loginBtn,toClientSignUp,toBusinessSignUp;


    RadioGroup loginType;
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

        loginType = view.findViewById(R.id.login_type);
        toBusinessSignUp = view.findViewById(R.id.btn_signup_business);
        toClientSignUp = view.findViewById(R.id.btn_signup_client);
        toClientSignUp.setOnClickListener((v) ->  {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_clientRegistrationFragment);
        });
        toBusinessSignUp.setOnClickListener((v) ->  {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_businessRegistrationFragment);
        });
        loginBtn.setOnClickListener((v) -> {
            if(isValidFields(new EditText[] {emailEt,passEt})) {

                showLoading("Signing in...");
                // client login
                if(loginType.getCheckedRadioButtonId() == R.id.business_rb) {
                    System.out.println("Business");
                    FirebaseManager.instance
                            .signInBusiness(emailEt.getText().toString(), passEt.getText().toString()
                                    , new WheelsExternalListener<WheelsBusiness>() {
                                        @Override
                                        public void onSuccess(WheelsBusiness business) {

                                            Intent i = new Intent(getActivity(),MainActivity.class);
                                            i.putExtra("business", JsonHelper.toJson(business));
                                            startActivity(i);
                                            if(getActivity()!=null)
                                                getActivity().finish();
                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                            stopLoading();
                                            Toast.makeText(getContext(),"No Corresponding Business Found / Wrong Credentials",Toast.LENGTH_SHORT).show();

                                        }
                                    });
                }else {

                    FirebaseManager.instance
                            .signInCustomer(emailEt.getText().toString(), passEt.getText().toString()
                                    , new WheelsExternalListener<WheelsCustomer>() {
                                        @Override
                                        public void onSuccess(WheelsCustomer customer) {
                                            Intent i = new Intent(getActivity(),MainActivity.class);
                                            i.putExtra("customer", JsonHelper.toJson(customer));
                                            startActivity(i);
                                            stopLoading();
                                            if(getActivity()!=null)
                                                getActivity().finish();
                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                            stopLoading();
                                            Toast.makeText(getContext(),"No Corresponding Client Found / Wrong Credentials",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                }

            }
        });
    }
}
