package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 支付方式.
 */
public class Payment {

    @SerializedName("pay_id") private int mId;

    @SerializedName("pay_name") private String mName;

    @SerializedName("format_pay_fee") private String mPrice;

    @SerializedName("pay_code") private String mCode;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getCode() {
        return mCode;
    }

}
