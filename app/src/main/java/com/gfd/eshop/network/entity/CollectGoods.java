package com.gfd.eshop.network.entity;


import com.google.gson.annotations.SerializedName;

public class CollectGoods extends SimpleGoods {

    @SerializedName("rec_id") private int mRecId; // 收藏id

    public int getRecId() {
        return mRecId;
    }

}
