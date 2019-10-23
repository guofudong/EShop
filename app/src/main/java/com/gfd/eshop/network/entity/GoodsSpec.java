package com.gfd.eshop.network.entity;


import com.google.gson.annotations.SerializedName;

public class GoodsSpec {

    public static final int ATTR_TYPE_UNIQUE = 0;
    public static final int ATTR_TYPE_SINGLE = 1;
    public static final int ATTR_TYPE_MULTIPLE = 2;

    @SerializedName("attr_type") private int mAttrType;

    @SerializedName("name") private String mAttrName;

    public static class GoodsValue {

        @SerializedName("id") private int mId;

        @SerializedName("label") private String mLabel;

        @SerializedName("price") private float mPrice;

        @SerializedName("format_price") private String mFormatPrice;
    }

}
