package com.tia.ecommerce.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

public class Constants {
    //firebase collections
    public static final String USERS = "users";
    public static final String PRODUCTS = "products";
    public static final String CART_ITEMS = "cart_items";
    public static final String ADDRESSES = "addresses";
    public static final String ORDERS = "orders";
    public static final String SOLD_PRODUCTS = "sold_products";

    //share preferences for user login info
    public static final String LOGIN_USERNAME = "login_username";
    public static final String MYSHOP_PREFERENCES = "MyshopPrefs";
    public static final String EXTRA_USER_DETAILS = "Extra_user_details";

    //user permission
    public static final int READ_STORAGE_PERMISSION_CODE = 2;
    public static final int PICK_IMAGE_REQUEST_CODE = 1;

    //fire store users collection field
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    public static final String MOBILE = "mobile";
    public static final String GENDER = "gender";
    public static final String ADDRESS = "address";
    public static final String IMAGE = "image";
    public static final String COMPLETE_PROFILE = "profileCompleted";

    //product items
    public static final String USER_ID = "user_id";
    public static final String EXTRA_PRODUCT_ID = "extra_product_id";
    public static final String EXTRA_PRODUCT_OWNER_ID = "extra_product_owner_id";

    //cart
    public static final String DEFAULT_CART_QUANTITY = "1";
    public static final String PRODUCT_ID = "product_id";
    public static final String CART_QUANTITY = "cart_quantity";
    public static final String  STOCK_QUANTITY = "stock_quantity";

    //address
    public static final String HOME = "Nhà riêng";
    public static final String OFFICE = "Cơ quan";
    public static final String OTHER = "Khác";
    public static final String EXTRA_ADDRESS_DETAIL = "AddressDetails";
    public static final String EXTRA_SELECT_ADDRESS = "extra_select_address";
    public static final int ADD_ADDRESS_REQUEST_CODE = 121;

    //checkout
    public static final String EXTRA_SELECTED_ADDRESS = "extra_selected_address";

    //order
    public static final String EXTRA_MY_ORDER_DETAILS = "extra_MY_ORDER_DETAILS";

    //sold product
    public static final String EXTRA_SOLD_PRODUCT_DETAIL = "extra_SOLD_PRODUCT_DETAILS";

   /* other*/
    public static final String USER_PROFILE_IMAGE = "user_profile_image";
    public static final String PRODUCT_IMAGE = "product_image";

    
    public static void showImageChooser(Activity activity){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    public static String getFileExtension(Activity activity, Uri uri){
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.getContentResolver().getType(uri));
    }
}
