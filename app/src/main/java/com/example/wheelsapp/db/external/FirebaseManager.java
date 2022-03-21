package com.example.wheelsapp.db.external;
import android.net.Uri;

import com.example.wheelsapp.interfaces.OnWheelsBusinessBookingsExternalListener;
import com.example.wheelsapp.interfaces.OnWheelsBusinessExternalListener;
import com.example.wheelsapp.interfaces.OnWheelsCustomerExternalListener;
import com.example.wheelsapp.interfaces.WheelsExternalListener;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.LatLng;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {
    private FirebaseManager() {}

    public static FirebaseManager instance = new FirebaseManager();

    private final DatabaseReference customers_ref = FirebaseDatabase.getInstance().getReference("users");
    private final DatabaseReference businesses_ref = FirebaseDatabase.getInstance().getReference("businesses");
    private final DatabaseReference bookings_ref = FirebaseDatabase.getInstance().getReference("services");
    public void getWheelsCustomer(String uid, OnWheelsCustomerExternalListener listener) {
        assert FirebaseAuth.getInstance().getUid() !=null;
        customers_ref.child(uid)
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    WheelsCustomer u = dataSnapshot.getValue(WheelsCustomer.class);
                    listener.onSuccess(u);
                }).addOnFailureListener(listener::onFailure);
    }

    public void getWheelsBusiness(String bid, OnWheelsBusinessExternalListener listener) {
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


    public void getAllBusinessBookings(String bid, OnWheelsBusinessBookingsExternalListener listener) {
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


    // saves a business by owner id (Firebase user uid)
    public void saveBusinessWithBusinessOwner(String oid, WheelsBusiness business) {
        businesses_ref.child(oid)
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


    public void createNewBusiness(String email, String password, String bName, String bPhoneNumber, LatLng coordinates,
                                  Uri image, OnWheelsBusinessExternalListener listener) {


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                    if(authResult.getUser()!= null) {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("/images/businesses/ " + authResult.getUser().getUid());
                        storageReference.putFile(image)
                                .addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            WheelsBusiness wheelsBusiness = new WheelsBusiness(bPhoneNumber,bName,coordinates,uri.toString(),email,authResult.getUser().getUid());
                                            saveBusinessWithBusinessOwner(authResult.getUser().getUid(),wheelsBusiness);
                                            listener.onSuccess(wheelsBusiness);
                                        }).addOnFailureListener(listener::onFailure)).addOnFailureListener(listener::onFailure);

                    }
                }).addOnFailureListener(listener::onFailure);

    }


    public void createNewCustomer(String fullName,
                                  String email,
                                  String phoneNumber,
                                  String password,
                                  WheelsExternalListener<WheelsCustomer> listener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                    if(authResult.getUser()!= null ) {
                        WheelsCustomer newUser = new WheelsCustomer(email, fullName, phoneNumber);
                        saveCustomer(authResult.getUser().getUid(),newUser);
                        listener.onSuccess(newUser);
                    }
                }).addOnFailureListener(listener::onFailure);
    }


    // Signs in a customer by email pass
    public void signInCustomer(String email, String password, WheelsExternalListener<WheelsCustomer> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                    if(authResult.getUser()!=null) {
                        getWheelsCustomer(authResult.getUser().getUid(), new OnWheelsCustomerExternalListener() {
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
    public void signInBusiness(String email, String password, WheelsExternalListener<WheelsBusiness> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                    if(authResult.getUser()!=null) {
                        getWheelsBusiness(authResult.getUser().getUid(), new OnWheelsBusinessExternalListener() {
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
