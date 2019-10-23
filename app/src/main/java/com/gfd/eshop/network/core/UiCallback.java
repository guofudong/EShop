package com.gfd.eshop.network.core;


import android.os.Handler;
import android.os.Looper;

import com.gfd.eshop.R;
import com.gfd.eshop.base.utils.LogUtils;
import com.gfd.eshop.base.wrapper.ToastWrapper;
import com.gfd.eshop.network.EShopClient;
import com.gfd.eshop.network.UserManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * OkHttp网络请求回调-UI线程中调用
 */
public abstract class UiCallback implements Callback {

    /**
     * 主线程中的Handler
     */
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 响应实体类型
     */
    private Class<? extends ResponseEntity> mResponseType;

    /**
     * <p>当网络请求由于以下原因失败时被调用: 请求被取消, 网络连接问题或者请求超时.
     * <p>由于网络可能在数据交换的中途发生故障, 有可能在失败前, 服务器处理了请求.
     *
     * @param call
     * @param e
     */
    @Override
    public final void onFailure(Call call, final IOException e) {
        HANDLER.post(() -> onFailureInUi(e));
    }


    /**
     * 当HTTP响应从服务器成功返回时被调用. 可以通过{@link Response#body}读取响应体.
     * 在响应体变为{@linkplain ResponseBody closed}状态之前, 响应会一直处于可用状态.
     * <p/>
     * 注意: 此回调在后台线程执行, 响应的接收者可以将数据发送到另一个线程来处理.
     * <p/>
     * 注意: 传输层的成功(接受到了HTTP响应码, 响应头和响应体)不代表应用层的成功:
     * {@code response}依然可能是一个失败的响应码, 例如404或500.
     */
    @Override
    public final void onResponse(Call call, Response response) throws IOException {
        final ResponseEntity responseEntity = EShopClient.getInstance()
                .getResponseEntity(response, mResponseType);
        HANDLER.post(() -> onResponseInUi(responseEntity));
    }

    /**
     * 网络请求失败回调方法-UI线程中执行
     *
     * @param e
     */
    private void onFailureInUi(IOException e) {
        ToastWrapper.show(R.string.error_network);
        onBusinessResponse(false, null);
    }

    /**
     * OkHttp网络请求成功回调-UI线程中执行
     *
     * @param responseEntity
     */
    private void onResponseInUi(ResponseEntity responseEntity) {
        if (responseEntity == null || responseEntity.getStatus() == null) {
            throw new RuntimeException("Fatal Api Error!");
        }
        //通过返回的数据中的status来判断请求是否真正的成功
        if (responseEntity.getStatus().isSucceed()) {
            onBusinessResponse(true, responseEntity);
        } else {
            ToastWrapper.show(responseEntity.getStatus().getErrorDesc());
            onBusinessResponse(false, null);
            if (responseEntity.getStatus().getErrorCode() == ApiError.SESSION_EXPIRE) {
                UserManager.getInstance().clear();
            }
        }
    }

    /**
     * 设置响应数据实体类类型
     * @param responseType
     */
    public void setResponseType(Class<? extends ResponseEntity> responseType) {
        mResponseType = responseType;
    }

    /**
     * 请求数据获取成功回调
     *
     * @param success
     * @param responseEntity
     */
    public abstract void onBusinessResponse(boolean success, ResponseEntity responseEntity);

}
