package com.gfd.eshop.base.wrapper;


import android.content.Context;
import android.widget.Toast;

/**
 * Toast包装类
 */
public class ToastWrapper {

    private static Toast mToast;

    /**
     * 初始化操作
     * @param context：上下文
     */
    public static void init(Context context) {
        mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setDuration(Toast.LENGTH_SHORT);
    }

    /**
     * 以Toast形式显示文本-单例模式
     *
     * @param resId：str资源id
     */
    public static void show(int resId) {
        mToast.setText(resId);
        mToast.show();
    }

    /**
     * 以Toast形式显示文本-单例模式
     *
     * @param charSequence
     */
    public static void show(CharSequence charSequence) {
        mToast.setText(charSequence);
        mToast.show();
    }

}
