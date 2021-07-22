package com.tia.ecommerce.firestore;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.model.SoldProduct;
import com.tia.ecommerce.ui.activities.BaseActivity;
import com.tia.ecommerce.ui.activities.CartListActivity;
import com.tia.ecommerce.ui.activities.CheckoutActivity;
import com.tia.ecommerce.ui.activities.ProductDetailActivity;
import com.tia.ecommerce.ui.fragments.BaseFragment;
import com.tia.ecommerce.ui.fragments.DashboardFragment;
import com.tia.ecommerce.ui.fragments.ProductsFragment;
import com.tia.ecommerce.ui.fragments.SoldProductFragment;
import com.tia.ecommerce.utils.Constants;

import java.util.ArrayList;

public class ProductFireStore {
    private FirebaseFirestore firestore = FirestoreClass.getInstance().getFirestore();
    private static final ProductFireStore instance = null;

    private ProductFireStore() {
    }

    public static ProductFireStore getInstance() {
        if (instance == null) return new ProductFireStore();
        else return instance;
    }

    public void getProductList(BaseFragment fragment){
        firestore.collection(Constants.PRODUCTS)
                .whereEqualTo(Constants.USER_ID, FirestoreClass.getInstance().getCurrentUserId())
                .get()
                .addOnSuccessListener(document -> {
                    ArrayList<Product> products = new ArrayList<>();
                    for (DocumentSnapshot snapshot: document.getDocuments()) {
                        Product p = snapshot.toObject(Product.class);
                        p.setProduct_id(snapshot.getId());
                        products.add(p);
                    }
                    if(fragment instanceof ProductsFragment){
                        ((ProductsFragment) fragment).successProductList(products);
                    }
                })
                .addOnFailureListener(e -> {
                    fragment.hideProgressDialog();
                    Log.e(fragment.getClass().getName(), "Error when get product item list", e);
                });
    }

    public void getDashboardItemList(DashboardFragment fragment){
        firestore.collection(Constants.PRODUCTS)
                .get()
                .addOnSuccessListener(document -> {
                    ArrayList<Product> productList = new ArrayList<>();
                    for (DocumentSnapshot snapshot: document.getDocuments()) {
                        Product p = snapshot.toObject(Product.class);
                        p.setProduct_id(snapshot.getId());
                        productList.add(p);
                    }
                    fragment.successDashboardItemList(productList);

                })
                .addOnFailureListener(e -> {
                    fragment.hideProgressDialog();
                    Log.e(fragment.getClass().getName(), "Error when get dashboard item list", e);
                });
    }

    public void deleteProduct(ProductsFragment fragment, String productID){
        firestore.collection(Constants.PRODUCTS)
                .document(productID)
                .delete()
                .addOnSuccessListener(document -> {
                    fragment.successDeleteProduct();
                })
                .addOnFailureListener(e -> {
                    fragment.hideProgressDialog();
                    Log.e(fragment.getClass().getName(), "Error when delete product item", e);
                });
    }

    public void getProductDetails(ProductDetailActivity activity, String productID){
        firestore.collection(Constants.PRODUCTS)
                .document(productID)
                .get()
                .addOnSuccessListener(document -> {
                    Product product = document.toObject(Product.class);
                    if(product != null) activity.successProductDetail(product);
                })
                .addOnFailureListener(e -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when get product details", e);
                });
    }

    public void getAllProductList(BaseActivity activity){
        firestore.collection(Constants.PRODUCTS)
                .get()
                .addOnSuccessListener(document -> {
                    ArrayList<Product> productList = new ArrayList<>();
                    for (DocumentSnapshot snapshot: document.getDocuments()) {
                        Product p = snapshot.toObject(Product.class);
                        p.setProduct_id(snapshot.getId());
                        productList.add(p);
                    }
                    if(activity instanceof CartListActivity){
                        ((CartListActivity) activity).successAllProductList(productList);
                    }else if(activity instanceof CheckoutActivity){
                        ((CheckoutActivity) activity).successAllProductList(productList);
                    }
                })
                .addOnFailureListener(e -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when get all product list", e);
                });
    }

    public void getSoldProductList(SoldProductFragment fragment){
        firestore.collection(Constants.SOLD_PRODUCTS)
                .whereEqualTo(Constants.USER_ID, FirestoreClass.getInstance().getCurrentUserId())
                .get()
                .addOnSuccessListener(document -> {
                    ArrayList<SoldProduct> soldProducts = new ArrayList<>();
                    for (DocumentSnapshot snapshot: document.getDocuments()) {
                        SoldProduct soldProduct = snapshot.toObject(SoldProduct.class);
                        soldProduct.setId(snapshot.getId());
                        soldProducts.add(soldProduct);
                    }
                    fragment.successGetSoldProductList(soldProducts);
                })
                .addOnFailureListener(e -> {
                    fragment.hideProgressDialog();
                    Log.e(fragment.getClass().getName(), "Error when get sold product list", e);
                });

    }
}
