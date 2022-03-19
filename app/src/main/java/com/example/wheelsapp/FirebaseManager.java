package com.example.wheelsapp;
import com.example.wheelsapp.interfaces.OnWheelsBusinessBookingsListener;
import com.example.wheelsapp.interfaces.OnWheelsBusinessListener;
import com.example.wheelsapp.interfaces.OnWheelsServicesListener;
import com.example.wheelsapp.interfaces.OnWheelsCustomerListener;
import com.example.wheelsapp.interfaces.WheelsListener;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;
import com.example.wheelsapp.models.WheelsService;
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

    private final DatabaseReference customers_ref = FirebaseDatabase.getInstance().getReference("users");
    private final DatabaseReference businesses_ref = FirebaseDatabase.getInstance().getReference("businesses");
    private final DatabaseReference bookings_ref = FirebaseDatabase.getInstance().getReference("services");
    public void getWheelsCustomer(String uid, OnWheelsCustomerListener listener) {
        assert FirebaseAuth.getInstance().getUid() !=null;
        customers_ref.child(uid)
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    WheelsCustomer u = dataSnapshot.getValue(WheelsCustomer.class);
                    listener.onSuccess(u);
                }).addOnFailureListener(listener::onFailure);
    }

    public void getWheelsBusiness(String bid, OnWheelsBusinessListener listener) {
        assert bid !=null;
        businesses_ref.child(bid)
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    WheelsBusiness b = dataSnapshot.getValue(WheelsBusiness.class);
                    if(b!=null) {
                        listener.onSuccess(b);
                    }else {
                        listener.onFailure(new Exception("Business with id " +  bid +" not found"));
                    }
                }).addOnFailureListener(listener::onFailure);
    }


    public void getAllBusinessBookings(String bid,OnWheelsBusinessBookingsListener listener) {
        businesses_ref
                .child(bid)
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    List<Booking> list = new ArrayList<>();
                    Booking next;
                    for(DataSnapshot child : dataSnapshot.getChildren()) {
                        next = child.getValue(Booking.class);
                        if(next!=null)
                        list.add(next);
                    }
                    listener.onSuccess(list);
                }).addOnFailureListener(listener::onFailure);
    }

    public void saveBusiness(WheelsBusiness business) {
        assert FirebaseAuth.getInstance().getUid() !=null;
        businesses_ref.child(FirebaseAuth.getInstance().getUid())
                .setValue(business);
    }
    public void saveCustomer (String uid, WheelsCustomer user) {
        customers_ref.child(uid)
                .setValue(user);
    }

    public void saveBooking(String bid, Booking service) {
        assert bid !=null;
        bookings_ref.child(bid)
                .push()
                .setValue(service);
    }


   //@TODO : Create a new customer
    public void createNewCustomer(String fullName,
                                  String email,
                                  String phoneNumber,
                                  String password,
                                  WheelsListener<WheelsCustomer> listener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {

                    WheelsCustomer newUser = new WheelsCustomer(email,fullName,phoneNumber);

                   // saveCustomer(new);
                    listener.onSuccess(newUser);
                }).addOnFailureListener(listener::onFailure);
    }


    // Signs in a customer by email pass
    public void signInCustomer(String email,String password,WheelsListener<WheelsCustomer> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                    if(authResult.getUser()!=null) {
                        getWheelsCustomer(authResult.getUser().getUid(), new OnWheelsCustomerListener() {
                            @Override
                            public void onFailure(Exception e) {
                                listener.onFailure(e);
                            }

                            @Override
                            public void onSuccess(WheelsCustomer customer) {
                                listener.onSuccess(customer);
                            }
                        });
                    }else {    listener.onFailure(new Exception("Something happened while fetching customer")); }
                }).addOnFailureListener(listener::onFailure);
    }
    // Signs in a business by email pass
    public void signInBusiness(String email,String password,WheelsListener<WheelsBusiness> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                    if(authResult.getUser()!=null) {
                        getWheelsBusiness(authResult.getUser().getUid(), new OnWheelsBusinessListener() {
                            @Override
                            public void onSuccess(WheelsBusiness business) {
                                listener.onSuccess(business);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                listener.onFailure(e);
                            }
                        });

                    }else {    listener.onFailure(new Exception("Something happened while fetching customer")); }
                }).addOnFailureListener(listener::onFailure);
    }




}
