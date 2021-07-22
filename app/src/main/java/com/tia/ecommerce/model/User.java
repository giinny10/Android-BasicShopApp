package com.tia.ecommerce.model;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String uid;
    private String firstName;
    private String lastName;
    private String email = "";
    private String image = "";
    private String mobile = "";
    private String address = "";
    private String gender = "";
    private int profileCompleted;

    public User(){}

    public User(String uid, String firstName, String lastName, String email) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(int profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.uid);
        dest.writeString(this.image);
        dest.writeString(this.mobile);
        dest.writeString(this.address);
        dest.writeString(this.gender);
        dest.writeInt(this.profileCompleted);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(Parcel in){
        this.email = in.readString();
        this.firstName = in.readString();
        this.lastName =  in.readString();
        this.uid = in.readString();
        this.image = in.readString();
        this.mobile =  in.readString();
        this.address = in.readString();
        this.gender =  in.readString();
        this.profileCompleted =  in.readInt();
    }
}
