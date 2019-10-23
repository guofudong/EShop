package com.gfd.eshop.network.entity;


import com.google.gson.annotations.SerializedName;

/**
 * 简单商品对象.
 */
public class SimpleGoods {

    @SerializedName("goods_id") private int mGoodsId;

    @SerializedName("id") private int mId; // 商品ID

    @SerializedName("name") private String mName; // 商品名称

    @SerializedName("market_price") private String mMarketPrice; // 商场价格

    @SerializedName("shop_price") private String mShopPrice; // 商店价格

    @SerializedName("promote_price") private String mPromotePrice; // 促销价格

    @SerializedName("brief") private String mBrief; // 简要

    @SerializedName("img") private Picture mImg; // 图片

    public int getId() {
        if (mId != 0) return mId;
        return mGoodsId;
    }

    public String getName() {
        return mName;
    }

    public String getMarketPrice() {
        return mMarketPrice;
    }

    public String getShopPrice() {
        return mShopPrice;
    }

    public String getPromotePrice() {
        return mPromotePrice;
    }

    public String getBrief() {
        return mBrief;
    }

    public Picture getImg() {
        return mImg;
    }

}
