package com.example.boghdady.campusapp.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by AbdulrahmanGamal on 8/27/2016.
 */

public class CustomEditText extends EditText {

    public CustomEditText(Context context) {
        super(context);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/flat_font.ttf");
        this.setTypeface(face);

    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/flat_font.ttf");
        this.setTypeface(face);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/flat_font.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }
}
