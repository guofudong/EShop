package com.gfd.eshop.network.entity;


import com.google.gson.annotations.SerializedName;

public class OrderInfo {

    @SerializedName("pay_code") private String mPayCode;
    @SerializedName("order_amount") private float mPrice;
    @SerializedName("order_id") private int mId;
    @SerializedName("subject") private String mSubject;
    @SerializedName("desc") private String mDesc;

    public String getPayCode() {
        return mPayCode;
    }

    public float getPrice() {
        return mPrice;
    }

    public int getId() {
        return mId;
    }

    public String getSubject() {
        return mSubject;
    }

    public String getDesc() {
        return mDesc;
    }

}
