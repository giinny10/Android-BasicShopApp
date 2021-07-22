package com.tia.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Order implements Parcelable{
    private String user_id = "";
    private ArrayList<Cart> items = new ArrayList<>();
    private Address address = new Address();
    private String title = "";
    private String image = "";
    private String sub_total_amount = "";
    private String shipping_charge = "";
    private String total_amount = "";
    private Long order_datetime = 0L;
    private String id = "";

    public Order(){}

    public Order(String user_id, ArrayList<Cart> items, Address address, String title, String image,
                 String sub_total_amount, String shipping_charge, String total_amount) {
        this.user_id = user_id;
        this.items = items;
        this.address = address;
        this.title = title;
        this.image = image;
        this.sub_total_amount = sub_total_amount;
        this.shipping_charge = shipping_charge;
        this.total_amount = total_amount;
        this.order_datetime = order_datetime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeTypedList(this.items);
        dest.writeParcelable(this.address, flags);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.sub_total_amount);
        dest.writeString(this.shipping_charge);
        dest.writeString(this.total_amount);
        dest.writeLong(this.order_datetime);
        dest.writeString(this.id);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public Order(Parcel in){
        this.user_id = in.readString();
        this.items = in.readArrayList(getClass().getClassLoader());
        this.address =  in.readParcelable(getClass().getClassLoader());
        this.title = in.readString();
        this.image = in.readString();
        this.sub_total_amount =  in.readString();
        this.shipping_charge = in.readString();
        this.total_amount =  in.readString();
        this.order_datetime = in.readLong();
        this.id =  in.readString();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<Cart> getItems() {
        return items;
    }

    public void setItems(ArrayList<Cart> items) {
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSub_total_amount() {
        return sub_total_amount;
    }

    public void setSub_total_amount(String sub_total_amount) {
        this.sub_total_amount = sub_total_amount;
    }

    public String getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(String shipping_charge) {
        this.shipping_charge = shipping_charge;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public Long getOrder_datetime() {
        return order_datetime;
    }

    public void setOrder_datetime(Long order_datetime) {
        this.order_datetime = order_datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
