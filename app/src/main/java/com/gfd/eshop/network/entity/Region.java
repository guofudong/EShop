package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 区域.
 */
public class Region {

    @SerializedName("id") private int mId;

    @SerializedName("parent_id") private int mParentId;

    @SerializedName("name") private String mName;

    public int getId() {
        return mId;
    }

    public int getParentId() {
        return mParentId;
    }

    public String getName() {
        return mName;
    }

}
