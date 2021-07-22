package com.tia.ecommerce.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import com.tia.ecommerce.databinding.ActivityAddProductBinding;
import com.tia.ecommerce.firestore.FirestoreClass;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.utils.Constants;

import java.util.Arrays;

public class AddProductActivity extends BaseActivity {
    private ActivityAddProductBinding binding;
    private Uri selectedImageFileUri;
    private String productImageURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpActionbar();
    }

    private void setUpActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_add_product_activity);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void onClickuploadProductImage(View view) {
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
                    binding.addUpdateProduct.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_vector_edit));
                    selectedImageFileUri=  data.getData();
                    Glide.with(this).load(selectedImageFileUri).centerCrop().into(binding.productImage);
                }
            }
        }
    }
    public void onClickBtnCreateProduct(View view) {
        if(validateProductDetails()){
            uploadProductImage();
        }
    }

    private boolean validateProductDetails(){
        if(selectedImageFileUri == null){
            showErrorSnackBar(getResources().getString(R.string.err_msg_select_product_image), true);
            return false;
        }else if(binding.etProductTitle.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_product_title), true);
            return false;
        }else if(binding.etProductPrice.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_product_price), true);
            return false;
        }
        else if(binding.etProductDescription.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_product_description), true);
            return false;
        }
        else if(binding.etProductQuantity.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_product_quantity), true);
            return false;
        } else return true;
    }


    private void uploadProductImage(){
        showLoadingProgress();
        FirestoreClass.getInstance().uploadImageToCloudStorage(this, selectedImageFileUri, Constants.PRODUCT_IMAGE);
    }

    public void imageUploadSuccess(String url){
        productImageURL = url;
        uploadProductDetails();
    }

    private void uploadProductDetails(){
        String username = getSharedPreferences(Constants.MYSHOP_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.LOGIN_USERNAME, "");
        Product product = new Product(
                FirestoreClass.getInstance().getCurrentUserId(),
                username,
                binding.etProductTitle.getText().toString(),
                binding.etProductPrice.getText().toString(),
                binding.etProductDescription.getText().toString(),
                binding.etProductQuantity.getText().toString(),
                productImageURL);
        FirestoreClass.getInstance().uploadProductDetails(this, product);
    }

    public void productUploadSuccess(){
        hideLoadingProgress();
        showErrorSnackBar(getResources().getString(R.string.product_uploaded_success_message), false);
        finish();
    }
}