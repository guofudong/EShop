package com.gfd.eshop.network.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.RequestParam;
import com.gfd.eshop.network.core.ResponseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 服务器接口: 添加到购物车.
 */
public class ApiCartCreate implements ApiInterface {

    private Req mReq;

    public ApiCartCreate(int goodsId, int number) {
        mReq = new Req();
        mReq.mId = goodsId;
        mReq.mNumber = number;
    }

    @NonNull
    @Override public String getPath() {
        return ApiPath.CART_CREATE;
    }

    @Nullable
    @Override public RequestParam getRequestParam() {
        return mReq;
    }

    @NonNull @Override public Class<? extends ResponseEntity> getResponseType() {
        return Rsp.class;
    }

    public static class Req extends RequestParam {
        @SerializedName("goods_id") private int mId;
        @SerializedName("number") private int mNumber;
        @SerializedName("spec") private List<Integer> mSpecs;
        @Override protected int sessionUsage() {
            return SESSION_MANDATORY;
        }
    }

    public static class Rsp extends ResponseEntity {
    }

}
