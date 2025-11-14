package com.gemini.labsense.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统配置表实体类
 * 存储系统相关配置信息
 */
@Data
@TableName("tb_system_config")
public class SystemConfig {
    /**
     * 自增ID
     */
    @TableId
    private Long id;

    /**
     * 配置键（如密码正则）
     */
    private String configKey;

    /**
     * 配置值（如8位以上含字母、数字、特殊字符）
     */
    private String configValue;

    /**
     * 配置说明
     */
    private String description;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
