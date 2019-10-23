package com.gfd.eshop.base.wrapper;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 显示图片对话框
 */
public class PhotoWrapper extends DialogFragment {

    @BindView(R.id.image_photo) ImageView ivPhoto;

    private Unbinder mUnbind;
    private String mUrl;

    public PhotoWrapper() {
        // Empty constructor required for DialogFragment
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
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
        View view = inflater.inflate(R.layout.fragment_photo_dialog, container, false);
        mUnbind = ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ivPhoto.setImageDrawable(new ColorDrawable());
        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                ivPhoto.setImageBitmap(resource);
            }
        };
        Glide.with(getActivity())
                .load(mUrl)
                .asBitmap()
                .into(target);
    }

    @Override public void onStart() {
        super.onStart();
        //设置对话框属性
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.6f;
        window.setAttributes(windowParams);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mUnbind.unbind();
        mUnbind = null;
    }

    /**
     * 在Fragment中显示图片对话框
     * @param fragment
     * @param url：图片地址
     */
    public void showPhoto(BaseFragment fragment, String url) {
        if (isAdded()) return;
        mUrl = url;
        show(fragment.getChildFragmentManager(), "PhotoWrapper");
    }

    /**
     * 在Activity中显示图片对话框
     * @param activity
     * @param url：图片地址
     */
    public void showPhoto(BaseActivity activity, String url) {
        if (isAdded()) return;
        mUrl = url;
        show(activity.getSupportFragmentManager(), "PhotoWrapper");
    }

}
