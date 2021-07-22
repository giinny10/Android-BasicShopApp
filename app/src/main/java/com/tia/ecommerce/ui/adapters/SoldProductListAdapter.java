package com.tia.ecommerce.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tia.ecommerce.R;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.model.SoldProduct;
import com.tia.ecommerce.ui.activities.ProductDetailActivity;
import com.tia.ecommerce.ui.activities.SoldProductDetailActivity;
import com.tia.ecommerce.ui.fragments.ProductsFragment;
import com.tia.ecommerce.utils.Constants;

import java.util.ArrayList;

public class SoldProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<SoldProduct> soldProducts;
    public SoldProductListAdapter(Context context, ArrayList<SoldProduct> soldProducts){
        this.context = context;
        this.soldProducts = soldProducts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SoldProductListAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SoldProduct model = soldProducts.get(position);

        if (holder instanceof SoldProductListAdapter.MyViewHolder) {
            ImageView imageView = holder.itemView.findViewById(R.id.iv_item_image);
            Glide.with(context).load(model.getImage()).centerCrop().into(imageView);

            TextView title = holder.itemView.findViewById(R.id.tv_item_name);
            title.setText(model.getTitle());

            TextView price = holder.itemView.findViewById(R.id.tv_item_price);
            price.setText(model.getPrice() + " VND");

            ImageButton deleteBtn = holder.itemView.findViewById(R.id.ib_delete_product);
            deleteBtn.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, SoldProductDetailActivity.class);
                intent.putExtra(Constants.EXTRA_SOLD_PRODUCT_DETAIL,model);
                context.startActivity(intent);
            });
        }
    }
    @Override
    public int getItemCount() {
        return soldProducts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
