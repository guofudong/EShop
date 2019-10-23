package com.gfd.eshop.network.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.RequestParam;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.Address;
import com.google.gson.annotations.SerializedName;

/**
 * 更新收货地址
 */
public class ApiAddressUpdate implements ApiInterface {

    private final Req mReq;

    public ApiAddressUpdate(Address address, int addressId) {
        mReq = new Req();
        mReq.mAddress = address;
        mReq.mId = addressId;
    }

    @NonNull
    @Override public String getPath() {
        return ApiPath.ADDRESS_UPDATE;
    }

    @Nullable
    @Override public RequestParam getRequestParam() {
        return mReq;
    }

    @NonNull @Override public Class<? extends ResponseEntity> getResponseType() {
        return Rsp.class;
    }

    public static class Req extends RequestParam {
        @SerializedName("address") private Address mAddress;
        @SerializedName("address_id") private int mId;
        @Override protected int sessionUsage() {
            return SESSION_MANDATORY;
        }
    }

    public static class Rsp extends ResponseEntity {
    }

}
