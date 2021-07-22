package com.tia.ecommerce.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tia.ecommerce.model.Product;

import java.util.ArrayList;
import com.tia.ecommerce.R;
import com.tia.ecommerce.ui.activities.ProductDetailActivity;
import com.tia.ecommerce.ui.fragments.ProductsFragment;
import com.tia.ecommerce.utils.Constants;

public class MyProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Product> products;
    private ProductsFragment productsFragment;
    public MyProductListAdapter(Context context, ArrayList<Product> products,ProductsFragment productsFragment ){
        this.context = context;
        this.products = products;
        this.productsFragment = productsFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product model = products.get(position);
        if(holder instanceof MyViewHolder){
            ImageView imageView = holder.itemView.findViewById(R.id.iv_item_image);
            Glide.with(context).load(model.getImage()).centerCrop().into(imageView);
            TextView title = holder.itemView.findViewById(R.id.tv_item_name);
            title.setText(model.getTitle() + " VND");

            TextView price = holder.itemView.findViewById(R.id.tv_item_price);
            price.setText(model.getPrice() + " VND");

            ImageButton deleteBtn = holder.itemView.findViewById(R.id.ib_delete_product);
            deleteBtn.setOnClickListener(view -> {
                productsFragment.deleteProduct(model.getProduct_id());
            });

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(Constants.EXTRA_PRODUCT_ID,model.getProduct_id());
                intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID,model.getUser_id());
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
