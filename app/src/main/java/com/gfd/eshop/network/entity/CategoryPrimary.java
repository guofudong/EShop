package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * 商品一级分类.
 */
public class CategoryPrimary extends CategoryBase {

    @SerializedName("children") private List<CategoryBase> mChildren = Collections.emptyList();

    public List<CategoryBase> getChildren() {
        return mChildren;
    }

}
