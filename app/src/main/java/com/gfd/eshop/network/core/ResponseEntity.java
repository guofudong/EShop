package com.gfd.eshop.network.core;

import com.gfd.eshop.network.entity.Status;
import com.google.gson.annotations.SerializedName;

/**
 * 响应体的基类.
 */
public abstract class ResponseEntity {

    @SerializedName("status") private Status mStatus;

    public Status getStatus() {
        return mStatus;
    }

}
