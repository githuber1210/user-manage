package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 专业实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Major {

    private Long id;
    
    /**
     * 专业名称
     */
    private String majorName;
    
    /**
     * 专业代码
     */
    private String majorCode;
    
    /**
     * 所属学院
     */
    private String college;
    
    /**
     * 培养方案
     */
    private String trainingProgram;
    
    /**
     * 学制（年）
     */
    private Integer duration;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}