package com.tia.ecommerce.ui.activities;

import android.app.Dialog;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.tia.ecommerce.R;

public class BaseActivity extends AppCompatActivity {
    private Dialog loadingProgress;
    private boolean doubleBackToExitApp = false;

    public void showErrorSnackBar(String message, boolean errorMessage){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();

        if(errorMessage){
            snackbarView.setBackgroundColor(ContextCompat.getColor(BaseActivity.this, R.color.colorSnackBarError));
        }else{
            snackbarView.setBackgroundColor(ContextCompat.getColor(BaseActivity.this, R.color.colorSnackBarSuccess));
        }
        snackbar.show();
    }

    public void showLoadingProgress(){
        loadingProgress = new Dialog(this);
        loadingProgress.setContentView(R.layout.dialog_progess);
        loadingProgress.setCancelable(false);
        loadingProgress.setCanceledOnTouchOutside(false);
        loadingProgress.show();
    }

    public void hideLoadingProgress(){
        if(!this.isFinishing() && loadingProgress != null){
            loadingProgress.dismiss();

        }
    }

    public void doubleBackToExitApp(){
        if(doubleBackToExitApp){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitApp = true;
        Toast.makeText(this, getResources().getString(R.string.please_click_back_again_to_exit), Toast.LENGTH_SHORT).show();;
        new Handler().postDelayed( () -> doubleBackToExitApp = false, 2000);
    }
}