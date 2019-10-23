package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 会话实体
 */
public class Session {

    @SerializedName("uid") private int mUid;

    @SerializedName("sid") private String mSid;

    public int getUid() {
        return mUid;
    }

    public String getSid() {
        return mSid;
    }

}
