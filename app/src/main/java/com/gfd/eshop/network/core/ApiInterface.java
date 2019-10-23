package com.gfd.eshop.network.core;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Api接口的抽象, 每一个子类代表一个服务器接口.
 */
public interface ApiInterface {

    /**
     * 获取接口路径
     * @return
     */
    @NonNull
    String getPath();

    /**
     * 获取请求参数
     * @return
     */
    @Nullable
    RequestParam getRequestParam();

    /**
     * 响应实体的类型
     * @return
     */
    @NonNull Class<? extends ResponseEntity> getResponseType();

}
