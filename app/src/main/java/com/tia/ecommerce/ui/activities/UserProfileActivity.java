package com.tia.ecommerce.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.tia.ecommerce.R;
import com.tia.ecommerce.databinding.ActivityUserProfileBinding;
import com.tia.ecommerce.firestore.FirestoreClass;
import com.tia.ecommerce.model.User;
import com.tia.ecommerce.utils.Constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends BaseActivity {
    private ActivityUserProfileBinding binding;
    private User user;
    private Uri selectedImageFileUri;
    private String userProfileImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if(getIntent().hasExtra(Constants.EXTRA_USER_DETAILS)){
            user = getIntent().getParcelableExtra(Constants.EXTRA_USER_DETAILS);
        }
        binding.etEmailId.setHint(user.getEmail());
        if(user.getProfileCompleted() == 0){
            binding.userProfileTitleId.setText(getResources().getString(R.string.title_complete_profile));
            binding.etFirstNameId.setHint(user.getFirstName());
            binding.etLastNameId.setHint(user.getLastName());
            binding.etFirstNameId.setEnabled(false);
            binding.etLastNameId.setEnabled(false);
        }else{
            setUpActionbar();
            binding.userProfileTitleId.setText(getResources().getString(R.string.title_edit_profile));
            userProfileImageUrl = user.getImage();
            Glide.with(this).load(userProfileImageUrl).centerCrop().placeholder(R.drawable.ic_user_placeholder).into(binding.userPhotoId);
            binding.etFirstNameId.setText(user.getFirstName());
            binding.etLastNameId.setText(user.getLastName());
            if(user.getMobile() != ""){
                binding.etMobileNumberId.setText(user.getMobile());
            }
            if(user.getGender() == Constants.MALE){
                binding.rbMale.setChecked(true);
            }else{
                binding.rbFemale.setChecked(true);
            }
        }

    }

    public void updateProfileImage(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Constants.showImageChooser(this);
        }else{
            ActivityCompat.requestPermissions(this, Arrays.asList(Manifest.permission.READ_EXTERNAL_STORAGE).toArray(new String[0]),
                    Constants.READ_STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this);
            }else{
                Toast.makeText(this, getResources().getString(R.string.read_storage_permission_denied), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    selectedImageFileUri=  data.getData();
                    binding.userPhotoId.setImageURI(selectedImageFileUri);
                }
            }
        }
    }

    private boolean validateUserProfileInput(){
        if(binding.etMobileNumberId.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_mobile_number), true);
            return false;
        }else if(binding.etAddressId.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_address), true);
            return false;
        }else return true;
    }

    public void onSaveProfileButton(View view) {
        if(validateUserProfileInput()){
            showLoadingProgress();
            if(selectedImageFileUri != null){
                FirestoreClass.getInstance().uploadImageToCloudStorage(this, selectedImageFileUri, Constants.USER_PROFILE_IMAGE);
            }else{
                updateUserProfileDetail();
            }

        }
    }

    private void updateUserProfileDetail(){
        Map<String, Object> userHashMap = new HashMap<>();
        String firstName = binding.etFirstNameId.getText().toString().trim();
        String lastName = binding.etLastNameId.getText().toString().trim();
        String mobileNumber = binding.etMobileNumberId.getText().toString().trim();
        String address = binding.etAddressId.getText().toString().trim();
        String gender = binding.rbMale.isChecked() ? Constants.MALE : Constants.FEMALE;

        if(firstName != user.getFirstName()){
            userHashMap.put(Constants.FIRST_NAME, firstName);
        }
        if(lastName != user.getLastName()){
            userHashMap.put(Constants.LAST_NAME, lastName);
        }
        if(!mobileNumber.isEmpty() && mobileNumber != user.getMobile()) {
            userHashMap.put(Constants.MOBILE, mobileNumber);
        }
        if(!address.isEmpty()) userHashMap.put(Constants.ADDRESS, address);
        userHashMap.put(Constants.GENDER, gender);
        if(!userProfileImageUrl.isEmpty())userHashMap.put(Constants.IMAGE, userProfileImageUrl);
        userHashMap.put(Constants.COMPLETE_PROFILE, 1);
        FirestoreClass.getInstance().updateUserProfileData(this, userHashMap);
    }

    public void userProfileUpdateSuccess(){
        hideLoadingProgress();
        startActivity(new Intent(UserProfileActivity.this, DashboardActivity.class));
        finish();
    }

    public void imageUploadSuccess(String url){
        userProfileImageUrl = url;
        updateUserProfileDetail();
    }

    private void setUpActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_user_profile_activity_id);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
    }
