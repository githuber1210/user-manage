package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 角色实体类
 * 支持三种角色：管理员(ADMIN)、老师(TEACHER)、学生(STUDENT)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Long id;
    
    /**
     * 角色名称
     */
    private String roleName;
    
    /**
     * 角色编码（ADMIN/TEACHER/STUDENT）
     */
    private String roleCode;
    
    /**
     * 角色描述
     */
    private String description;
    
    /**
     * 权限标识（逗号分隔）
     */
    private String permissions;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}