package com.tia.ecommerce.firestore;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.ui.activities.AddProductActivity;
import com.tia.ecommerce.ui.activities.LoginActivity;
import com.tia.ecommerce.ui.activities.RegisterActivity;
import com.tia.ecommerce.ui.activities.SettingActivity;
import com.tia.ecommerce.ui.activities.UserProfileActivity;
import com.tia.ecommerce.model.User;
import com.tia.ecommerce.utils.Constants;

import java.util.Map;

public class FirestoreClass {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static final FirestoreClass instance = null;

    public FirebaseFirestore getFirestore(){
        return this.firestore;
    }

    public static FirestoreClass getInstance() {
        if (instance == null) return new FirestoreClass();
        else return instance;
    }

    public void registerUser(RegisterActivity activity, User userInfo) {
        firestore.collection(Constants.USERS)
                .document(userInfo.getUid())
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener(onSuccess -> {
                    activity.userRegisterSucess();
                })
                .addOnFailureListener(e -> {
                    Log.e(activity.getClass().getName(), "Error when register user", e);
                });
    }

    public String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = "";
        if (currentUser != null) currentUserId = currentUser.getUid();
        return currentUserId;
    }

    public void getUserDetails(Activity activity) {
        firestore.collection(Constants.USERS)
                .document(getCurrentUserId())
                .get()
                .addOnSuccessListener(document -> {
                    User user = document.toObject(User.class);
                    SharedPreferences sharedPreferences =
                            activity.getSharedPreferences(Constants.MYSHOP_PREFERENCES, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.LOGIN_USERNAME, user.getFirstName() + " " + user.getLastName());
                    editor.apply();

                    if (activity instanceof LoginActivity) {
                        ((LoginActivity) activity).userLoginSucess(user);
                    }else if(activity instanceof SettingActivity){
                        ((SettingActivity) activity).userDetailsSuccess(user);
                    }
                })
                .addOnFailureListener(e ->  {
                    if(activity instanceof LoginActivity){
                        ((LoginActivity) activity).hideLoadingProgress();
                    }else if (activity instanceof SettingActivity){
                        ((SettingActivity) activity).hideLoadingProgress();
                    }
                });
    }

    public void updateUserProfileData(UserProfileActivity activity, Map<String, Object> userHashMap){
        firestore.collection(Constants.USERS)
                .document(getCurrentUserId())
                .update(userHashMap)
                .addOnSuccessListener(onsucess -> {
                        activity.userProfileUpdateSuccess();
                })
                .addOnFailureListener(exception -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when updating user profile", exception);
                });
    }

    public void uploadImageToCloudStorage(Activity activity, Uri imageFileUrl, String imageType){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(imageType + System.currentTimeMillis() + "." + Constants.getFileExtension(activity, imageFileUrl));

        storageReference.putFile(imageFileUrl)
                .addOnSuccessListener(taskSnapshot -> {
                    Log.e("Firebase Image URL", taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                    // Get the downloadable url from the task snapshot
                    taskSnapshot.getMetadata().getReference().getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                if(activity instanceof UserProfileActivity){
                                    ((UserProfileActivity) activity).imageUploadSuccess(uri.toString());
                                }else if(activity instanceof AddProductActivity){
                                    ((AddProductActivity) activity).imageUploadSuccess(uri.toString());
                                }
                            });

                })
                .addOnFailureListener(exception -> {
                    if(activity instanceof UserProfileActivity){
                        ((UserProfileActivity) activity).hideLoadingProgress();
                    }else if(activity instanceof AddProductActivity){
                        ((AddProductActivity) activity).hideLoadingProgress();;
                    }
                    Log.e(activity.getClass().getName(), exception.getMessage(), exception);
                });
    }

    public void uploadProductDetails(AddProductActivity activity, Product productInfo){
        firestore.collection(Constants.PRODUCTS)
                .document()
                .set(productInfo, SetOptions.merge())
                .addOnSuccessListener(onSuccess -> {
                    activity.productUploadSuccess();
                })
                .addOnFailureListener(e -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when upload product detail", e);
                });
    }

}
