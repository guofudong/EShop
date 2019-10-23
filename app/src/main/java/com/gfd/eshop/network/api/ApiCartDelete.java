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
 * 服务器接口: 从购物车删除商品.
 */
public class ApiCartDelete implements ApiInterface {

    private Req mReq;

    public ApiCartDelete(int recId) {
        mReq = new Req();
        mReq.mRecId = recId;
    }

    @NonNull
    @Override public String getPath() {
        return ApiPath.CART_DELETE;
    }

    @Nullable
    @Override public RequestParam getRequestParam() {
        return mReq;
    }

    @NonNull @Override public Class<? extends ResponseEntity> getResponseType() {
        return Rsp.class;
    }

    public static class Req extends RequestParam {
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
