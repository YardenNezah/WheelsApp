package com.example.wheelsapp.db.external;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Database;

import com.example.wheelsapp.interfaces.CustomerBookings;
import com.example.wheelsapp.interfaces.OnWheelsBusinessBookingsExternalListener;
import com.example.wheelsapp.interfaces.OnWheelsBusinessExternalListener;
import com.example.wheelsapp.interfaces.OnWheelsCustomerExternalListener;
import com.example.wheelsapp.interfaces.WheelsExternalListener;
import com.example.wheelsapp.models.Booking;
import com.example.wheelsapp.models.LatLng;
import com.example.wheelsapp.models.WheelsBusiness;
import com.example.wheelsapp.models.WheelsCustomer;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FirebaseManager {
    private FirebaseManager() {
    }

    public static FirebaseManager instance = new FirebaseManager();

    private final DatabaseReference customers_ref = FirebaseDatabase.getInstance().getReference("users");
    private final DatabaseReference businesses_ref = FirebaseDatabase.getInstance().getReference("businesses");
    private final DatabaseReference bookings_ref = FirebaseDatabase.getInstance().getReference("bookings");


    // listeners
    private ValueEventListener businessListListener;
    private ValueEventListener businessBookingsListener;
    private ValueEventListener customerBookingsListener;

    public void getWheelsCustomer(String uid, OnWheelsCustomerExternalListener listener) {
        assert FirebaseAuth.getInstance().getUid() != null;
        customers_ref.child(uid)
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    WheelsCustomer u = dataSnapshot.getValue(WheelsCustomer.class);
                    if (u == null) listener.onFailure(new Exception("No Customer with id " + uid));
                    else listener.onSuccess(u);
                }).addOnFailureListener(listener::onFailure);
    }


    public void getWheelsBusiness(String bid, OnWheelsBusinessExternalListener listener) {
        assert bid != null;
        businesses_ref.child(bid)
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    WheelsBusiness b = dataSnapshot.getValue(WheelsBusiness.class);
                    if (b != null) {
                        listener.onSuccess(b);
                    } else {
                        listener.onFailure(new Exception("Business with id " + bid + " not found"));
                    }
                }).addOnFailureListener(listener::onFailure);
    }


    public void removeBusinessListValueEventListener() {
        if (businessListListener != null) {
            businesses_ref.removeEventListener(businessListListener);
            businessListListener = null;
        }
    }

    public void removeBusinessBookingsListener() {
        if (businessBookingsListener != null) {
            bookings_ref.removeEventListener(businessBookingsListener);
            businessBookingsListener = null;
        }
    }

    public void removeCustomerBookingsListener() {
        if (customerBookingsListener != null) {
            bookings_ref.removeEventListener(customerBookingsListener);
            customerBookingsListener = null;
        }
    }

    public void getCustomerBookingList(String cid, WheelsExternalListener<CustomerBookings> listener) {
        customerBookingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Booking> reviewedBookings = new ArrayList<>();
                List<Booking> waitingBookings = new ArrayList<>();
                Booking b;
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (child.child(Booking.WAITING).child(cid).exists()) {

                        b = child.child(Booking.WAITING).child(cid).getValue(Booking.class);
                        if (b != null)
                            waitingBookings.add(b);
                    } else if (child.child(Booking.APPROVED).child(cid).exists()) {
                        b = child.child(Booking.APPROVED).child(cid).getValue(Booking.class);
                        if (b != null)
                            reviewedBookings.add(b);
                    } else if (child.child(Booking.DECLINED).child(cid).exists()) {
                        b = child.child(Booking.DECLINED).child(cid).getValue(Booking.class);
                        if (b != null)
                            reviewedBookings.add(b);
                    }
                }
                listener.onSuccess(new CustomerBookings(waitingBookings, reviewedBookings));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure(error.toException());
            }
        };
        bookings_ref.addValueEventListener(customerBookingsListener);
    }

    public void getBusinessList(WheelsExternalListener<List<WheelsBusiness>> listener) {
        businessListListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<WheelsBusiness> businesses = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    WheelsBusiness b = child.getValue(WheelsBusiness.class);
                    if (b != null)
                        businesses.add(b);
                }
                listener.onSuccess(businesses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure(error.toException());
            }
        };

        businesses_ref.addValueEventListener(businessListListener);
    }


    public void declineBooking(Booking booking, WheelsExternalListener<String> listener) {
        booking.setStatus(Booking.DECLINED);
        DatabaseReference businessBookingsReference = bookings_ref.child(booking.getBusinessId());
        businessBookingsReference
                .child(Booking.WAITING)
                .child(booking.getId())
                .removeValue()
                .addOnSuccessListener(unused -> businessBookingsReference
                        .child(Booking.DECLINED)
                        .child(booking.getId())
                        .setValue(booking)
                        .addOnSuccessListener(unused1 -> listener.onSuccess("Booking successfully Declined"))
                        .addOnFailureListener(listener::onFailure))
                .addOnFailureListener(listener::onFailure);
    }

    public void approveBooking(Booking booking, WheelsExternalListener<String> listener) {
        booking.setStatus(Booking.APPROVED);
        DatabaseReference businessBookingsReference = bookings_ref.child(booking.getBusinessId());
        businessBookingsReference
                .child(Booking.WAITING)
                .child(booking.getId())
                .removeValue()
                .addOnSuccessListener(unused -> businessBookingsReference
                        .child(Booking.APPROVED)
                        .child(booking.getId())
                        .setValue(booking)
                        .addOnSuccessListener(unused1 -> listener.onSuccess("Booking successfully Approved"))
                        .addOnFailureListener(listener::onFailure))
                .addOnFailureListener(listener::onFailure);
    }


    //uploads an image to firebase
    public void uploadImage(Uri uri, String path, WheelsExternalListener<String> urlListener) {
        StorageReference ref = FirebaseStorage.getInstance()
                .getReference()
                .child("images/")
                .child(path);
        ref.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(imageUri -> urlListener.onSuccess(imageUri.toString()))
                        .addOnFailureListener(urlListener::onFailure))
                .addOnFailureListener(urlListener::onFailure);
    }


    public void getAllBusinessBookings(String bid, OnWheelsBusinessBookingsExternalListener listener) {

        businessBookingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Booking> list = new ArrayList<>();
                Booking next;
                for (DataSnapshot child : snapshot.child(Booking.DECLINED).getChildren()) {
                    next = child.getValue(Booking.class);
                    if (next != null)
                        list.add(next);
                }
                for (DataSnapshot child : snapshot.child(Booking.APPROVED).getChildren()) {
                    next = child.getValue(Booking.class);
                    if (next != null)
                        list.add(next);
                }
                for (DataSnapshot child : snapshot.child(Booking.WAITING).getChildren()) {
                    next = child.getValue(Booking.class);
                    if (next != null)
                        list.add(next);
                }
                listener.onSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure(error.toException());
            }
        };
        bookings_ref
                .child(bid)
                .addValueEventListener(businessBookingsListener);
    }


    // saves a business by owner id (Firebase user uid)
    public void saveBusinessWithBusinessOwner(String oid, WheelsBusiness business) {
        businesses_ref.child(oid)
                .setValue(business);
    }

    public void saveCustomer(String uid, WheelsCustomer user) {
        customers_ref.child(uid)
                .setValue(user);
    }

    public void saveBooking(String bid, Booking service, Uri image, WheelsExternalListener<String> listener) {
        assert bid != null;
        assert FirebaseAuth.getInstance().getUid()!=null;
        DatabaseReference ref = bookings_ref.child(bid).child(Booking.WAITING)
                .child(FirebaseAuth.getInstance().getUid());
        assert ref.getKey() != null;
        service.setId(ref.getKey());
        uploadImage(image, "bookings/" + ref.getKey(), new WheelsExternalListener<String>() {
            @Override
            public void onSuccess(String imageUrl) {
                service.setImageUrl(imageUrl);
                ref.setValue(service)
                        .addOnSuccessListener((unused -> listener.onSuccess("Successfully booked treatment")))
                        .addOnFailureListener(listener::onFailure);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure(e);
            }
        });

    }


    public void createNewBusiness(String email, String password, String bName, String loction, String bPhoneNumber, LatLng coordinates,
                                  Uri image, OnWheelsBusinessExternalListener listener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    if (authResult.getUser() != null) {

                        uploadImage(image, "businesses/" + authResult.getUser().getUid(),
                                new WheelsExternalListener<String>() {

                                    @Override
                                    public void onSuccess(String imageUrl) {
                                        WheelsBusiness wheelsBusiness = new WheelsBusiness(bPhoneNumber, bName, loction, coordinates, imageUrl, email, authResult.getUser().getUid());
                                        saveBusinessWithBusinessOwner(authResult.getUser().getUid(), wheelsBusiness);
                                        listener.onSuccess(wheelsBusiness);
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        listener.onFailure(e);
                                    }
                                });
                    }
                }).addOnFailureListener(listener::onFailure);

    }


    public void createNewCustomer(String fullName,
                                  String email,
                                  String phoneNumber,
                                  String password,
                                  WheelsExternalListener<WheelsCustomer> listener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    if (authResult.getUser() != null) {
                        WheelsCustomer newUser = new WheelsCustomer(email, fullName, phoneNumber);
                        newUser.setCustomerId(authResult.getUser().getUid());
                        saveCustomer(authResult.getUser().getUid(), newUser);
                        listener.onSuccess(newUser);
                    }
                }).addOnFailureListener(listener::onFailure);
    }


    // Signs in a customer by email pass
    public void signInCustomer(String email, String password, WheelsExternalListener<WheelsCustomer> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    if (authResult.getUser() != null) {
                        getWheelsCustomer(authResult.getUser().getUid(), new OnWheelsCustomerExternalListener() {
                            @Override
                            public void onFailure(Exception e) {
                                listener.onFailure(e);
                                FirebaseAuth.getInstance().signOut();
                            }

                            @Override
                            public void onSuccess(WheelsCustomer customer) {
                                listener.onSuccess(customer);
                            }
                        });
                    } else {
                        listener.onFailure(new Exception("Something happened while fetching customer"));
                    }
                }).addOnFailureListener(listener::onFailure);
    }


    // Signs in a business by email pass
    public void signInBusiness(String email, String password, WheelsExternalListener<WheelsBusiness> listener) {

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    if (authResult.getUser() != null) {
                        getWheelsBusiness(authResult.getUser().getUid(), new OnWheelsBusinessExternalListener() {
                            @Override
                            public void onSuccess(WheelsBusiness business) {
                                listener.onSuccess(business);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                listener.onFailure(e);
                                FirebaseAuth.getInstance().signOut();
                            }
                        });

                    } else {
                        listener.onFailure(new Exception("Something happened while fetching customer"));
                    }
                }).addOnFailureListener(listener::onFailure);
    }


}
