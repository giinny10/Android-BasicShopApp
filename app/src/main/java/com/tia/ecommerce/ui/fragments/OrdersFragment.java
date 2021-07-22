package com.tia.ecommerce.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tia.ecommerce.R;
import com.tia.ecommerce.firestore.OrderFireStore;
import com.tia.ecommerce.model.Order;
import com.tia.ecommerce.ui.adapters.MyOrderListAdapter;
import com.tia.ecommerce.ui.adapters.MyProductListAdapter;

import java.util.ArrayList;

public class OrdersFragment extends BaseFragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_orders, container, false);

        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        getMyOrderList();
    }

    public void getMyOrderList(){
        showProgressDialog();
        OrderFireStore.getInstance().getMyOrderList(this);
    }

    public void successGetMyOrderList(ArrayList<Order> orders){
        hideProgressDialog();

        RecyclerView recyclerView = getView().findViewById(R.id.rv_my_order_items);
        TextView notFound = getView().findViewById(R.id.tv_no_orders_found);
        if(orders.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            notFound.setVisibility(View.GONE);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);

            MyOrderListAdapter myOrderListAdapter = new MyOrderListAdapter(requireActivity(), orders, this);
            recyclerView.setAdapter(myOrderListAdapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            notFound.setVisibility(View.VISIBLE);
        }
    }
}