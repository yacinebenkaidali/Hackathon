package com.example.drawableText;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;


/**
 * @author amulya
 * @datetime 17 Oct 2014, 4:02 PM
 */
public class DrawableProvider {

    public static final int SAMPLE_RECT = 1;
    public static final int SAMPLE_ROUND_RECT = 2;
    public static final int SAMPLE_ROUND = 3;


    private final ColorGenerator mGenerator;
    private final Context mContext;

    public DrawableProvider(Context context) {
        mGenerator = ColorGenerator.DEFAULT;
        mContext = context;
    }

    public TextDrawable getRect(String text) {
        return TextDrawable.builder()
                .buildRect(text, mGenerator.getColor(text));
    }

    public TextDrawable getRound(String text) {
        return TextDrawable.builder()
                .buildRound(text, mGenerator.getColor(text));
    }

    public TextDrawable getRoundRect(String text) {
        return TextDrawable.builder()
                .buildRoundRect(text, mGenerator.getColor(text), toPx(10));
    }



    public TextDrawable getRectWithMultiLetter() {
        String text = "AK";
        return TextDrawable.builder()
                .beginConfig()
                    .fontSize(toPx(20))
                    .toUpperCase()
                .endConfig()
                .buildRect(text, mGenerator.getColor(text));
    }


    public int toPx(int dp) {
        Resources resources = mContext.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}
