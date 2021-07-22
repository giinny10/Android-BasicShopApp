package com.tia.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {
    private String user_id = "";
    private String product_owner_id = "";
    private String product_id = "";
    private String title = "";
    private String price = "";
    private String image = "";
    private String cart_quantity = "";
    private String stock_quantity = "";
    private String id = "";
    public Cart(){}

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCart_quantity() {
        return cart_quantity;
    }

    public void setCart_quantity(String cart_quantity) {
        this.cart_quantity = cart_quantity;
    }

    public String getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(String stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_owner_id() {
        return product_owner_id;
    }

    public void setProduct_owner_id(String product_owner_id) {
        this.product_owner_id = product_owner_id;
    }

    public Cart(String currentUserId, String productOwnerID, String productId, String title, String price, String image, String defaultCartQuantity) {
        this.user_id = currentUserId;
        this.product_owner_id = productOwnerID;
        this.product_id = productId;
        this.title = title;
        this.price = price;
        this.image = image;
        this.cart_quantity = defaultCartQuantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.product_id);
        dest.writeString(this.title);
        dest.writeString(this.price);
        dest.writeString(this.image);
        dest.writeString(this.cart_quantity);
        dest.writeString(this.stock_quantity);
        dest.writeString(this.id);
        dest.writeString(this.product_owner_id);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
    public Cart(Parcel in){
        this.user_id = in.readString();
        this.product_id = in.readString();
        this.title =  in.readString();
        this.price = in.readString();
        this.image = in.readString();
        this.cart_quantity =  in.readString();
        this.stock_quantity = in.readString();
        this.id =  in.readString();
        this.product_owner_id = in.readString();
    }

}
