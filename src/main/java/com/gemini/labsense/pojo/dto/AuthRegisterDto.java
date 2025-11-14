package com.gemini.labsense.pojo.dto;

import com.gemini.labsense.common.constant.SystemConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AuthRegisterDto {
    /**
     * 用户名（学号/工号）
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 加密密码（bcrypt哈希）
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 真实姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = SystemConstant.PHONE_REGEX, message = "手机格式错误")
    private String phone;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = SystemConstant.EMAIL_REGEX, message = "邮箱格式错误")
    private String email;

    /**
     * 角色ID（关联role表）
     */
    @NotNull(message = "角色不能为空")
    private Integer roleId;

    /**
     * 所属团队ID（关联team表）
     */
    private Integer teamId;
}
