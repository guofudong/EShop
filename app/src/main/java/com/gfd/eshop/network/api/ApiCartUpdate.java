package com.gfd.eshop.network.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.RequestParam;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.CartBill;
import com.google.gson.annotations.SerializedName;

/**
 * 更新购物车
 */
public class ApiCartUpdate implements ApiInterface {

    private Req mReq;

    public ApiCartUpdate(int recId, int newNumber) {
        mReq = new Req();
        mReq.mRecId = recId;
        mReq.mNumber = newNumber;
    }


    @NonNull
    @Override public String getPath() {
        return ApiPath.CART_UPDATE;
    }

    @Nullable
    @Override public RequestParam getRequestParam() {
        return mReq;
    }

    @NonNull @Override public Class<? extends ResponseEntity> getResponseType() {
        return Rsp.class;
    }

    public static class Req extends RequestParam {
        @SerializedName("new_number") private int mNumber;
        @SerializedName("rec_id") int mRecId;
        @Override protected int sessionUsage() {
            return SESSION_MANDATORY;
        }
    }

    public static class Rsp extends ResponseEntity {
        @SerializedName("total") private CartBill mCartBill;
        public CartBill getCartBill() {
            return mCartBill;
        }
    }

}
