package com.gfd.eshop.network.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.RequestParam;
import com.gfd.eshop.network.core.ResponseEntity;
import com.google.gson.annotations.SerializedName;

/**
 * 删除收货地址
 */
public class ApiAddressDelete implements ApiInterface {

    private Req mReq;

    public ApiAddressDelete(int addressId) {
        mReq = new Req();
        mReq.mId = addressId;
    }

    @NonNull
    @Override public String getPath() {
        return ApiPath.ADDRESS_DELETE;
    }

    @Nullable
    @Override public RequestParam getRequestParam() {
        return mReq;
    }

    @NonNull @Override public Class<? extends ResponseEntity> getResponseType() {
        return Rsp.class;
    }

    public static class Req extends RequestParam {
        @SerializedName("address_id") private int mId;
        @Override protected int sessionUsage() {
            return SESSION_MANDATORY;
        }
    }

    public static class Rsp extends ResponseEntity {
    }

}
