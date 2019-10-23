package com.gfd.eshop.network;

import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.core.UiCallback;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * <p>网络接口的操作类, 网络请求使用{@link OkHttpClient}实现.
 */
public class EShopClient {

    private static final String BASE_URL = "https://www.fastmock.site/mock/f9c998b9cc741ac815efc2c8d1e09f5c/shop/";

    private static EShopClient sInstance;

    private final OkHttpClient mOkHttpClient;
    private final Gson mGson;
    private boolean mShowLog = false;

    /**
     * 获取实例
     * @return
     */
    public static EShopClient getInstance() {
        if (sInstance == null) {
            sInstance = new EShopClient();
        }
        return sInstance;
    }

    private EShopClient() {
        mGson = new Gson();
        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(mLoggingInterceptor)
                .build();
    }

    /**
     * 同步执行Api请求.
     *
     * @param apiInterface 服务器Api接口.
     * @param <T>          响应体的实体类型.
     * @return 响应数据实体.
     * @throws IOException 请求被取消, 连接超时, 失败的响应码等等.
     */
    public <T extends ResponseEntity> T execute(ApiInterface apiInterface) throws IOException {
        Response response = newApiCall(apiInterface, null).execute();
        //noinspection unchecked
        Class<T> entityClass = (Class<T>) apiInterface.getResponseType();
        return getResponseEntity(response, entityClass);
    }

    /**
     * 异步执行Api请求.
     *
     * @param apiInterface 服务器Api接口.
     * @param uiCallback   回调
     * @return {@link Call}对象.
     */
    public Call enqueue(ApiInterface apiInterface, UiCallback uiCallback, String tag) {
        Call call = newApiCall(apiInterface, tag);
        uiCallback.setResponseType(apiInterface.getResponseType());
        call.enqueue(uiCallback);
        return call;
    }

    /**
     * 取消请求
     * @param tag：请求tag
     */
    public void cancelByTag(String tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
    }

    /**
     * 是否输出日志
     * @param showLog
     */
    void setShowLog(boolean showLog) {
        mShowLog = showLog;
    }

    /**
     * 获取响应数据实体类
     * @param response：请求响应
     * @param clazz：实体类类型class
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends ResponseEntity> T getResponseEntity(Response response, Class<T> clazz)
            throws IOException {
        if (!response.isSuccessful()) {
            throw new IOException("Response code is " + response.code());
        }
        return mGson.fromJson(response.body().charStream(), clazz);
    }

    /**
     * 构建请求
     * @param apiInterface
     * @param tag
     * @return
     */
    private Call newApiCall(ApiInterface apiInterface, String tag) {
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + apiInterface.getPath());
        if (apiInterface.getRequestParam() != null) {
            String param = mGson.toJson(apiInterface.getRequestParam());
            FormBody formBody = new FormBody.Builder()
                    .add("json", param)
                    .build();
            builder.post(formBody);
        }
        builder.tag(tag);
        Request request = builder.build();
        return mOkHttpClient.newCall(request);
    }

}
