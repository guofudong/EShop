package com.gfd.eshop.network.entity;


import com.google.gson.annotations.SerializedName;

public class OrderGoods {

    @SerializedName("goods_id") private int mGoodsId;

    @SerializedName("name") private String mGoodsName;

    @SerializedName("goods_number") private int mGoodsNumber;

    @SerializedName("subtotal") private String mSubTotal;

    @SerializedName("formated_shop_price") private String mFormatedPrice;

    @SerializedName("img") private Picture mImg;

    public int getGoodsId() {
        return mGoodsId;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public int getGoodsNumber() {
        return mGoodsNumber;
    }

    public String getSubTotal() {
        return mSubTotal;
    }

    public String getFormatedPrice() {
        return mFormatedPrice;
    }

    public Picture getImg() {
        return mImg;
    }

}
