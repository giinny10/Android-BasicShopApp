package com.tia.ecommerce.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tia.ecommerce.R;
import com.tia.ecommerce.databinding.ActivityMyOrderDetailActivityBinding;
import com.tia.ecommerce.model.Order;
import com.tia.ecommerce.ui.adapters.CartListAdapter;
import com.tia.ecommerce.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyOrderDetailActivity extends AppCompatActivity {
    private Order orderDetail;
    private ActivityMyOrderDetailActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrderDetailActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpActionbar();
        orderDetail = new Order();
        if(getIntent().hasExtra(Constants.EXTRA_MY_ORDER_DETAILS)){
            orderDetail = getIntent().getParcelableExtra(Constants.EXTRA_MY_ORDER_DETAILS);
        }

        setupUI();
    }

    private void setUpActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_my_order_details_activity);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void setupUI(){
        binding.tvOrderDetailsId.setText(orderDetail.getTitle());
        String dateFormat = "dd MMM yyyy HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(orderDetail.getOrder_datetime());

        String orderDateTime = formatter.format(calendar.getTime());
        binding.tvOrderDetailsDate.setText(orderDateTime);

        // If the difference in hours is 2 or less then the order status will be PENDING.
        // If the difference in hours is 3 or greater then 1 then the order status will be PROCESSING.
        // And, if the difference in hours is 4 or greater then the order status will be DELIVERED.
        Long diffInMilliSeconds = System.currentTimeMillis() - orderDetail.getOrder_datetime();
        Long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMilliSeconds);
        if(diffInHours < 2){
            binding.tvOrderStatus.setText(getResources().getString(R.string.order_status_pending));
            binding.tvOrderStatus.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
        else if(diffInHours < 3){
            binding.tvOrderStatus.setText(getResources().getString(R.string.order_status_in_process));
            binding.tvOrderStatus.setTextColor(ContextCompat.getColor(this, R.color.colorOrderStatusInProcess));
        }
        else{
            binding.tvOrderStatus.setText(getResources().getString(R.string.order_status_delivered));
            binding.tvOrderStatus.setTextColor(ContextCompat.getColor(this, R.color.colorOrderStatusDelivered));
        }

        binding.rvMyOrderItemsList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMyOrderItemsList.setHasFixedSize(true);

        CartListAdapter cartListAdapter = new CartListAdapter(this, orderDetail.getItems(), false);
        binding.rvMyOrderItemsList.setAdapter(cartListAdapter);

        binding.tvMyOrderDetailsAddressType.setText(orderDetail.getAddress().getType());
        binding.tvMyOrderDetailsFullName.setText(orderDetail.getAddress().getName());
        binding.tvMyOrderDetailsAddress.setText(orderDetail.getAddress().getAddress() + ", " + orderDetail.getAddress().getZipCode());
        binding.tvMyOrderDetailsAdditionalNote.setText(orderDetail.getAddress().getAdditionalNote());

        if(!orderDetail.getAddress().getOtherDetails().isEmpty()){
            binding.tvMyOrderDetailsOtherDetails.setVisibility(View.VISIBLE);
            binding.tvMyOrderDetailsOtherDetails.setText(orderDetail.getAddress().getOtherDetails());
        }else{
            binding.tvMyOrderDetailsOtherDetails.setVisibility(View.GONE);
        }
        binding.tvMyOrderDetailsMobileNumber.setText(orderDetail.getAddress().getMobileNumber());
        binding.tvOrderDetailsSubTotal.setText(orderDetail.getSub_total_amount());
        binding.tvOrderDetailsShippingCharge.setText(orderDetail.getShipping_charge());
        binding.tvOrderDetailsTotalAmount.setText(orderDetail.getTotal_amount());
    }
}