package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 图片实体.
 */
public class Picture {

    @SerializedName("small") private String mSmall; // 小图

    @SerializedName("thumb") private String mMiddle; // 中图

    @SerializedName("url") private String mLarge; // 大图

    public String getSmall() {
        return mSmall;
    }

    public String getMiddle() {
        return mMiddle;
    }

    public String getLarge() {
        return mLarge;
    }

}
