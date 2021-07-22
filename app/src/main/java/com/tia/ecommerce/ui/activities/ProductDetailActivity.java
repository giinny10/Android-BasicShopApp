package com.tia.ecommerce.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.tia.ecommerce.R;
import com.tia.ecommerce.databinding.ActivityProductDetailBinding;
import com.tia.ecommerce.firestore.CartFireStore;
import com.tia.ecommerce.firestore.FirestoreClass;
import com.tia.ecommerce.firestore.ProductFireStore;
import com.tia.ecommerce.model.Cart;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.utils.Constants;

public class ProductDetailActivity extends BaseActivity {
    private String productId = "";
    private ActivityProductDetailBinding binding;
    private Product productDetail;
    private String mProductOwnerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setUpActionbar();

        if(getIntent().hasExtra(Constants.EXTRA_PRODUCT_ID)){
            this.productId = getIntent().getStringExtra(Constants.EXTRA_PRODUCT_ID);
            Log.e("product id: ", this.productId);
        }
        if(getIntent().hasExtra(Constants.EXTRA_PRODUCT_OWNER_ID)){
            mProductOwnerId = getIntent().getStringExtra(Constants.EXTRA_PRODUCT_OWNER_ID);
        }

        if(FirestoreClass.getInstance().getCurrentUserId().equals(mProductOwnerId)){
            binding.btnAddToCart.setVisibility(View.GONE);
            binding.btnGoToCart.setVisibility(View.GONE);
        }else{
            binding.btnAddToCart.setVisibility(View.VISIBLE);
        }

        getProductDetails();
    }

    private void setUpActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_product_details_activity);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void getProductDetails(){
        showLoadingProgress();
        ProductFireStore.getInstance().getProductDetails(this, productId);
    }

    public void successProductDetail(Product product) {
        productDetail = product;
        Glide.with(this).load(product.getImage()).centerCrop().into(binding.ivProductDetailImage);
        binding.tvProductDetailsTitle.setText(product.getTitle());
        binding.tvProductDetailsPrice.setText(product.getPrice() + "VND");
        binding.tvProductDetailsDescription.setText(product.getDescription());
        binding.tvProductDetailsStockQuantity.setText(product.getStock_quantity());

        if(product.getStock_quantity().equals("0")){
            hideLoadingProgress();
            binding.btnAddToCart.setVisibility(View.GONE);
            binding.tvProductDetailsStockQuantity.setText(getResources().getString(R.string.lbl_out_of_stock));
            binding.tvProductDetailsStockQuantity.setTextColor(Color.RED);
        }else{
            if(FirestoreClass.getInstance().getCurrentUserId().equals(product.getUser_id())){
                hideLoadingProgress();
            }else{
                CartFireStore.getInstance().checkIfItemExistInCart(this, productId);
            }
        }

    }

    public void onClickAddToCart(View view) {
        Cart cart = new Cart(FirestoreClass.getInstance().getCurrentUserId(),
                mProductOwnerId,
                productId,
                productDetail.getTitle(),
                productDetail.getPrice(),
                productDetail.getImage(),
                Constants.DEFAULT_CART_QUANTITY);
       showLoadingProgress();
        CartFireStore.getInstance().addCartItems(this, cart);

    }

    public void successAddToCart(){
        hideLoadingProgress();
        showErrorSnackBar(getResources().getString(R.string.success_message_item_added_to_cart), false);
        binding.btnAddToCart.setVisibility(View.GONE);
        binding.btnGoToCart.setVisibility(View.VISIBLE);
    }

    public void productExistInCart(){
        hideLoadingProgress();
        binding.btnAddToCart.setVisibility(View.GONE);
        binding.btnGoToCart.setVisibility(View.VISIBLE);
    }

    public void onClickGoToCart(View view) {
        startActivity(new Intent(this, CartListActivity.class));
    }
}