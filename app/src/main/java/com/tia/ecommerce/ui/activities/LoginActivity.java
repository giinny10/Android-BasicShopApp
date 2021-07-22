package com.tia.ecommerce.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.tia.ecommerce.R;
import com.tia.ecommerce.databinding.ActivityLoginBinding;
import com.tia.ecommerce.firestore.FirestoreClass;
import com.tia.ecommerce.model.User;
import com.tia.ecommerce.utils.Constants;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView textView = findViewById(R.id.register_link_id);
        textView.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

    }

    public void handleLoginButtonClick(View view) {
        boolean isValid = validateLogin();
        if(isValid){
            String email = binding.etEmailId.getText().toString();
            String password = binding.etPasswordId.getText().toString();
            loginUserFirebase(email, password);
        }
    }

    private void loginUserFirebase(String email, String password) {
        showLoadingProgress();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    hideLoadingProgress();
                    if (task.isSuccessful()) {
                        FirestoreClass.getInstance().getUserDetails(LoginActivity.this);
                    } else {
                        hideLoadingProgress();
                        showErrorSnackBar(task.getException().getMessage(), true);
                    }
                });
    }

    private boolean validateLogin(){
        if(binding.etEmailId.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_email), true);
            return false;
        }else if(binding.etPasswordId.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_confirm_password), true);
            return false;
        }else return true;
    }

    public void userLoginSucess(User user){
        hideLoadingProgress();
        showErrorSnackBar(getResources().getString(R.string.success_msg_login), false);

        if(user.getProfileCompleted() == 0){
            Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user);
            startActivity(intent);
        }else{
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        }
        finish();
    }
}