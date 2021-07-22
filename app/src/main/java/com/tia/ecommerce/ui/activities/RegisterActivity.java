package com.tia.ecommerce.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tia.ecommerce.R;
import com.tia.ecommerce.databinding.ActivityRegisterBinding;
import com.tia.ecommerce.firestore.FirestoreClass;
import com.tia.ecommerce.model.User;

import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity {
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setUpActionbar();

        loginLinkListener();

    }

    private void setUpActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_register_activity_id);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }

    private void loginLinkListener(){
        TextView textView = findViewById(R.id.login_link_id);
        textView.setOnClickListener(v -> {
           onBackPressed();
        });
    }


    public void handleRegisterButtonClick(View view) {
        boolean isValid = validateRegister();

        if(isValid){
            String email = binding.etEmailId.getText().toString().trim();
            String password = binding.etPasswordId.getText().toString().trim();
            registerUserFirebase(email, password);
        }

    }

    private boolean validateRegister(){
        if(binding.etFirstnameId.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_first_name), true);
            return false;
        }else if(binding.etLastnameId.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_last_name), true);
            return false;
        }
        else if(binding.etEmailId.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_email), true);
            return false;
        }
        else if(!Pattern.compile(EMAIL_REGEX).matcher(binding.etEmailId.getText().toString()).matches()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_invalid_mail), true);
            return false;
        }
        else if(binding.etPasswordId.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_password), true);
            return false;
        }
        else if(!Pattern.compile(PASSWORD_REGEX).matcher(binding.etPasswordId.getText().toString()).matches()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_invalid_password), true);
            return false;
        }
        else if(binding.etConfirmPasswordId.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_confirm_password), true);
            return false;
        }
        else if(!binding.etPasswordId.getText().toString().equals(binding.etConfirmPasswordId.getText().toString())){
            showErrorSnackBar(getResources().getString(R.string.err_msg_password_and_confirm_password_mismatch), true);
            return false;
        }
        else if(!binding.checkboxTermsAndConditionId.isChecked()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_agree_terms_and_condition), true);
            return false;
        }
        else {
            return true;
        }
    }


    private void registerUserFirebase(String email, String password){
        showLoadingProgress();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser firebaseUser = task.getResult().getUser();
                User user = new User(firebaseUser.getUid(), binding.etFirstnameId.getText().toString(),
                        binding.etLastnameId.getText().toString(), binding.etEmailId.getText().toString());

                FirestoreClass.getInstance().registerUser(RegisterActivity.this, user);
            }else{
                hideLoadingProgress();
                showErrorSnackBar(task.getException().getMessage(), true);
            }
        });
    }

    public void userRegisterSucess(){
        hideLoadingProgress();
        showErrorSnackBar(getResources().getString(R.string.success_msg_register), false);
        Handler handler = new Handler();
        handler.postDelayed(() -> this.loginLinkListener(), 2000);
    }
}