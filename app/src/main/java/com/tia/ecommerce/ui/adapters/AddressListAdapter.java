package com.tia.ecommerce.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tia.ecommerce.R;
import com.tia.ecommerce.model.Address;
import com.tia.ecommerce.model.Cart;
import com.tia.ecommerce.ui.activities.AddEditAddressActivity;
import com.tia.ecommerce.ui.activities.CheckoutActivity;
import com.tia.ecommerce.utils.Constants;

import java.util.ArrayList;

public class AddressListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<Address> addresses;
    private boolean selectAddress;
    public AddressListAdapter(Context context, ArrayList<Address> addresses, boolean selectAddress){
        this.context = context;
        this.addresses = addresses;
        this.selectAddress = selectAddress;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressListAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_address_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Address model = addresses.get(position);

        if(holder instanceof MyViewHolder){
            TextView fullname = holder.itemView.findViewById(R.id.tv_address_full_name);
            fullname.setText(model.getName());

            TextView mobile = holder.itemView.findViewById(R.id.tv_address_mobile_number);
            mobile.setText(model.getMobileNumber());

            TextView type = holder.itemView.findViewById(R.id.tv_address_type);
            type.setText(model.getType());

            TextView details = holder.itemView.findViewById(R.id.tv_address_details);
            details.setText(model.getAddress() + ", " + model.getZipCode());

            if(selectAddress){
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, CheckoutActivity.class);
                    intent.putExtra(Constants.EXTRA_SELECTED_ADDRESS, model);
                    context.startActivity(intent);
                });
            }

        }
    }

    public void notifyEditItem(Activity activity, int position){
        Intent intent = new Intent(context, AddEditAddressActivity.class);
        intent.putExtra(Constants.EXTRA_ADDRESS_DETAIL, addresses.get(position));
        activity.startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
