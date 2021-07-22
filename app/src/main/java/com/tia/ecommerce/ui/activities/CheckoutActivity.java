package com.tia.ecommerce.ui.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tia.ecommerce.R;
import com.tia.ecommerce.databinding.ActivityCheckoutBinding;
import com.tia.ecommerce.firestore.CartFireStore;
import com.tia.ecommerce.firestore.FirestoreClass;
import com.tia.ecommerce.firestore.OrderFireStore;
import com.tia.ecommerce.firestore.ProductFireStore;
import com.tia.ecommerce.model.Address;
import com.tia.ecommerce.model.Cart;
import com.tia.ecommerce.model.Order;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.ui.adapters.CartListAdapter;
import com.tia.ecommerce.utils.Constants;

import java.util.ArrayList;

public class CheckoutActivity extends BaseActivity {
    private Address mAddDressDetail;
    private ActivityCheckoutBinding binding;
    private ArrayList<Product> mproducts;
    private ArrayList<Cart> mcarts;
    private int mSubTotal;
    private int mTotalAmount;
    private Order mOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpActionbar();

        mproducts = new ArrayList<>();
        mcarts = new ArrayList<>();

        if(getIntent().hasExtra(Constants.EXTRA_SELECTED_ADDRESS)){
            mAddDressDetail = getIntent().getParcelableExtra(Constants.EXTRA_SELECTED_ADDRESS);
        }

        if(mAddDressDetail != null){
            binding.tvCheckoutAddressType.setText(mAddDressDetail.getType());
            binding.tvCheckoutFullName.setText(mAddDressDetail.getName());
            binding.tvCheckoutAddress.setText(mAddDressDetail.getAddress() + ", " + mAddDressDetail.getZipCode());
            binding.tvCheckoutAdditionalNote.setText(mAddDressDetail.getAdditionalNote());
            if(!mAddDressDetail.getOtherDetails().isEmpty()){
                binding.tvCheckoutOtherDetails.setText(mAddDressDetail.getOtherDetails());
            }
            binding.tvCheckoutMobileNumber.setText(mAddDressDetail.getMobileNumber());
        }

        getProductList();
    }

    private void setUpActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_checkout_activity);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void getProductList(){
        showLoadingProgress();
        ProductFireStore.getInstance().getAllProductList(CheckoutActivity.this);
    }

    public void successAllProductList(ArrayList<Product> productList){
        mproducts = productList;
        getCartList();
    }

    private void getCartList(){
        CartFireStore.getInstance().getCartList(this);
    }

    public void successGetCartList(ArrayList<Cart> cartList){
        hideLoadingProgress();

        for (Product product: mproducts) {
            for (Cart cart: cartList) {
                if (product.getProduct_id().equals(cart.getProduct_id())) {
                    cart.setStock_quantity(product.getStock_quantity());
                }
            }
        }

        mcarts = cartList;

        binding.rvCartListItems.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCartListItems.setHasFixedSize(true);

        CartListAdapter cartListAdapter = new CartListAdapter(this, cartList, false);
        binding.rvCartListItems.setAdapter(cartListAdapter);

        for (Cart cart: mcarts) {
            int availableQuantity = Integer.parseInt(cart.getStock_quantity());
            if (availableQuantity > 0) {
                int price = Integer.parseInt(cart.getPrice());
                int quantity = Integer.parseInt(cart.getCart_quantity());
                mSubTotal += (price * quantity);
            }
        }
        binding.tvCheckoutSubTotal.setText(mSubTotal +" VND");
        binding.tvCheckoutShippingCharge.setText("10000 VND");

        if (mSubTotal > 0) {
            binding.llCheckoutPlaceOrder.setVisibility(View.VISIBLE);
            mTotalAmount = mSubTotal + 10000;
            binding.tvCheckoutTotalAmount.setText(mTotalAmount + " VND");
        } else {
            binding.llCheckoutPlaceOrder.setVisibility(View.GONE);
        }
    }

    public void onClickBtnPlaceOrder(View v){
        showLoadingProgress();
        if(mAddDressDetail != null){
            mOrderDetails = new Order(FirestoreClass.getInstance().getCurrentUserId(), mcarts, mAddDressDetail, "My order " + System.currentTimeMillis(),
                    mcarts.get(0).getImage(), mSubTotal+"", "10.000", mTotalAmount+"");
            OrderFireStore.getInstance().placeOrder(this, mOrderDetails);

        }

    }

    public void successPlaceOrder(){
        OrderFireStore.getInstance().updateAllDetails(this, mcarts, mOrderDetails);
    }

    public void successUpdateAllDetail(){
        hideLoadingProgress();
        Toast.makeText(this, getResources().getString(R.string.msg_order_successfullly), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}