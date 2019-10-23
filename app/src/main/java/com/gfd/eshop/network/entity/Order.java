package com.gfd.eshop.network.entity;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("order_id") private int mId;

    @SerializedName("order_sn") private String mSn;

    @SerializedName("order_time") private String mTime;

    @SerializedName("total_fee") private String mTotalFee;

    @SerializedName("goods_list") private List<OrderGoods> mGoodsList;

    @SerializedName("order_info") private OrderInfo mOrderInfo;

    @SerializedName("formated_shipping_fee") private String mShippingFee;

    public int getId() {
        return mId;
    }

    public String getSn() {
        return mSn;
    }

    public String getTime() {
        return mTime;
    }

    public String getTotalFee() {
        return mTotalFee;
    }

    public List<OrderGoods> getGoodsList() {
        return mGoodsList;
    }

    public OrderInfo getOrderInfo() {
        return mOrderInfo;
    }

    public String getShippingFee() {
        return mShippingFee;
    }

}
