package com.tia.ecommerce.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.tia.ecommerce.R;
import com.tia.ecommerce.databinding.ActivityAddEditAddressBinding;
import com.tia.ecommerce.firestore.AddressFireStore;
import com.tia.ecommerce.firestore.FirestoreClass;
import com.tia.ecommerce.model.Address;
import com.tia.ecommerce.utils.Constants;

public class AddEditAddressActivity extends BaseActivity {
    private ActivityAddEditAddressBinding binding;
    private Address addressDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditAddressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpActionbar();

        this.bindingAddressDetailToView();

        binding.rgType.setOnCheckedChangeListener((v, checkedId)-> {
            if(checkedId == R.id.rb_other){
                binding.tilOtherDetails.setVisibility(View.VISIBLE);
            }else{
                binding.tilOtherDetails.setVisibility(View.GONE);
            }
        });
    }

    private void setUpActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_add_edit_address_activity);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void onCliCkBtnAddAddress(View view) {
        saveAddressToFirebase();
    }

    private void saveAddressToFirebase(){
        String fullname = binding.etFullName.getText().toString();
        String phoneNumber = binding.etPhoneNumber.getText().toString();
        String address = binding.etAddress.getText().toString();
        String zipCode = binding.etZipCode.getText().toString();
        String additionalNotes = binding.etAdditionalNote.getText().toString();
        String otherDetails = binding.etOtherDetails.getText().toString();

        if(validateData()){
            showLoadingProgress();
            String addressType = binding.rbHome.isChecked() ? Constants.HOME : binding.rbOffice.isChecked()
                    ? Constants.OFFICE : Constants.OTHER;
            Address addressModel = new Address(FirestoreClass.getInstance().getCurrentUserId(),
                    fullname, phoneNumber, address, zipCode, additionalNotes, addressType, otherDetails);
            if(addressDetail != null && !addressDetail.getId().isEmpty()){
                AddressFireStore.getInstance().updateAddressDetail(this, addressModel, addressDetail.getId());
            }else{
                AddressFireStore.getInstance().addAddress(this, addressModel);
            }

        }

    }

    public boolean validateData(){
        if(binding.etFullName.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_please_enter_full_name), true);
            return false;
        }
        else if(binding.etPhoneNumber.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_please_enter_phone_number), true);
            return false;
        }
        else if(binding.etAddress.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_please_enter_address), true);
            return false;
        }
        else if(binding.etZipCode.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_please_enter_zip_code), true);
            return false;
        }
        else if(binding.rbOther.isChecked() && binding.etZipCode.getText().toString().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.err_msg_please_enter_zip_code), true);
            return false;
        }
        return true;
    }

    public void successAddEditAddress(){
        hideLoadingProgress();
        if(addressDetail != null && !addressDetail.getId().isEmpty()){
            showErrorSnackBar(getResources().getString(R.string.msg_your_address_updated_successfully), false);
        }else{
            showErrorSnackBar(getResources().getString(R.string.err_your_address_added_successfully), false);
        }

        setResult(RESULT_OK);
        finish();
    }

    public void bindingAddressDetailToView(){
        if(getIntent().hasExtra(Constants.EXTRA_ADDRESS_DETAIL)){
            addressDetail = getIntent().getParcelableExtra(Constants.EXTRA_ADDRESS_DETAIL);
        }

        if(addressDetail != null){
            if(!addressDetail.getId().isEmpty()){
                binding.tvTitle.setText(getResources().getString(R.string.title_edit_address));
                binding.btnSubmitAddress.setText(getResources().getString(R.string.btn_lbl_update));

                binding.etFullName.setText(addressDetail.getName());
                binding.etPhoneNumber.setText(addressDetail.getMobileNumber());
                binding.etAddress.setText(addressDetail.getAddress());
                binding.etZipCode.setText(addressDetail.getZipCode());
                binding.etAdditionalNote.setText(addressDetail.getAdditionalNote());

                switch (addressDetail.getType()){
                    case Constants.HOME:
                        binding.rbHome.setChecked(true);
                        break;
                    case Constants.OFFICE:
                        binding.rbOffice.setChecked(true);
                        break;
                    case Constants.OTHER:
                        binding.rbOther.setChecked(true);
                        binding.tilOtherDetails.setVisibility(View.VISIBLE);
                        binding.etOtherDetails.setText(addressDetail.getOtherDetails());
                        break;
                }
            }
        }
    }




}