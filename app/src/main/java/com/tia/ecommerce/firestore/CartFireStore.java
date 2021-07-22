package com.tia.ecommerce.firestore;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.protobuf.Any;
import com.tia.ecommerce.model.Cart;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.ui.activities.BaseActivity;
import com.tia.ecommerce.ui.activities.CartListActivity;
import com.tia.ecommerce.ui.activities.CheckoutActivity;
import com.tia.ecommerce.ui.activities.ProductDetailActivity;
import com.tia.ecommerce.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class CartFireStore {
    private FirebaseFirestore firestore = FirestoreClass.getInstance().getFirestore();
    private static final CartFireStore instance = null;
    private CartFireStore() {
    }
    public static CartFireStore getInstance() {
        if (instance == null) return new CartFireStore();
        else return instance;
    }

    public void addCartItems(ProductDetailActivity activity, Cart cart){
        firestore.collection(Constants.CART_ITEMS)
                .document()
                .set(cart, SetOptions.merge())
                .addOnSuccessListener(data -> {
                    activity.successAddToCart();
                })
                .addOnFailureListener(e -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when add product to cart item", e);
                });
    }

    public void checkIfItemExistInCart(ProductDetailActivity activity, String productID){
        firestore.collection(Constants.CART_ITEMS)
                .whereEqualTo(Constants.USER_ID, FirestoreClass.getInstance().getCurrentUserId())
                .whereEqualTo(Constants.PRODUCT_ID, productID)
                .get()
                .addOnSuccessListener(document -> {
                    if(document.getDocuments().size() > 0){
                        activity.productExistInCart();
                    }else{
                        activity.hideLoadingProgress();
                    }
                })
                .addOnFailureListener(e -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when check if item exist in cart", e);
                });

    }

    public void getCartList(BaseActivity activity){
        firestore.collection(Constants.CART_ITEMS)
                .whereEqualTo(Constants.USER_ID, FirestoreClass.getInstance().getCurrentUserId())
                .get()
                .addOnSuccessListener(document -> {
                    ArrayList<Cart> carts = new ArrayList<>();
                    for (DocumentSnapshot snapshot: document.getDocuments()) {
                        Cart cart = snapshot.toObject(Cart.class);
                        cart.setId(snapshot.getId());
                        carts.add(cart);
                    }
                    if(activity instanceof CartListActivity){
                        ((CartListActivity) activity).successGetCartList(carts);
                    }
                    if(activity instanceof CheckoutActivity){
                        ((CheckoutActivity) activity).successGetCartList(carts);
                    }
                })
                .addOnFailureListener(e -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when get cart list item", e);
                });
    }

    public void removeItemFromCart(Context context, String cartID){
        firestore.collection(Constants.CART_ITEMS)
                .document(cartID)
                .delete()
                .addOnSuccessListener(document -> {
                    if(context instanceof CartListActivity){
                        ((CartListActivity) context).successRemoveCartItem();
                    }
                })
                .addOnFailureListener(e -> {
                    if(context instanceof CartListActivity){
                        ((CartListActivity) context).hideLoadingProgress();
                    }
                    Log.e(context.getClass().getName(), "Error when remove cart item", e);
                });
    }

    public void updateCartItem(Context context, String cartID, HashMap<String, Object> hashMap){
        firestore.collection(Constants.CART_ITEMS)
                .document(cartID)
                .update(hashMap)
                .addOnSuccessListener(document -> {
                    if(context instanceof CartListActivity){
                        ((CartListActivity) context).successUpdateCartItem();
                    }
                })
                .addOnFailureListener(e -> {
                    if(context instanceof CartListActivity){
                        ((CartListActivity) context).hideLoadingProgress();
                    }
                    Log.e(context.getClass().getName(), "Error when update cart item", e);
                });
    }

}
