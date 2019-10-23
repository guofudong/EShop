package com.gfd.eshop.network.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.core.RequestParam;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.CategoryHome;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 首页-商品分类
 */
public class ApiHomeCategory implements ApiInterface {

    @NonNull
    @Override public String getPath() {
        return ApiPath.HOME_CATEGORY;
    }

    @Nullable
    @Override public RequestParam getRequestParam() {
        return null;
    }

    @NonNull @Override public Class<? extends ResponseEntity> getResponseType() {
        return Rsp.class;
    }

    public static class Rsp extends ResponseEntity {
        @SerializedName("data")
        private List<CategoryHome> mData;
        public List<CategoryHome> getData() {
            return mData;
        }
    }

}
