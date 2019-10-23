package com.gfd.eshop.network.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ApiInterface;
import com.gfd.eshop.network.core.RequestParam;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.Banner;
import com.gfd.eshop.network.entity.SimpleGoods;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 服务器接口: 轮播图和促销商品.
 */
public class ApiHomeBanner implements ApiInterface {

    @NonNull
    @Override public String getPath() {
        return ApiPath.HOME_DATA;
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
        private Data mData;

        public Data getData() { return mData; }

        public static class Data {
            @SerializedName("player") private List<Banner> mBanners;
            @SerializedName("promote_goods") private List<SimpleGoods> mGoodsList;
            public List<Banner> getBanners() {
                return mBanners;
            }
            public List<SimpleGoods> getGoodsList() {
                return mGoodsList;
            }
        }
    }

}
