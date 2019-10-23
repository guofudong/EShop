package com.gfd.eshop.base.utils;

import android.util.Log;

/**
 * 日志工具类，只有Debug模式下才打印日志
 */
public class LogUtils {

    private LogUtils() {
        throw new UnsupportedOperationException("LogUtils can't be instantiated.");
    }

    private static boolean isDebug = true;
    private static final String TAG = "EShop";

    /**
     * verbose级别日志
     * @param msg
     */
    public static void verbose(String msg) {
        if (isDebug) Log.v(TAG, msg);
    }

    /**
     * info级别日志
     * @param msg
     */
    public static void info(String msg) {
        if (isDebug) Log.i(TAG, msg);
    }

    /**
     * debug级别日志
     * @param msg
     * @param objects
     */
    public static void debug(String msg, Object... objects) {
        if (isDebug) {
            msg = String.format(msg, objects);
            Log.d(TAG, msg);
        }
    }

    /**
     * error级别日志
     * @param msg
     */
    public static void error(String msg) {
        if (isDebug) Log.e(TAG, msg);
    }

    /**
     * error级别日志
     * @param msg
     * @param t
     */
    public static void error(String msg, Throwable t) {
        if (isDebug) Log.e(TAG, msg, t);
    }

}
