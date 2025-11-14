package com.gemini.labsense.pojo.dto;

import lombok.Data;

/**
 * 登录对象
 */
@Data
public class AuthLoginDto {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}