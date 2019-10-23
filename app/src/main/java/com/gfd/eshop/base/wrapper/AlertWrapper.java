package com.gfd.eshop.base.wrapper;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 提示对话框
 */
public class AlertWrapper extends DialogFragment {

    @BindView(R.id.text_alert) TextView tvAlert;

    private Unbinder mUnbind;
    private View.OnClickListener mConfirmListener;
    private View.OnClickListener mCancelListener;
    private int mAlertId;

    public AlertWrapper() {
        // Empty constructor required for DialogFragment
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置点击外部可以消失
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
        View view = inflater.inflate(R.layout.fragment_alert_dialog, container, false);
        mUnbind = ButterKnife.bind(this, view);
        if (mAlertId != 0) tvAlert.setText(mAlertId);
        return view;
    }

    @Override public void onStart() {
        super.onStart();
        //设置对话框属性
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.6f;
        window.setAttributes(windowParams);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mUnbind.unbind();
        mUnbind = null;
    }

    @OnClick({R.id.text_cancel, R.id.text_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_cancel:
                if (mCancelListener != null) {
                    mCancelListener.onClick(view);
                }
                dismiss();
                break;
            case R.id.text_ok:
                if (mConfirmListener != null) {
                    mConfirmListener.onClick(view);
                } else {
                    dismiss();
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    /**
     * 设置提示内容
     * @param resId：str资源id
     * @return：当前调用的对象
     */
    public AlertWrapper setAlertText(int resId) {
        mAlertId = resId;
        if (tvAlert != null) tvAlert.setText(mAlertId);
        return this;
    }

    /**
     * 设置确认按钮点击监听
     * @param confirmListener
     * @return
     */
    public AlertWrapper setConfirmListener(View.OnClickListener confirmListener) {
        mConfirmListener = confirmListener;
        return this;
    }

    /**
     * 设置取消按钮点击监听
     * @param cancelListener
     * @return
     */
    public AlertWrapper setCancelListener(View.OnClickListener cancelListener) {
        mCancelListener = cancelListener;
        return this;
    }

    /**
     * 在Fragment中显示该对话框
     * @param fragment
     */
    public void showAlert(BaseFragment fragment) {
        if (isAdded()) return;
        show(fragment.getChildFragmentManager(), "AlertWrapper");
    }

    /**
     * 在Activity中显示该对话框
     * @param activity
     */
    public void showAlert(BaseActivity activity) {
        if (isAdded()) return;
        show(activity.getSupportFragmentManager(), "AlertWrapper");
    }

}
