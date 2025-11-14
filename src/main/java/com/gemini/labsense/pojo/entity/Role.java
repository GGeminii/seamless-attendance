package com.gemini.labsense.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统角色表实体类
 * 定义系统角色
 */
@Data
@TableName("tb_role")
public class Role {
    /**
     * 角色ID
     */

    @TableId
    private Integer id;

    /**
     * 角色名称（超级管理员/教师/学生管理员/一般学生）
     */
    private String roleName;

    /**
     * 角色描述（如"负责审批所有团队加入申请"）
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}