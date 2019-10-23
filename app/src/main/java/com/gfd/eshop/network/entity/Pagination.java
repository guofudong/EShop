package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 分页参数.
 */
public class Pagination {

    @SerializedName("page") private int mPage = 1; // 页码

    @SerializedName("count") private int mCount = 5; // 每页条数

    public Pagination next() {
        mPage++;
        return this;
    }

    public Pagination reset() {
        mPage = 1;
        return this;
    }

    public boolean isFirst() {
        return mPage == 1;
    }

    public int getPage() {
        return mPage;
    }

    public int getCount() {
        return mCount;
    }

}
