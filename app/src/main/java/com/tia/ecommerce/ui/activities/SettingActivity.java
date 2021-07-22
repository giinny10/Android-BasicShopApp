package com.tia.ecommerce.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.tia.ecommerce.R;
import com.tia.ecommerce.databinding.ActivitySettingBinding;
import com.tia.ecommerce.firestore.FirestoreClass;
import com.tia.ecommerce.model.User;
import com.tia.ecommerce.utils.Constants;

public class SettingActivity extends BaseActivity {
    private ActivitySettingBinding binding;
    private User userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpActionbar();
    }

    private void setUpActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_settings_activity);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void getUserDetails(){
        showLoadingProgress();
        FirestoreClass.getInstance().getUserDetails(this);
    }

    public void userDetailsSuccess(User user){
        userDetails = user;
        hideLoadingProgress();

        binding.tvEmail.setText(user.getEmail());
        binding.tvGender.setText(user.getGender());
        binding.tvMobileNumber.setText(user.getMobile());
        binding.tvName.setText(user.getFirstName() + " " + user.getLastName());
        Glide.with(this).load(user.getImage()).centerCrop().placeholder(R.drawable.ic_user_placeholder).into(binding.userPhoto);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetails();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void editUser(View view) {
        Intent intent = new Intent(SettingActivity.this, UserProfileActivity.class);
        intent.putExtra(Constants.EXTRA_USER_DETAILS, userDetails);
        startActivity(intent);
    }

    public void onClickLayoutAddressList(View view) {
        Intent intent = new Intent(this, AddressListActivity.class);
        startActivity(intent);
    }
}