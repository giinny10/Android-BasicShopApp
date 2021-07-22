package com.tia.ecommerce.firestore;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.tia.ecommerce.model.Address;
import com.tia.ecommerce.model.Cart;
import com.tia.ecommerce.ui.activities.AddEditAddressActivity;
import com.tia.ecommerce.ui.activities.AddressListActivity;
import com.tia.ecommerce.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class AddressFireStore {
    private FirebaseFirestore firestore = FirestoreClass.getInstance().getFirestore();
    private static final AddressFireStore instance = null;
    private AddressFireStore() {
    }
    public static AddressFireStore getInstance() {
        if (instance == null) return new AddressFireStore();
        else return instance;
    }

    public void addAddress(AddEditAddressActivity activity, Address addressInfo){
        firestore.collection(Constants.ADDRESSES)
                .document()
                .set(addressInfo, SetOptions.merge())
                .addOnSuccessListener(document -> {
                    activity.successAddEditAddress();
                })
                .addOnFailureListener(e -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when add address", e);
                });
    }

    public void getAddressList(AddressListActivity activity){
        firestore.collection(Constants.ADDRESSES)
                .whereEqualTo(Constants.USER_ID, FirestoreClass.getInstance().getCurrentUserId())
                .get()
                .addOnSuccessListener(document -> {
                    ArrayList<Address> addresses = new ArrayList<>();
                    for (DocumentSnapshot snapshot: document.getDocuments()) {
                        Address address = snapshot.toObject(Address.class);
                        address.setId(snapshot.getId());
                        addresses.add(address);
                    }
                    activity.successGetAddressList(addresses);
                })
                .addOnFailureListener(e -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when get address list", e);
                });
    }

    public void updateAddressDetail(AddEditAddressActivity activity, Address addressInfo, String addressId){
        firestore.collection(Constants.ADDRESSES)
                .document(addressId)
                .set(addressInfo, SetOptions.merge())
                .addOnSuccessListener(document -> {
                    activity.successAddEditAddress();
                })
                .addOnFailureListener(e -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when update address detail", e);
                });
    }

    public void deleteAddressDetail(AddressListActivity activity, String addressId){
        firestore.collection(Constants.ADDRESSES)
                .document(addressId)
                .delete()
                .addOnSuccessListener(document -> {
                    activity.successDeleteAddress();
                })
                .addOnFailureListener(e -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when delete address detail", e);
                });
    }
}
