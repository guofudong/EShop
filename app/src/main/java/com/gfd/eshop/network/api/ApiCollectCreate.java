package com.gfd.eshop.network.api;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.RequestParam;
import com.gfd.eshop.network.core.ResponseEntity;
import com.google.gson.annotations.SerializedName;

public class ApiCollectCreate implements ApiInterface {

    private final Req mReq;

    public ApiCollectCreate(int goodsId) {
        mReq = new Req();
        mReq.mGoodsId = goodsId;
    }

    @NonNull @Override public String getPath() {
        return ApiPath.COLLECT_CREATE;
    }

    @Nullable @Override public RequestParam getRequestParam() {
        return mReq;
    }

    @NonNull @Override public Class<? extends ResponseEntity> getResponseType() {
        return Rsp.class;
    }

    public static class Req extends RequestParam {

        @SerializedName("goods_id") private int mGoodsId;

        @Override protected int sessionUsage() {
            return SESSION_MANDATORY;
        }
    }

    public static class Rsp extends ResponseEntity {

    }


}
