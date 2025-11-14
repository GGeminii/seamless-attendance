package com.gemini.labsense.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息表实体类
 * 存储所有用户基础信息
 */
@TableName("tb_user")
@Data
public class User {
    /**
     * 用户ID
     */
    @TableId
    private Long id;

    /**
     * 用户名（学号/工号）
     */
    private String username;

    /**
     * 加密密码（bcrypt哈希）
     */
    private String password;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 角色ID（关联role表）
     */
    private Integer roleId;

    /**
     * 所属团队ID（关联team表）
     */
    private Integer teamId;

    /**
     * 微信绑定标识
     */
    private String wechatOpenid;

    /**
     * 状态（0：待审核；1：正常；2：禁用）
     */
    private Integer status;

    /**
     * 审核人ID（关联user表）
     */
    private Long reviewId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    List<String> userPermsList;

    public List<SimpleGrantedAuthority> toGrantedAuthorities() {
        return this.getUserPermsList()
                .stream()
                .filter(code -> StringUtils.hasText(code.trim()))
                .map(code -> new SimpleGrantedAuthority(code.trim()))
                .toList();
    }
}