package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 商品详情(购物车)
 */
public class CartGoods {

    @SerializedName("rec_id") private int mRecId;

    @SerializedName("goods_id") private int mGoodsId;

    @SerializedName("goods_name") private String mGoodsName;

    @SerializedName("goods_number") private int mGoodsNumber;

    @SerializedName("img") private Picture mImg;

    @SerializedName("subtotal") private String mTotalPrice;

    public int getRecId() {
        return mRecId;
    }

    public int getGoodsId() {
        return mGoodsId;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public int getGoodsNumber() {
        return mGoodsNumber;
    }

    public Picture getImg() {
        return mImg;
    }

    public String getTotalPrice() {
        return mTotalPrice;
    }

}
