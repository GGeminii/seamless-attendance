package com.gemini.labsense.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统权限表实体类
 * 定义系统中所有可操作的权限项
 */
@Data
@TableName("tb_permission")
public class Permission {
    /**
     * 权限ID
     */
    @TableId
    private Integer id;

    /**
     * 权限编码（唯一标识，如"approve_team"）
     */
    private String permCode;

    /**
     * 权限名称（如"审批团队加入"）
     */
    private String permName;

    /**
     * 权限描述
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