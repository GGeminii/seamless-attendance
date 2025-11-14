package com.gemini.labsense.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_role_permission")
public class RolePermission {
    /**
     * ID
     */
    @TableId
    private Integer id;

    /**
     * 角色ID（关联role表）
     */
    private Integer roleId;

    /**
     * 权限ID（关联permission表）
     */
    private Integer permId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}

