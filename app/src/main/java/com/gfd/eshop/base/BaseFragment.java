package com.gfd.eshop.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.EShopClient;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.core.UiCallback;
import com.gfd.eshop.network.event.UserEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 通用Fragment基类.
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbind;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewLayout(), container, false);
        mUnbind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        EventBus.getDefault().register(this);
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        EShopClient.getInstance().cancelByTag(getClass().getSimpleName());
        EventBus.getDefault().unregister(this);
        mUnbind.unbind();
        mUnbind = null;
    }

    /**
     * 执行异步请求
     * @param apiInterface
     * @return
     */
    protected final Call enqueue(final ApiInterface apiInterface) {
        UiCallback uiCallback = new UiCallback() {
            @Override
            public void onBusinessResponse(boolean success, ResponseEntity responseEntity) {
                BaseFragment.this.onBusinessResponse(apiInterface.getPath(), success, responseEntity);
            }
        };
        return EShopClient.getInstance().enqueue(apiInterface, uiCallback, getClass().getSimpleName());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(UserEvent event) {
    }

    @LayoutRes
    protected abstract int getContentViewLayout();

    protected abstract void initView();

    protected abstract void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp);

}
