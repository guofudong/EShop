package com.gfd.eshop.base.widgets.ptr;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.gfd.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class RefreshHeader extends RelativeLayout implements PtrUIHandler {

    @BindView(R.id.refresh_header_progressbar) ProgressBar progressBar;
    @BindView(R.id.image_refresh_logo) ImageView ivLogo;

    public RefreshHeader(Context context) {
        super(context);
        init(context);
    }

    public RefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override public void onUIReset(PtrFrameLayout frame) {
        ivLogo.setImageResource(R.drawable.refresh_logo_inactive);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override public void onUIRefreshBegin(PtrFrameLayout frame) {
        ivLogo.setImageResource(R.drawable.refresh_logo_active);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame,
                                   boolean isUnderTouch,
                                   byte status,
                                   PtrIndicator ptrIndicator) {
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_refresh_header, this, true);
        ButterKnife.bind(this);
    }
}
