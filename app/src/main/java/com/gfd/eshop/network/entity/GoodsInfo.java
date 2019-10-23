package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 商品详情实体.
 */
public class GoodsInfo {

    @SerializedName("id") private int mId;

    @SerializedName("goods_name") private String mName;

    @SerializedName("pictures") private List<Picture> mPictures;

    @SerializedName("shop_price") private String mShopPrice;

    @SerializedName("market_price") private String mMarketPrice;

    @SerializedName("specification") private List<GoodsSpec> mSpecs;

    @SerializedName("img") private Picture mImg;

    @SerializedName("goods_number") private int mNumber;

    @SerializedName("collected") private int mCollected;

    public String getMarketPrice() {
        return mMarketPrice;
    }

    public List<Picture> getPictures() {
        return mPictures;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getShopPrice() {
        return mShopPrice;
    }

    public List<GoodsSpec> getSpecs() {
        return mSpecs;
    }

    public Picture getImg() {
        return mImg;
    }

    public int getNumber() {
        return mNumber;
    }

    public boolean isCollected() {
        return mCollected == 1;
    }

}
