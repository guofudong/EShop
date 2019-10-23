package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 商品分类基类.
 */
public class CategoryBase {

    @SerializedName("id") private int mId;

    @SerializedName("name") private String mName;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

}
