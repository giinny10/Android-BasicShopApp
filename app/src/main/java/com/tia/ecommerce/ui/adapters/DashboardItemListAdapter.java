package com.tia.ecommerce.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tia.ecommerce.R;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.ui.activities.ProductDetailActivity;
import com.tia.ecommerce.utils.Constants;

import java.util.ArrayList;

public class DashboardItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Product> products;
    public DashboardItemListAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DashboardItemListAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_dashboard_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product model = products.get(position);
        if(holder instanceof DashboardItemListAdapter.MyViewHolder){
            ImageView imageView = holder.itemView.findViewById(R.id.iv_dashboard_item_image);
            Glide.with(context).load(model.getImage()).centerCrop().into(imageView);
            TextView title = holder.itemView.findViewById(R.id.tv_dashboard_item_title);
            title.setText(model.getTitle());

            TextView price = holder.itemView.findViewById(R.id.tv_dashboard_item_price);
            price.setText(model.getPrice() + " VND");

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(Constants.EXTRA_PRODUCT_ID, model.getProduct_id());
                intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID,  model.getUser_id());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
