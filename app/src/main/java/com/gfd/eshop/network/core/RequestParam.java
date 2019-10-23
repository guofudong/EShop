package com.gfd.eshop.network.core;


import androidx.annotation.IntDef;

import com.gfd.eshop.network.UserManager;
import com.gfd.eshop.network.entity.Session;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 请求参数的基类.
 */
public abstract class RequestParam {

    protected static final int SESSION_NO_NEED = 0;
    protected static final int SESSION_OPTIONAL = 1;
    public static final int SESSION_MANDATORY = 2;

    @IntDef({SESSION_NO_NEED, SESSION_OPTIONAL, SESSION_MANDATORY})
    @Retention(RetentionPolicy.SOURCE)
    @interface SessionUsage {
    }

    @SerializedName("session") private Session mSession;

    public RequestParam() {
        int usage = sessionUsage();
        if (usage == SESSION_NO_NEED) return;
        Session session = UserManager.getInstance().getSession();
        if (usage == SESSION_MANDATORY && session == null) {
            throw new IllegalStateException("Session is mandatory.");
        }
        mSession = session;
    }

    @SessionUsage protected abstract int sessionUsage();

}
