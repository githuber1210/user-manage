package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 教师实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    private Long id;
    
    /**
     * 工号
     */
    private String teacherNo;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 性别（男/女）
     */
    private String gender;
    
    /**
     * 职称
     */
    private String title;
    
    /**
     * 所属专业ID
     */
    private Long majorId;
    
    /**
     * 专业名称（用于显示）
     */
    private String majorName;
    
    /**
     * 所属学院
     */
    private String college;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}