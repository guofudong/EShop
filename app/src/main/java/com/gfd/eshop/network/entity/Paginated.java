package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 分页结果
 */
public class Paginated {

    @SerializedName("total") private int mTotal; // 总数

    @SerializedName("count") private int mCount; // 返回条数

    @SerializedName("more") private int mMore; // 是否还有更多 (1为是, 0为否)

    public int getTotal() {
        return mTotal;
    }

    public int getCount() {
        return mCount;
    }

    public boolean hasMore() {
        return mMore == 1;
    }

}
