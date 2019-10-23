package com.gfd.eshop.base.wrapper;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Toolbar包装类
 */
public class ToolbarWrapper {

    private BaseActivity mBaseActivity;

    @Nullable
    private TextView mTvTitle;

    /**
     * Activity中构建使用
     * @param activity
     */
    public ToolbarWrapper(BaseActivity activity) {
        mBaseActivity = activity;
        Toolbar toolbar = activity.findViewById(R.id.standard_toolbar);
        init(toolbar);
        setShowBack(true);
        setShowTitle(false);
    }

    /**
     * Fragment中构建使用
     * @param fragment
     */
    public ToolbarWrapper(BaseFragment fragment) {
        mBaseActivity = (BaseActivity) fragment.getActivity();
        //noinspection ConstantConditions
        Toolbar toolbar =fragment.getView().findViewById(R.id.standard_toolbar);
        init(toolbar);
        fragment.setHasOptionsMenu(true); // 设置Fragment有选项菜单.
        setShowBack(false);
        setShowTitle(false);
    }

    /**
     * 设置Toolbar-是否显示标题
     * @param enable
     * @return
     */
    public ToolbarWrapper setShowTitle(boolean enable) {
        getSupportActionBar().setDisplayShowTitleEnabled(enable);
        return this;
    }

    /**
     * 设置Toolbar是否显示返回按钮
     * @param enable
     * @return
     */
    public ToolbarWrapper setShowBack(boolean enable) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        return this;
    }

    /**
     * 设置Toolbar显示的标题
     * @param resId：str资源id
     * @return
     */
    public ToolbarWrapper setCustomTitle(int resId) {
        if (mTvTitle == null) {
            throw new UnsupportedOperationException("No title textview in toolbar.");
        }
        mTvTitle.setText(resId);
        return this;
    }

    /**
     * 初始化操作
     * @param toolbar
     */
    private void init(Toolbar toolbar) {
        mTvTitle =toolbar.findViewById(R.id.standard_toolbar_title);
        mBaseActivity.setSupportActionBar(toolbar);
    }

    /**
     * 获取ActionBar
     * @return
     */
    private ActionBar getSupportActionBar() {
        return mBaseActivity.getSupportActionBar();
    }

}
