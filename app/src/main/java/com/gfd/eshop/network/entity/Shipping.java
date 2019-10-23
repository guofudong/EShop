package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 派送方式.
 */
public class Shipping {

    @SerializedName("shipping_id") private int mId;

    @SerializedName("shipping_name") private String mName;

    @SerializedName("format_shipping_fee") private String mPrice;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPrice() {
        return mPrice;
    }

}
