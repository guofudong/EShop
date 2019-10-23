package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 购物车结算.
 */
public class CartBill {

    @SerializedName("goods_price") private String mGoodsPrice;

    @SerializedName("real_goods_count") private int mGoodsCount; // 实物商品数量

    public String getGoodsPrice() {
        return mGoodsPrice;
    }

    public int getGoodsCount() {
        return mGoodsCount;
    }

}
