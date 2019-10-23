package com.gfd.eshop.base.wrapper;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.BaseFragment;

/**
 * 加载对话框
 */
public class ProgressWrapper extends DialogFragment {

    public ProgressWrapper() {
        // Empty constructor required for DialogFragment
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //点击外部不可取消
        setCancelable(false);
    }

    @NonNull
    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));  // 无背景色
        window.requestFeature(Window.FEATURE_NO_TITLE);  // 无标题栏
        return dialog;
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false);
    }

    @Override public void onStart() {
        super.onStart();
        //设置对话框属性
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f; // 背景透明度: 全透明
        window.setAttributes(windowParams);
    }

    /**
     * Activity中显示加载对话框
     * @param activity
     */
    public void showProgress(BaseActivity activity) {
        if (isAdded()) return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        show(activity.getSupportFragmentManager(), "ProgressWrapper");
    }

    /**
     * Fragment中显示加载对话框
     * @param fragment
     */
    public void showProgress(BaseFragment fragment) {
        if (isAdded()) return;
        show(fragment.getChildFragmentManager(), "ProgressWrapper");
    }

    /** 隐藏对话框*/
    public void dismissProgress() {
        if (isAdded()) dismiss();
    }

}
