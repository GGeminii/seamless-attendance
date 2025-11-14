package com.gemini.labsense.common.constant;

public class CacheConstant {
    private CacheConstant() {
        throw new IllegalStateException("非法实例化常量类");
    }

    private static final String CACHE_PREFIX = "labsense:";

    public static final String AUTH_LOGIN_KEY_PREFIX = CACHE_PREFIX + "login:";
    // 登录缓存有效期: 30天
    public static final int AUTH_LOGIN_KEY_TIMEOUT = 60 * 60 * 24 * 30;

    // 邮件验证码缓存有效期: 5分钟
    public static final String MAIL_VERIFY_CODE_KEY_PREFIX = CACHE_PREFIX + "verify-code:";
    public static final int MAIL_VERIFY_CODE_KEY_TIMEOUT = 60 * 5;
}
