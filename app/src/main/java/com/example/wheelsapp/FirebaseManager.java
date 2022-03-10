package com.example.wheelsapp;
import com.example.wheelsapp.interfaces.OnWheelsBusinessListener;
import com.example.wheelsapp.interfaces.OnWheelsServicesListener;
import com.example.wheelsapp.interfaces.OnWheelsUserListener;
import com.example.wheelsapp.interfaces.WheelsListener;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsService;
import com.example.wheelsapp.models.WheelsUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {
    private FirebaseManager() {}

    public static FirebaseManager instance = new FirebaseManager();
    private WheelsUser currentUser;

    private final DatabaseReference users_ref = FirebaseDatabase.getInstance().getReference("users");
    private final DatabaseReference businesses_ref = FirebaseDatabase.getInstance().getReference("businesses");
    private final DatabaseReference services_ref = FirebaseDatabase.getInstance().getReference("services");

    public void getCurrentWheelsUser(OnWheelsUserListener listener) {
        if(currentUser!=null)
            listener.onSuccess(currentUser);
        else getWheelsUser(listener);
    }

    public void getWheelsUser(OnWheelsUserListener listener) {
        assert FirebaseAuth.getInstance().getUid() !=null;
        users_ref.child(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    WheelsUser u = dataSnapshot.getValue(WheelsUser.class);
                    if(u!=null) {
                       listener.onSuccess(u);
                       this.currentUser = u;
                    }else {
                        listener.onFailure(new Exception("User not found"));
                    }
                }).addOnFailureListener(listener::onFailure);
    }

    public void getWheelsBusiness(String businessProviderId, OnWheelsBusinessListener listener) {
        assert businessProviderId !=null;
        businesses_ref.child(businessProviderId)
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    WheelsBusiness b = dataSnapshot.getValue(WheelsBusiness.class);
                    if(b!=null) {
                        listener.onSuccess(b);
                    }else {
                        listener.onFailure(new Exception("Business not found"));
                    }
                }).addOnFailureListener(listener::onFailure);
    }

    public void getWheelsServices(OnWheelsServicesListener listener) {
        assert FirebaseAuth.getInstance().getUid() !=null;
        services_ref.child(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    List<WheelsService> list = new ArrayList<>();
                    WheelsService s;
                    for(DataSnapshot serviceSnap : dataSnapshot.getChildren()) {
                        s = serviceSnap.getValue(WheelsService.class);
                        if(s!=null)
                            list.add(s);
                    }
                    if(list.size() > 0) {
                        listener.onSuccess(list);
                    }else {
                        listener.onFailure(new Exception("Services for "+ FirebaseAuth.getInstance().getCurrentUser().getEmail() + " were not found"));
                    }
                }).addOnFailureListener(listener::onFailure);
    }

    public void saveBusiness(WheelsBusiness business) {
        assert FirebaseAuth.getInstance().getUid() !=null;
        businesses_ref.child(FirebaseAuth.getInstance().getUid())
                .setValue(business);
    }
    public void saveUser(WheelsUser user) {
        assert FirebaseAuth.getInstance().getUid() !=null;
        users_ref.child(FirebaseAuth.getInstance().getUid())
                .setValue(user);
    }
    public void saveService(WheelsService service) {
        assert FirebaseAuth.getInstance().getUid() !=null;
        services_ref.child(FirebaseAuth.getInstance().getUid())
                .push()
                .setValue(service);
    }

    public void createNewAuthUser(String fullName,
                                  String email,
                                  String password,
                                  boolean businessOwner,
                                  WheelsListener<FirebaseUser> listener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                    listener.onSuccess(authResult.getUser());
                    WheelsUser newUser = new WheelsUser(email,fullName,businessOwner);
                    saveUser(newUser);
                }).addOnFailureListener(listener::onFailure);
    }

    public void signInUser(String email,String password,WheelsListener<WheelsUser> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                    if(authResult.getUser()!=null) {
                        getWheelsUser(new OnWheelsUserListener() {
                            @Override
                            public void onFailure(Exception e) {
                                listener.onFailure(e);
                            }

                            @Override
                            public void onSuccess(WheelsUser user) {
                                listener.onSuccess(user);
                            }
                        });
                    }
                }).addOnFailureListener(listener::onFailure);
    }



}
