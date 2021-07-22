package com.tia.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {
    private String user_id = "";
    private String user_name = "";
    private String title = "";
    private String price = "";
    private String description = "";
    private String stock_quantity = "";
    private String image = "";
    private String product_id = "";

    public Product(){}

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(String stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.user_name);
        dest.writeString(this.title);
        dest.writeString(this.price);
        dest.writeString(this.description);
        dest.writeString(this.stock_quantity);
        dest.writeString(this.image);
        dest.writeString(this.stock_quantity);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public Product(Parcel in){
        this.user_id = in.readString();
        this.user_name = in.readString();
        this.title =  in.readString();
        this.price = in.readString();
        this.description = in.readString();
        this.stock_quantity =  in.readString();
        this.product_id = in.readString();
        this.image =  in.readString();
    }

    public Product(String user_id, String user_name, String title, String price, String description, String stock_quantity, String image) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.title = title;
        this.price = price;
        this.description = description;
        this.stock_quantity = stock_quantity;
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return "product ID: " + this.getProduct_id() + ", product stock: " + this.getStock_quantity() +
                ", user_id: " + this.getUser_id() + ", product title:" + this.getTitle();
    }
}
