package com.tia.ecommerce.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tia.ecommerce.R;
import com.tia.ecommerce.databinding.ActivityCartListBinding;
import com.tia.ecommerce.firestore.CartFireStore;
import com.tia.ecommerce.firestore.ProductFireStore;
import com.tia.ecommerce.model.Cart;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.ui.adapters.CartListAdapter;
import com.tia.ecommerce.utils.Constants;

import java.util.ArrayList;

public class CartListActivity extends BaseActivity {
    private ActivityCartListBinding binding;
    private ArrayList<Product> products;
    private ArrayList<Cart> carts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpActionbar();
    }

    private void setUpActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_cart_list_activity);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onResume() {
        super.onResume();
        //getCartList();
        getAllProductList();
    }

    public void getAllProductList(){
        showLoadingProgress();
        ProductFireStore.getInstance().getAllProductList(this);
    }

    public void successAllProductList(ArrayList<Product> productList){
        products = productList;
        getCartList();
    }

    private void getCartList(){
        CartFireStore.getInstance().getCartList(this);
    }


    public void successGetCartList(ArrayList<Cart> cartList){
        hideLoadingProgress();

        for(Product product: products){
            Log.e(getClass().getName(), product.toString());
            for(Cart cart: cartList){
                if(product.getProduct_id().equals(cart.getProduct_id())){
                    cart.setStock_quantity(product.getStock_quantity());

                    if(Integer.parseInt(product.getStock_quantity()) == 0){
                        cart.setCart_quantity(product.getStock_quantity());
                    }
                }
            }
        }

        this.carts = cartList;

        if(carts.size() > 0){
            binding.rvCartItemsList.setVisibility(View.VISIBLE);
            binding.btnCheckout.setVisibility(View.VISIBLE);
            binding.tvNoCartItemFound.setVisibility(View.GONE);

            binding.rvCartItemsList.setLayoutManager(new LinearLayoutManager(this));
            binding.rvCartItemsList.setHasFixedSize(true);

            CartListAdapter cartListAdapter = new CartListAdapter(this, carts, true);
            binding.rvCartItemsList.setAdapter(cartListAdapter);

            double subTotal = 0.0;
            for(Cart cart: carts){
                int availableQuantity = Integer.parseInt( cart.getStock_quantity());
                if(availableQuantity > 0){
                    double price = Double.parseDouble(cart.getPrice());
                    int quantity = Integer.parseInt(cart.getCart_quantity());
                    subTotal += (price * quantity);
                }

            }
            binding.tvSubTotal.setText(subTotal+ " VND");
            binding.tvShippingCharge.setText("10.000 VND");

            if(subTotal > 0){
                binding.llCheckout.setVisibility(View.VISIBLE);
                double total = subTotal + 10;
                binding.tvTotalAmount.setText(total + " VND");
            }else{
                binding.llCheckout.setVisibility(View.GONE);
            }
        }else{
            binding.rvCartItemsList.setVisibility(View.GONE);
            binding.btnCheckout.setVisibility(View.GONE);
            binding.tvNoCartItemFound.setVisibility(View.VISIBLE);
        }
    }

    public void successRemoveCartItem(){
        hideLoadingProgress();
        showErrorSnackBar(getResources().getString(R.string.msg_item_removed_successfully), false);
        getCartList();
    }

    public void successUpdateCartItem(){
        hideLoadingProgress();
        getCartList();
    }

    public void onClickBtnCheckOut(View view) {
        Intent intent = new Intent(this, AddressListActivity.class);
        intent.putExtra(Constants.EXTRA_SELECT_ADDRESS, true);
        startActivity(intent);
    }
}