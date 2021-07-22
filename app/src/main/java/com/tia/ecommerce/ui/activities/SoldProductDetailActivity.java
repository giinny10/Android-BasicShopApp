package com.tia.ecommerce.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tia.ecommerce.R;
import com.tia.ecommerce.databinding.ActivityMyOrderDetailActivityBinding;
import com.tia.ecommerce.databinding.ActivitySoldProductDetailBinding;
import com.tia.ecommerce.model.SoldProduct;
import com.tia.ecommerce.ui.adapters.CartListAdapter;
import com.tia.ecommerce.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SoldProductDetailActivity extends AppCompatActivity {
    private ActivitySoldProductDetailBinding binding;
    private SoldProduct mSoldProductDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySoldProductDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setUpActionbar();

        mSoldProductDetail = new SoldProduct();
        if(getIntent().hasExtra(Constants.EXTRA_SOLD_PRODUCT_DETAIL)){
            mSoldProductDetail = getIntent().getParcelableExtra(Constants.EXTRA_SOLD_PRODUCT_DETAIL);
        }

        setupUI();

    }

    private void setUpActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_sold_product_details_activity);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void setupUI(){
        binding.tvSoldProductDetailsId.setText(mSoldProductDetail.getOrder_id());
        String dateFormat = "dd MMM yyyy HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mSoldProductDetail.getOrder_date());

        String orderDateTime = formatter.format(calendar.getTime());
        binding.tvSoldProductDetailsDate.setText(orderDateTime);

        Glide.with(this).load(mSoldProductDetail.getImage()).centerCrop().into(binding.ivProductItemImage);

        binding.tvProductItemName.setText(mSoldProductDetail.getTitle());
        binding.tvProductItemPrice.setText(mSoldProductDetail.getPrice());
        binding.tvSoldProductQuantity.setText(mSoldProductDetail.getSold_quantity());

        binding.tvSoldDetailsAddressType.setText(mSoldProductDetail.getAddress().getType());
        binding.tvSoldDetailsFullName.setText(mSoldProductDetail.getAddress().getName());
        binding.tvSoldDetailsAddress.setText(mSoldProductDetail.getAddress().getAddress() + ", " + mSoldProductDetail.getAddress().getZipCode());
        binding.tvSoldDetailsAdditionalNote.setText(mSoldProductDetail.getAddress().getAdditionalNote());

        if(!mSoldProductDetail.getAddress().getOtherDetails().isEmpty()){
            binding.tvSoldDetailsOtherDetails.setVisibility(View.VISIBLE);
            binding.tvSoldDetailsOtherDetails.setText(mSoldProductDetail.getAddress().getOtherDetails());
        }else{
            binding.tvSoldDetailsOtherDetails.setVisibility(View.GONE);
        }

        binding.tvSoldDetailsMobileNumber.setText(mSoldProductDetail.getAddress().getMobileNumber());
        binding.tvSoldProductSubTotal.setText(mSoldProductDetail.getSub_total_amount());
        binding.tvSoldProductShippingCharge.setText(mSoldProductDetail.getShipping_charge());
        binding.tvSoldProductTotalAmount.setText(mSoldProductDetail.getTotal_amount());
    }
}