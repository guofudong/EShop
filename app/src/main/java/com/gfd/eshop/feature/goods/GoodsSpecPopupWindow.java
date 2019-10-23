package com.gfd.eshop.feature.goods;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.glide.GlideUtils;
import com.gfd.eshop.base.widgets.SimpleNumberPicker;
import com.gfd.eshop.base.wrapper.ToastWrapper;
import com.gfd.eshop.network.entity.GoodsInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品属性弹窗
 */
public class GoodsSpecPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    @BindView(R.id.image_goods) ImageView ivGoods;
    @BindView(R.id.text_goods_price) TextView tvPrice;
    @BindView(R.id.number_picker) SimpleNumberPicker numberPicker;
    @BindView(R.id.text_number_value) TextView tvNumber;
    @BindView(R.id.text_inventory_value) TextView tvInventory;

    private ViewGroup mParent;
    private GoodsInfo mGoodsInfo;
    private BaseActivity mActivity;

    private OnConfirmListener mConfirmListener;

    GoodsSpecPopupWindow(BaseActivity activity, @NonNull GoodsInfo goodsInfo) {
        mActivity = activity;
        mGoodsInfo = goodsInfo;
        mParent = (ViewGroup) activity.getWindow().getDecorView();
        Context context = mParent.getContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.popup_goods_spec, mParent, false);

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(context.getResources().getDimensionPixelSize(R.dimen.size_400));

        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);

        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setOnDismissListener(this);

        ButterKnife.bind(this, view);
        initView();
    }

    public void show(OnConfirmListener listener) {
        mConfirmListener = listener;
        showAtLocation(mParent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.6f);
    }

    @Override public void onDismiss() {
        backgroundAlpha(1.0f);
        mConfirmListener = null;
    }

    @OnClick(R.id.button_ok) void onClick() {
        int number = numberPicker.getNumber();
        if (number == 0) {
            ToastWrapper.show(R.string.goods_msg_must_choose_number);
            return;
        }
        mConfirmListener.onConfirm(number);
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }

    private void initView() {
        GlideUtils.loadPicture(mGoodsInfo.getImg(), ivGoods);
        tvPrice.setText(mGoodsInfo.getShopPrice());
        tvInventory.setText(String.valueOf(mGoodsInfo.getNumber()));
        numberPicker.setOnNumberChangedListener(number -> tvNumber.setText(String.valueOf(number)));
    }

    public interface OnConfirmListener {
        void onConfirm(int number);
    }

}
