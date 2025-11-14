package com.gemini.labsense.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthPasswordUpdateDto {
    /**
     * 用户名
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;
    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
