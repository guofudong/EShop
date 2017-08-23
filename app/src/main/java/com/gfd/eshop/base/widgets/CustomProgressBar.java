package com.gfd.eshop.base.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.gfd.eshop.R;
import com.github.ybq.android.spinkit.style.FadingCircle;

public class CustomProgressBar extends ProgressBar {
    public CustomProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {


        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomProgressBar);

        int color = typedArray.getColor(R.styleable.CustomProgressBar_loading_color, 0xFFFFFF);

        FadingCircle fadingCircle = new FadingCircle();
        //noinspection deprecation
        fadingCircle.setColor(color);
        setIndeterminateDrawable(fadingCircle);

        typedArray.recycle();
    }
}
