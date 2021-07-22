package com.tia.ecommerce.firestore;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.tia.ecommerce.model.Cart;
import com.tia.ecommerce.model.Order;
import com.tia.ecommerce.model.Product;
import com.tia.ecommerce.model.SoldProduct;
import com.tia.ecommerce.ui.activities.CheckoutActivity;
import com.tia.ecommerce.ui.fragments.OrdersFragment;
import com.tia.ecommerce.ui.fragments.ProductsFragment;
import com.tia.ecommerce.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class OrderFireStore {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static final OrderFireStore instance = null;

    public static OrderFireStore getInstance() {
        if (instance == null) return new OrderFireStore();
        else return instance;
    }

    public void placeOrder(CheckoutActivity activity, Order order){
        firestore.collection(Constants.ORDERS)
                .document()
                .set(order, SetOptions.merge())
                .addOnSuccessListener(document -> {
                    activity.successPlaceOrder();
                })
                .addOnFailureListener(e -> {
                    activity.hideLoadingProgress();
                    Log.e(activity.getClass().getName(), "Error when placing an order", e);
                });
    }

    public void updateAllDetails(CheckoutActivity activity, ArrayList<Cart> carts, Order order){
        WriteBatch writeBatch = firestore.batch();

        for(Cart cart: carts){
            SoldProduct soldProduct = new SoldProduct(cart.getProduct_owner_id(), cart.getTitle(), cart.getPrice(),
                    cart.getCart_quantity(), cart.getImage(), order.getTitle(), order.getOrder_datetime(), order.getSub_total_amount(),
                    order.getShipping_charge(), order.getTotal_amount(), order.getAddress());

            DocumentReference documentReference = firestore.collection(Constants.SOLD_PRODUCTS)
                    .document(cart.getProduct_id());
            writeBatch.set(documentReference, soldProduct);
        }

        for(Cart cart: carts){
            DocumentReference documentReference = firestore.collection(Constants.CART_ITEMS).document(cart.getId());
            writeBatch.delete(documentReference);
        }

        writeBatch.commit()
                    .addOnSuccessListener(document -> {
                        activity.successUpdateAllDetail();
                    })
                    .addOnFailureListener(e -> {
                        activity.hideLoadingProgress();
                        Log.e(activity.getClass().getName(), "Error when update all details", e);
                    });
    }

    public void getMyOrderList(OrdersFragment ordersFragment){
        firestore.collection(Constants.ORDERS)
                .whereEqualTo(Constants.USER_ID, FirestoreClass.getInstance().getCurrentUserId())
                .get()
                .addOnSuccessListener(document -> {
                    ArrayList<Order> orders = new ArrayList<>();
                    for (DocumentSnapshot snapshot: document.getDocuments()) {
                        Order order = snapshot.toObject(Order.class);
                        order.setId(snapshot.getId());
                        orders.add(order);
                    }
                    ordersFragment.successGetMyOrderList(orders);
                })
                .addOnFailureListener(e -> {
                    ordersFragment.hideProgressDialog();
                    Log.e(ordersFragment.getClass().getName(), "Error when get my order list", e);
                });

    }
}
