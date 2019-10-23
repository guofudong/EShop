package com.gfd.eshop.network.entity;

import androidx.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 搜索过滤器.
 */
public class Filter {

    public static final String SORT_PRICE_DESC = "price_desc"; // 价格由高到低
    public static final String SORT_PRICE_ASC = "price_asc"; // 价格由低到高
    public static final String SORT_IS_HOT = "is_hot"; // 热度

    @StringDef({SORT_PRICE_DESC, SORT_PRICE_ASC, SORT_IS_HOT})
    @Retention(RetentionPolicy.SOURCE)
    @interface SortOrder {
    }

    @SerializedName("keywords") private String mKeywords;

    @SerializedName("category_id") private int mCategoryId;

    @SerializedName("sort_by") @SortOrder private String mSortBy = SORT_IS_HOT;

    public void setKeywords(String keywords) {
        mKeywords = keywords;
    }

    public void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
    }

    public void setSortBy(@SortOrder String sortBy) {
        mSortBy = sortBy;
    }

    public String getSortBy() {
        return mSortBy;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

}
