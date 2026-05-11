package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 学生实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Long id;
    
    /**
     * 学号
     */
    private String studentNo;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 性别（男/女）
     */
    private String gender;
    
    /**
     * 出生日期
     */
    private String birthDate;
    
    /**
     * 班级ID
     */
    private Long classId;
    
    /**
     * 班级名称（用于显示）
     */
    private String className;
    
    /**
     * 专业ID
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
     * 联系电话
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 状态（在读/休学/毕业）
     */
    private String status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}