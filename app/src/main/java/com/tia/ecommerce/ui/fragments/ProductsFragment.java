package com.tia.ecommerce.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tia.ecommerce.R;
import com.tia.ecommerce.firestore.ProductFireStore;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.ui.activities.AddProductActivity;
import com.tia.ecommerce.ui.activities.SettingActivity;
import com.tia.ecommerce.ui.adapters.MyProductListAdapter;

import java.util.ArrayList;

public class ProductsFragment extends BaseFragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_product_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_product:
                startActivity(new Intent(getActivity(), AddProductActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        getProductList();
    }

    private void getProductList(){
        showProgressDialog();
        ProductFireStore.getInstance().getProductList(this);
    }

    public void successProductList(ArrayList<Product> products){
        hideProgressDialog();
        RecyclerView recyclerView = getView().findViewById(R.id.rv_my_product_items);
        TextView notFound = getView().findViewById(R.id.tv_no_products_found);
        if(products.size() > 0){

            recyclerView.setVisibility(View.VISIBLE);
            notFound.setVisibility(View.GONE);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);

            MyProductListAdapter producstAdapter = new MyProductListAdapter(requireActivity(), products, this);
            recyclerView.setAdapter(producstAdapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            notFound.setVisibility(View.VISIBLE);
        }
    }
    public  void deleteProduct(String productID){
        showAlertDialogDelete(productID);

    }

    public void showAlertDialogDelete(String productID){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(getResources().getString(R.string.delete_dialog_title));
        builder.setMessage(getResources().getString(R.string.delete_dialog_message));
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(getResources().getString(R.string.yes), ((dialog, which) -> {
            showProgressDialog();
            ProductFireStore.getInstance().deleteProduct(this, productID);
            dialog.dismiss();
        }));
        builder.setNegativeButton(getResources().getString(R.string.no), ((dialog, which) -> {
            dialog.dismiss();
        }));

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public void successDeleteProduct(){
        hideProgressDialog();
        Toast.makeText(requireActivity(), getResources().getString(R.string.product_delete_success_message), Toast.LENGTH_SHORT).show();
        getProductList();
    }


}