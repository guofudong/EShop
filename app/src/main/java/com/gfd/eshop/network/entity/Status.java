package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 响应状态对象.
 */
public class Status {

    @SerializedName("succeed") private int mSucceed;

    @SerializedName("error_code") private int mErrorCode;

    @SerializedName("error_desc") private String mErrorDesc;

    public boolean isSucceed() {
        return mSucceed == 1;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getErrorDesc() {
        return mErrorDesc;
    }

}
