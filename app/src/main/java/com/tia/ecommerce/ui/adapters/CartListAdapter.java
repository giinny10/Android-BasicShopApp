package com.tia.ecommerce.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tia.ecommerce.R;
import com.tia.ecommerce.firestore.CartFireStore;
import com.tia.ecommerce.model.Cart;
import com.tia.ecommerce.ui.activities.CartListActivity;
import com.tia.ecommerce.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class CartListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Cart> carts;
    private boolean isUpdateCardItem;

    public CartListAdapter(Context context, ArrayList<Cart> carts, boolean isUpdateCardItem) {
        this.context = context;
        this.carts = carts;
        this.isUpdateCardItem = isUpdateCardItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartListAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Cart model = carts.get(position);
        if (holder instanceof CartListAdapter.MyViewHolder) {
            ImageView imageView = holder.itemView.findViewById(R.id.iv_cart_item_image);
            Glide.with(context).load(model.getImage()).centerCrop().into(imageView);

            TextView title = holder.itemView.findViewById(R.id.tv_cart_item_title);
            title.setText(model.getTitle());

            TextView price = holder.itemView.findViewById(R.id.tv_cart_item_price);
            price.setText(model.getPrice());

            TextView quantity = holder.itemView.findViewById(R.id.tv_cart_quantity);
            quantity.setText(model.getCart_quantity());

            ImageButton removeBtn = holder.itemView.findViewById(R.id.ib_remove_cart_item);
            ImageButton addCartItem = holder.itemView.findViewById(R.id.ib_add_cart_item);
            ImageButton deleteCart = holder.itemView.findViewById(R.id.ib_delete_cart_item);
            if (!isUpdateCardItem) {
                deleteCart.setVisibility(View.GONE);
                removeBtn.setVisibility(View.GONE);
                addCartItem.setVisibility(View.GONE);
            } else {
                deleteCart.setVisibility(View.VISIBLE);
                removeBtn.setVisibility(View.VISIBLE);
                addCartItem.setVisibility(View.VISIBLE);

                if (model.getCart_quantity().equals("0")) {
                    removeBtn.setVisibility(View.GONE);
                    addCartItem.setVisibility(View.GONE);

                    quantity.setText(context.getResources().getString(R.string.lbl_out_of_stock));
                    quantity.setTextColor(Color.RED);

                } else {
                    removeBtn.setVisibility(View.VISIBLE);
                    addCartItem.setVisibility(View.VISIBLE);
                    quantity.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
                }

                deleteCart.setOnClickListener(view -> {
                    if (context instanceof CartListActivity) {
                        CartFireStore.getInstance().removeItemFromCart(context, model.getId());
                    }
                });

                removeBtn.setOnClickListener(view -> {
                    if (model.getCart_quantity().equals("1")) {
                        CartFireStore.getInstance().removeItemFromCart(context, model.getId());
                    } else {
                        int cartQuantity = Integer.parseInt(model.getCart_quantity());
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put(Constants.CART_QUANTITY, (cartQuantity - 1) + "");
                        if (context instanceof CartListActivity) {
                            ((CartListActivity) context).showLoadingProgress();
                        }
                        CartFireStore.getInstance().updateCartItem(context, model.getId(), hashMap);
                    }
                });

                addCartItem.setOnClickListener(view -> {
                    int cartQuantity = Integer.parseInt(model.getCart_quantity());
                    if (cartQuantity < Integer.parseInt(model.getStock_quantity())) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put(Constants.CART_QUANTITY, (cartQuantity + 1) + "");
                        if (context instanceof CartListActivity) {
                            ((CartListActivity) context).showLoadingProgress();
                        }
                        CartFireStore.getInstance().updateCartItem(context, model.getId(), hashMap);
                    } else {
                        if (context instanceof CartListActivity) {
                            ((CartListActivity) context).showErrorSnackBar(context.getResources().getString(R.string.msg_for_available_stock), true);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
