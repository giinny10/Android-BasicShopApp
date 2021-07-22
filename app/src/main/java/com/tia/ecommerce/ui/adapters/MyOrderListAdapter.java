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
import com.tia.ecommerce.model.Order;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.ui.activities.MyOrderDetailActivity;
import com.tia.ecommerce.ui.activities.ProductDetailActivity;
import com.tia.ecommerce.ui.fragments.OrdersFragment;
import com.tia.ecommerce.ui.fragments.ProductsFragment;
import com.tia.ecommerce.utils.Constants;

import java.util.ArrayList;

public class MyOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<Order> orders;
    private OrdersFragment ordersFragment;
    public MyOrderListAdapter(Context context, ArrayList<Order> orders,OrdersFragment ordersFragment ){
        this.context = context;
        this.orders = orders;
        this.ordersFragment = ordersFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderListAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Order model = orders.get(position);

        if(holder instanceof MyViewHolder){
            ImageView imageView = holder.itemView.findViewById(R.id.iv_item_image);
            Glide.with(context).load(model.getImage()).centerCrop().into(imageView);

            TextView title = holder.itemView.findViewById(R.id.tv_item_name);
            title.setText(model.getTitle());

            TextView price = holder.itemView.findViewById(R.id.tv_item_price);
            price.setText(model.getTotal_amount() + " VND");

            ImageButton deleteBtn = holder.itemView.findViewById(R.id.ib_delete_product);
            deleteBtn.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, MyOrderDetailActivity.class);
                intent.putExtra(Constants.EXTRA_MY_ORDER_DETAILS,model);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
