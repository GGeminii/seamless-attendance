package com.gemini.labsense.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MailCodeVerifyDto {
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "输入验证码不能为空")
    private String code;
}
