package com.example.amank.inspireone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

/**
 * Created by amank on 16-12-2017.
 */

public class Utils {
    public static Bitmap getScaledBitmapFromDrawable(Context context, int id){
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), id);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float scale = metrics.density;
        Integer width = Math.round(60 * scale);
        int height = Math.round(60 * scale);
        icon = Bitmap.createScaledBitmap(icon, width, height, false);
        return icon;
    }
}
