package com.tia.ecommerce.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.tia.ecommerce.R;
import com.tia.ecommerce.utils.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.MYSHOP_PREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Constants.LOGIN_USERNAME, "");
        TextView textView = findViewById(R.id.tv_main);
        textView.setText("Hello " + username);

    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}