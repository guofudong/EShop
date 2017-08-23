package com.gfd.eshop.base.wrapper;


import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class ToastWrapper {

    private static Toast mToast;

    @SuppressLint("ShowToast")
    public static void init(Context context) {
        mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setDuration(Toast.LENGTH_SHORT);
    }

    public static void show(int resId) {
        mToast.setText(resId);
        mToast.show();
    }

    public static void show(CharSequence charSequence) {
        mToast.setText(charSequence);
        mToast.show();
    }
}
