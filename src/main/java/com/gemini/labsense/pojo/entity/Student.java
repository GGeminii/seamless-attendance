package com.gemini.labsense.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学生详细信息表实体类
 * 扩展学生详细信息
 */
@Data
@TableName("tb_student")
public class Student {
    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 关联用户表ID（学生角色）
     */
    private Long userId;

    /**
     * 学号（唯一）
     */
    private String studentNumber;

    /**
     * 年级（如2023）
     */
    private Integer grade;

    /**
     * 学位，1、本科，2、研究生，3、博士
     */
    private Integer degree;

    /**
     * 状态（0：无需考勤；1：在读考勤）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}