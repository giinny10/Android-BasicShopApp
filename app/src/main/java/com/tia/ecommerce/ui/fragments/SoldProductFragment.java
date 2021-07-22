package com.tia.ecommerce.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tia.ecommerce.R;
import com.tia.ecommerce.firestore.ProductFireStore;
import com.tia.ecommerce.model.SoldProduct;
import com.tia.ecommerce.ui.adapters.MyProductListAdapter;
import com.tia.ecommerce.ui.adapters.SoldProductListAdapter;

import java.util.ArrayList;

public class SoldProductFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sold_product, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSoldProductList();
    }

    public void getSoldProductList(){
        showProgressDialog();
        ProductFireStore.getInstance().getSoldProductList(this);
    }
    public void successGetSoldProductList(ArrayList<SoldProduct> soldProducts){
        hideProgressDialog();
        RecyclerView recyclerView = getView().findViewById(R.id.rv_sold_product_items);
        TextView notFound = getView().findViewById(R.id.tv_no_sold_products_found);
        if(soldProducts.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            notFound.setVisibility(View.GONE);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);

            SoldProductListAdapter soldProductListAdapter = new SoldProductListAdapter(requireActivity(), soldProducts);
            recyclerView.setAdapter(soldProductListAdapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            notFound.setVisibility(View.VISIBLE);
        }
    }
}