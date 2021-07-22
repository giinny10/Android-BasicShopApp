package com.tia.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    private String user_id = "";
    private String name = "";
    private String mobileNumber = "";
    private String address = "";
    private String zipCode = "";
    private String additionalNote = "";
    private String type = "";
    private String otherDetails = "";
    private String id = "";

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.name);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.address);
        dest.writeString(this.zipCode);
        dest.writeString(this.additionalNote);
        dest.writeString(this.type);
        dest.writeString(this.otherDetails);
        dest.writeString(this.id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
    public Address(Parcel in){
        this.user_id = in.readString();
        this.name = in.readString();
        this.mobileNumber =  in.readString();
        this.address = in.readString();
        this.zipCode = in.readString();
        this.additionalNote =  in.readString();
        this.type = in.readString();
        this.otherDetails =  in.readString();
        this.id =  in.readString();
    }

    public Address(){}

    public Address(String user_id, String name, String mobileNumber, String address,
                   String zipCode, String additionalNote, String type, String otherDetails) {
        this.user_id = user_id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.zipCode = zipCode;
        this.additionalNote = additionalNote;
        this.type = type;
        this.otherDetails = otherDetails;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAdditionalNote() {
        return additionalNote;
    }

    public void setAdditionalNote(String additionalNote) {
        this.additionalNote = additionalNote;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
