package com.tia.ecommerce.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class BoldTextView extends AppCompatTextView {
    public BoldTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyFont();
    }


    void applyFont(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Bold.ttf");
        setTypeface(typeface);
    }
}
