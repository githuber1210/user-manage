package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 班级实体类
 * 避免与Java关键字class冲突，使用ClassInfo命名
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassInfo {

    private Long id;
    
    /**
     * 班级名称
     */
    private String className;
    
    /**
     * 所属专业ID
     */
    private Long majorId;
    
    /**
     * 专业名称（用于显示）
     */
    private String majorName;
    
    /**
     * 年级
     */
    private String grade;
    
    /**
     * 班主任ID
     */
    private Long teacherId;
    
    /**
     * 班主任姓名（用于显示）
     */
    private String teacherName;
    
    /**
     * 班级人数
     */
    private Integer studentCount;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}