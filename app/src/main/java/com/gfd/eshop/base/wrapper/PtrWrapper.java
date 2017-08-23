package com.gfd.eshop.base.wrapper;


import android.app.Activity;
import android.support.v4.app.Fragment;

import com.gfd.eshop.R;
import com.gfd.eshop.base.widgets.ptr.RefreshHeader;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public abstract class PtrWrapper {

    private PtrFrameLayout mRefreshLayout;

    private PtrDefaultHandler mPtrHandler = new PtrDefaultHandler() {
        @Override public void onRefreshBegin(PtrFrameLayout frame) {
            onRefresh();
        }
    };

    public PtrWrapper(Activity activity) {
        mRefreshLayout = ButterKnife.findById(activity, R.id.standard_refresh_layout);
        initPtr();
    }

    public PtrWrapper(Fragment fragment) {
        //noinspection ConstantConditions
        mRefreshLayout = ButterKnife.findById(fragment.getView(), R.id.standard_refresh_layout);
        initPtr();
    }

    public void postRefresh(long delay) {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override public void run() {
                mRefreshLayout.autoRefresh();
            }
        }, delay);
    }

    public void autoRefresh() {
        mRefreshLayout.autoRefresh();
    }

    public void stopRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.refreshComplete();
        }
    }

    public abstract void onRefresh();

    private void initPtr() {
        assert mRefreshLayout != null;
        mRefreshLayout.disableWhenHorizontalMove(true);
        RefreshHeader refreshHeader = new RefreshHeader(mRefreshLayout.getContext());
        mRefreshLayout.setHeaderView(refreshHeader);
        mRefreshLayout.addPtrUIHandler(refreshHeader);
        mRefreshLayout.setPtrHandler(mPtrHandler);
    }

}
