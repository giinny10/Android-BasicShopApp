package com.tia.ecommerce.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tia.ecommerce.R;

public class BaseFragment extends Fragment {
    private Dialog progressDialog;

    public BaseFragment() {
    }

    public void showProgressDialog(){
        progressDialog = new Dialog(requireActivity());
        progressDialog.setContentView(R.layout.dialog_progess);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgressDialog(){
        progressDialog.dismiss();
    }

}