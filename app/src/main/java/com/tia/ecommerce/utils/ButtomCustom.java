package com.tia.ecommerce.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.tia.ecommerce.R;

public class ButtomCustom extends AppCompatButton {
    public ButtomCustom(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyFont();
    }

    void applyFont(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Bold.ttf");
        setTypeface(typeface);
    }
}
