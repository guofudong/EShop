package com.gfd.eshop.network.entity;


import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id") private int mId;
    @SerializedName("name") private String mName;
    @SerializedName("rank_name") private String mRankName;
    @SerializedName("rank_level") private int mRankLevel;
    @SerializedName("order_num") private OrderNum mOrderNum;

    public static class OrderNum {
        @SerializedName("await_pay") private int mAwaitPay;
        @SerializedName("await_ship") private int mAwaitShip;
        @SerializedName("shipped") private int mShipped;
        @SerializedName("finished") private int mFinished;

        public int getAwaitPay() {
            return mAwaitPay;
        }

        public int getAwaitShip() {
            return mAwaitShip;
        }

        public int getShipped() {
            return mShipped;
        }

        public int getFinished() {
            return mFinished;
        }
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getRankName() {
        return mRankName;
    }

    public int getRankLevel() {
        return mRankLevel;
    }

    public OrderNum getOrderNum() {
        return mOrderNum;
    }

}
