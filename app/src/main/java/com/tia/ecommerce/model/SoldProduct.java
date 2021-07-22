package com.tia.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SoldProduct implements Parcelable{
    private String user_id = "";
    private String title = "";
    private String price = "";
    private String sold_quantity = "";
    private String image = "";
    private String order_id = "";
    private Long order_date = 0L;
    private String sub_total_amount = "";
    private String shipping_charge = "";
    private String total_amount = "";
    private Address address = new Address();
    private String id = "";

    public SoldProduct(){}

    public SoldProduct(Parcel in){
        this.user_id = in.readString();
        this.title =  in.readString();
        this.price = in.readString();
        this.sold_quantity = in.readString();
        this.image =  in.readString();
        this.order_id = in.readString();
        this.order_date =  in.readLong();
        this.sub_total_amount =  in.readString();
        this.shipping_charge = in.readString();
        this.total_amount =  in.readString();
        this.address = in.readParcelable(getClass().getClassLoader());
        this.id =  in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.title);
        dest.writeString(this.price);
        dest.writeString(this.sold_quantity);
        dest.writeString(this.image);
        dest.writeString(this.order_id);
        dest.writeLong(this.order_date);
        dest.writeString(this.sub_total_amount);
        dest.writeString(this.shipping_charge);
        dest.writeString(this.total_amount);
        dest.writeParcelable(this.address, flags);
        dest.writeString(this.id);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SoldProduct createFromParcel(Parcel in) {
            return new SoldProduct(in);
        }

        public SoldProduct[] newArray(int size) {
            return new SoldProduct[size];
        }
    };

    public SoldProduct(String user_id, String title, String price, String sold_quantity, String image, String order_id, Long order_date, String sub_total_amount, String shipping_charge, String total_amount, Address address) {
        this.user_id = user_id;
        this.title = title;
        this.price = price;
        this.sold_quantity = sold_quantity;
        this.image = image;
        this.order_id = order_id;
        this.order_date = order_date;
        this.sub_total_amount = sub_total_amount;
        this.shipping_charge = shipping_charge;
        this.total_amount = total_amount;
        this.address = address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getSold_quantity() {
        return sold_quantity;
    }

    public void setSold_quantity(String sold_quantity) {
        this.sold_quantity = sold_quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Long getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Long order_date) {
        this.order_date = order_date;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
