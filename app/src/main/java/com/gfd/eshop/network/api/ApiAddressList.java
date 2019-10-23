package com.gfd.eshop.network.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.RequestParam;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.Address;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 收货地址列表接口.
 */
public class ApiAddressList implements ApiInterface {

    @NonNull
    @Override public String getPath() {
        return ApiPath.ADDRESS_LIST;
    }

    @Nullable
    @Override public RequestParam getRequestParam() {
        return new Req();
    }

    @NonNull @Override public Class<? extends ResponseEntity> getResponseType() {
        return Rsp.class;
    }

    public static class Req extends RequestParam {
        @Override protected int sessionUsage() {
            return SESSION_MANDATORY;
        }
    }

    public static class Rsp extends ResponseEntity {
        @SerializedName("data") private List<Address> mData;
        public List<Address> getData() {
            return mData;
        }
    }

}
