package com.tia.ecommerce.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tia.ecommerce.R;
import com.tia.ecommerce.databinding.ActivityAddressListBinding;
import com.tia.ecommerce.firestore.AddressFireStore;
import com.tia.ecommerce.model.Address;
import com.tia.ecommerce.ui.adapters.AddressListAdapter;
import com.tia.ecommerce.utils.Constants;
import com.tia.ecommerce.utils.SwipeToDeleteCallback;
import com.tia.ecommerce.utils.SwipeToEditCallback;

import java.util.ArrayList;

public class AddressListActivity extends BaseActivity {
    private ActivityAddressListBinding binding;
    private boolean selectedAddrress = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpActionbar();

        if(getIntent().hasExtra(Constants.EXTRA_SELECT_ADDRESS)){
            selectedAddrress = getIntent().getBooleanExtra(Constants.EXTRA_SELECT_ADDRESS, false);
        }

        if(selectedAddrress){
            binding.tvTitle.setText(getResources().getString(R.string.title_select_address));
        }

        getAddressList();
    }

    private void setUpActionbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_address_list_activity);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void onCliCkBtnAddAddress(View view) {
        Intent intent = new Intent(this, AddEditAddressActivity.class);
        startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE);
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }*/

    public void getAddressList(){
        showLoadingProgress();
        AddressFireStore.getInstance().getAddressList(this);
    }

    public void successGetAddressList(ArrayList<Address> addresses){
        hideLoadingProgress();
        if(addresses.size() > 0){

            binding.rvAddressList.setVisibility(View.VISIBLE);
            binding.tvNoAddressFound.setVisibility(View.GONE);

            binding.rvAddressList.setLayoutManager(new LinearLayoutManager(this));
            binding.rvAddressList.setHasFixedSize(true);

            AddressListAdapter addressistAdapter = new AddressListAdapter(this, addresses, selectedAddrress);
            binding.rvAddressList.setAdapter(addressistAdapter);

            if(!selectedAddrress){
                SwipeToEditCallback swipeToEdit = new SwipeToEditCallback(this) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        AddressListAdapter adapter = (AddressListAdapter) binding.rvAddressList.getAdapter();
                        adapter.notifyEditItem(AddressListActivity.this, viewHolder.getAdapterPosition());
                    }
                };
                ItemTouchHelper editItemTouch = new ItemTouchHelper(swipeToEdit);
                editItemTouch.attachToRecyclerView(binding.rvAddressList);


                SwipeToDeleteCallback swipeToDelete = new SwipeToDeleteCallback(this) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        showLoadingProgress();
                        AddressFireStore.getInstance().deleteAddressDetail(AddressListActivity.this, addresses.get(viewHolder.getAdapterPosition()).getId());
                    }
                };

                ItemTouchHelper deleteItemTouch = new ItemTouchHelper(swipeToDelete);
                deleteItemTouch.attachToRecyclerView(binding.rvAddressList);
            }

        }else{
            binding.rvAddressList.setVisibility(View.GONE);
            binding.tvNoAddressFound.setVisibility(View.VISIBLE);
        }
    }

    public void successDeleteAddress(){
        hideLoadingProgress();
        showErrorSnackBar(getResources().getString(R.string.err_your_address_deleted_successfully), false);
        getAddressList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            getAddressList();
        }
    }
}