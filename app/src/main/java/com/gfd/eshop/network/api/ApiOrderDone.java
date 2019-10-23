package com.gfd.eshop.network.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.RequestParam;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.OrderInfo;
import com.google.gson.annotations.SerializedName;

/**
 * 订单生成.
 */
public class ApiOrderDone implements ApiInterface {

    private Req mReq;

    public ApiOrderDone(int payId, int shippingId) {
        mReq = new Req();
        mReq.mPayId = payId;
        mReq.mShippingId = shippingId;
    }

    @NonNull
    @Override public String getPath() {
        return ApiPath.ORDER_DONE;
    }

    @Nullable
    @Override public RequestParam getRequestParam() {
        return mReq;
    }

    @NonNull @Override public Class<? extends ResponseEntity> getResponseType() {
        return Rsp.class;
    }

    public static class Req extends RequestParam {
        @SerializedName("pay_id") private int mPayId;
        @SerializedName("shipping_id") private int mShippingId;
        @Override protected int sessionUsage() {
            return SESSION_MANDATORY;
        }
    }

    public static class Rsp extends ResponseEntity {

        @SerializedName("data") Data mData;
        public Data getData() {
            return mData;
        }

        public static class Data {
            @SerializedName("order_sn") private String mSn;
            @SerializedName("order_id") private String mId;
            @SerializedName("order_info") private OrderInfo mOrderInfo;
            public String getSn() {
                return mSn;
            }
            public String getId() {
                return mId;
            }
            public OrderInfo getOrderInfo() {
                return mOrderInfo;
            }
        }
    }

}
