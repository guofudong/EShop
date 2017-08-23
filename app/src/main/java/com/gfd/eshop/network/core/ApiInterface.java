package com.gfd.eshop.network.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * <p> Api接口的抽象, 每一个子类代表一个服务器接口.
 */
public interface ApiInterface {

    // 接口路径
    @NonNull String getPath();

    // 请求参数
    @Nullable RequestParam getRequestParam();

    // 响应实体的类型
    @NonNull Class<? extends ResponseEntity> getResponseType();
}
