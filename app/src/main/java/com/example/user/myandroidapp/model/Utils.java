package com.example.user.myandroidapp.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by user on 2016/8/31.
 */
public class Utils {

    public static Drawable getDrawable(Context context, int drawableId) {

        if(Build.VERSION.SDK_INT < 21) {
            return context.getResources().getDrawable(drawableId);
        }
        else {
            return context.getResources().getDrawable(drawableId, null);
        }

    }


}
