package com.gfd.eshop.base.wrapper;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AlertWrapper extends DialogFragment {

    @BindView(R.id.text_alert) TextView tvAlert;

    private Unbinder mUnbinder;
    private View.OnClickListener mConfirmListener;
    private View.OnClickListener mCancelListener;
    private int mAlertId;

    public AlertWrapper() {
        // Empty constructor required for DialogFragment
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));  // 无背景色
        window.requestFeature(Window.FEATURE_NO_TITLE);  // 无标题栏

        return dialog;
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alert_dialog, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        if (mAlertId != 0) tvAlert.setText(mAlertId);
        return view;
    }

    @Override public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.6f;

        window.setAttributes(windowParams);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mUnbinder = null;
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

    public AlertWrapper setAlertText(int resId) {
        mAlertId = resId;
        if (tvAlert != null) tvAlert.setText(mAlertId);
        return this;
    }

    public AlertWrapper setConfirmListener(View.OnClickListener confirmListener) {
        mConfirmListener = confirmListener;
        return this;
    }

    public AlertWrapper setCancelLisener(View.OnClickListener cancelListener) {
        mCancelListener = cancelListener;
        return this;
    }

    public void showAlert(BaseFragment fragment) {
        if (isAdded()) return;
        show(fragment.getChildFragmentManager(), "AlertWrapper");
    }

    public void showAlert(BaseActivity activity) {
        if (isAdded()) return;
        show(activity.getSupportFragmentManager(), "AlertWrapper");
    }
}
