package com.gfd.eshop.base.wrapper;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.BaseFragment;

import butterknife.ButterKnife;

public class ToolbarWrapper {

    private BaseActivity mBaseActivity;

    @Nullable
    private TextView mTvTitle;

    public ToolbarWrapper(BaseActivity activity) {
        mBaseActivity = activity;
        Toolbar toolbar = activity.findViewById(R.id.standard_toolbar);
        init(toolbar);

        setShowBack(true);
        setShowTitle(false);
    }

    public ToolbarWrapper(BaseFragment fragment) {
        mBaseActivity = (BaseActivity) fragment.getActivity();
        //noinspection ConstantConditions

        Toolbar toolbar =fragment.getView().findViewById(R.id.standard_toolbar);
        init(toolbar);
        fragment.setHasOptionsMenu(true); // 设置Fragment有选项菜单.

        setShowBack(false);
        setShowTitle(false);
    }

    public ToolbarWrapper setShowTitle(boolean enable) {
        getSupportActionBar().setDisplayShowTitleEnabled(enable);
        return this;
    }

    public ToolbarWrapper setShowBack(boolean enable) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        return this;
    }

    public ToolbarWrapper setCustomTitle(int resId) {
        if (mTvTitle == null) {
            throw new UnsupportedOperationException("No title textview in toolbar.");
        }
        mTvTitle.setText(resId);
        return this;
    }

    private void init(Toolbar toolbar) {
        mTvTitle =toolbar.findViewById(R.id.standard_toolbar_title);
        mBaseActivity.setSupportActionBar(toolbar);
    }

    private ActionBar getSupportActionBar() {
        return mBaseActivity.getSupportActionBar();
    }
}
