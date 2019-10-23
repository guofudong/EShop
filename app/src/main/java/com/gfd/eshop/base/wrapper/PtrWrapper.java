package com.gfd.eshop.base.wrapper;


import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.gfd.eshop.R;
import com.gfd.eshop.base.widgets.ptr.RefreshHeader;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 下拉刷新包装类的基类
 */
public abstract class PtrWrapper {

    private PtrFrameLayout mRefreshLayout;

    /** 刷新延时，调用Handler*/
    private PtrDefaultHandler mPtrHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            onRefresh();
        }
    };

    /**
     * Activity中构建使用
     * @param activity
     */
    protected PtrWrapper(Activity activity) {
        mRefreshLayout = activity.findViewById(R.id.standard_refresh_layout);
        initPtr();
    }

    /**
     * Fragment中构建使用
     * @param fragment
     */
    protected PtrWrapper(Fragment fragment) {
        //noinspection ConstantConditions
        mRefreshLayout = fragment.getView().findViewById( R.id.standard_refresh_layout);
        initPtr();
    }

    /** 初始化*/
    private void initPtr() {
        assert mRefreshLayout != null;
        mRefreshLayout.disableWhenHorizontalMove(true);
        RefreshHeader refreshHeader = new RefreshHeader(mRefreshLayout.getContext());
        mRefreshLayout.setHeaderView(refreshHeader);
        mRefreshLayout.addPtrUIHandler(refreshHeader);
        mRefreshLayout.setPtrHandler(mPtrHandler);
    }

    /**
     * 下拉刷新
     * @param delay：延时时间 单位：毫秒
     */
    public void postRefresh(long delay) {
        mRefreshLayout.postDelayed(() -> mRefreshLayout.autoRefresh(), delay);
    }

    /** 自动刷新*/
    public void autoRefresh() {
        mRefreshLayout.autoRefresh();
    }

    /** 停止刷新*/
    public void stopRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.refreshComplete();
        }
    }

    /** 刷新回调方法*/
    public abstract void onRefresh();

}
