package com.example.boghdady.campusapp.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import me.grantland.widget.AutofitTextView;

/**
 * Created by AbdulrahmanGamal on 8/3/2016.
 */

public class CustomTextView extends AutofitTextView {

    public CustomTextView(Context context) {
        super(context);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/flat_font.ttf");
        this.setTypeface(face);

    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/flat_font.ttf");
        this.setTypeface(face);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/flat_font.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }


}
