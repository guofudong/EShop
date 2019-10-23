package com.gfd.eshop.network.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.core.RequestParam;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.CategoryPrimary;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 服务器接口: 获取商品分类.
 */
public class ApiCategory implements ApiInterface {

    @NonNull
    @Override public String getPath() {
        return ApiPath.CATEGORY;
    }

    @Nullable
    @Override public RequestParam getRequestParam() {
        return null;
    }

    @NonNull @Override public Class<? extends ResponseEntity> getResponseType() {
        return Rsp.class;
    }

    public static class Rsp extends ResponseEntity {
        @SerializedName("data") private List<CategoryPrimary> mData;
        public List<CategoryPrimary> getData() {
            return mData;
        }
    }

}
