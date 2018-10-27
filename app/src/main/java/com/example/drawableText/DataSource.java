package com.example.drawableText;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class DataSource {

    public static final int NO_NAVIGATION = -1;

    public DataItem  mDataSource;
    private DrawableProvider mProvider;

    public DataSource(Context context,int type,String lettre) {
        mProvider = new DrawableProvider(context);
        mDataSource = itemFromType(type,lettre);

    }



    public DataItem itemFromType(int type, String lettre) {
        Drawable drawable = null;
        switch (type) {
            case DrawableProvider.SAMPLE_RECT:
                drawable = mProvider.getRect(lettre);
                break;
            case DrawableProvider.SAMPLE_ROUND_RECT:
                drawable = mProvider.getRoundRect(lettre);
                break;
            case DrawableProvider.SAMPLE_ROUND:
                drawable = mProvider.getRound(lettre);
                break;

        }
        return new DataItem(drawable);
    }
}