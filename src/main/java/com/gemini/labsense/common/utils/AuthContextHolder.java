package com.gemini.labsense.common.utils;

/**
 * 获取当前用户信息帮助类
 */
public class AuthContextHolder {
    private AuthContextHolder() {
        throw new IllegalStateException("非法实例化常量类");
    }

    private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        THREAD_LOCAL.set(userId);
    }

    public static Long getUserId() {
        return THREAD_LOCAL.get();
    }

    public static void removeUserId() {
        THREAD_LOCAL.remove();
    }

}