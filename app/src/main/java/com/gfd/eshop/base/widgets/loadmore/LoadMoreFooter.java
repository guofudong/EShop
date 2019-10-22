package com.gfd.eshop.base.widgets.loadmore;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.IntDef;

import com.gfd.eshop.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadMoreFooter extends FrameLayout {

    public static final int STATE_LOADED = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_COMPLETE = 2;

    @IntDef({STATE_LOADED, STATE_LOADING, STATE_COMPLETE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @BindView(R.id.text_load_complete) TextView tvComplete;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    public LoadMoreFooter(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_load_more_footer, this);
        ButterKnife.bind(this);
        setState(STATE_LOADED);
    }

    public void setState(@State int state) {
        switch (state) {
            case STATE_LOADED:
                setVisibility(View.GONE);
                break;
            case STATE_LOADING:
                setVisibility(View.VISIBLE);
                tvComplete.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                setVisibility(View.VISIBLE);
                tvComplete.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                break;
            default:
                throw new IllegalStateException("Illegal state: " + state);
        }
    }
}
