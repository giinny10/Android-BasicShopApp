package com.tia.ecommerce.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tia.ecommerce.R;
import com.tia.ecommerce.firestore.ProductFireStore;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.ui.activities.CartListActivity;
import com.tia.ecommerce.ui.activities.SettingActivity;
import com.tia.ecommerce.ui.adapters.DashboardItemListAdapter;
import com.tia.ecommerce.ui.adapters.MyProductListAdapter;

import java.util.ArrayList;

public class DashboardFragment extends BaseFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                return true;
            case R.id.action_cart:
                startActivity(new Intent(getActivity(), CartListActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDashboardItemList();
    }

    public void getDashboardItemList(){
        showProgressDialog();
        ProductFireStore.getInstance().getDashboardItemList(this);
    }

    public void successDashboardItemList(ArrayList<Product> products){
        hideProgressDialog();
        RecyclerView recyclerView = getView().findViewById(R.id.rv_dashboard_items);
        TextView notFound = getView().findViewById(R.id.tv_no_dashboard_items_found);
        if(products.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            notFound.setVisibility(View.GONE);

            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setHasFixedSize(true);

            DashboardItemListAdapter dashboardAdapter = new DashboardItemListAdapter(requireActivity(), products);
            recyclerView.setAdapter(dashboardAdapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            notFound.setVisibility(View.VISIBLE);
        }
    }
}