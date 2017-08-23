package com.gfd.eshop.base.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gfd.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleNumberPicker extends RelativeLayout {


    @BindView(R.id.text_number) TextView tvNumber;
    @BindView(R.id.image_minus) ImageView ivMinus;

    private OnNumberChangedListener mOnNumberChangedListener;

    private int mMin = 0;

    public SimpleNumberPicker(Context context) {
        super(context);
        init(context, null);
    }

    public SimpleNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SimpleNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @OnClick({R.id.image_plus, R.id.image_minus})
    public void onClick(View view) {
        if (view.getId() == R.id.image_minus) {

            if (getNumber() == mMin) {
                return;
            }

            setNumber(getNumber() - 1);
        } else {
            setNumber(getNumber() + 1);
        }

        if (getNumber() == mMin) {
            ivMinus.setImageResource(R.drawable.btn_minus_gray);
        } else {
            ivMinus.setImageResource(R.drawable.btn_minus);
        }

        if (mOnNumberChangedListener != null) {
            mOnNumberChangedListener.onNumberChanged(getNumber());
        }
    }

    public int getNumber() {
        String str = tvNumber.getText().toString();
        return Integer.parseInt(str);
    }

    public void setNumber(int number) {

        if (number < mMin) {
            throw new IllegalArgumentException("Min is " + mMin + " while number is " + number);
        }

        tvNumber.setText(String.valueOf(number));
    }

    public void setOnNumberChangedListener(OnNumberChangedListener onNumberChangedListener) {
        mOnNumberChangedListener = onNumberChangedListener;
    }

    public interface OnNumberChangedListener {
        void onNumberChanged(int number);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_simple_number_picker, this, true);
        ButterKnife.bind(this);

        if (attrs == null) return;

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SimpleNumberPicker);

        mMin = typedArray.getInteger(R.styleable.CustomProgressBar_loading_color, 0);

        typedArray.recycle();
    }
}
