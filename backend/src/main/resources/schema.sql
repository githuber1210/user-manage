CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码(BCrypt加密)',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `email` VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `role_id` BIGINT DEFAULT 3 COMMENT '角色ID',
    `status` VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '状态(ACTIVE/DISABLED)',
    `age` INT DEFAULT NULL COMMENT '年龄',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_username` (`username`),
    INDEX `idx_email` (`email`),
    INDEX `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

CREATE TABLE IF NOT EXISTS `role` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
    `role_name` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
    `role_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
    `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
    `permissions` TEXT DEFAULT NULL COMMENT '权限标识(逗号分隔)',
    `enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

CREATE TABLE IF NOT EXISTS `major` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '专业ID',
    `major_name` VARCHAR(100) NOT NULL UNIQUE COMMENT '专业名称',
    `major_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '专业代码',
    `college` VARCHAR(100) DEFAULT NULL COMMENT '所属学院',
    `training_program` TEXT DEFAULT NULL COMMENT '培养方案',
    `duration` INT DEFAULT 4 COMMENT '学制(年)',
    `enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_major_code` (`major_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='专业表';

CREATE TABLE IF NOT EXISTS `class_info` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '班级ID',
    `class_name` VARCHAR(100) NOT NULL UNIQUE COMMENT '班级名称',
    `major_id` BIGINT NOT NULL COMMENT '所属专业ID',
    `major_name` VARCHAR(100) NOT NULL COMMENT '专业名称(冗余)',
    `grade` VARCHAR(20) NOT NULL COMMENT '年级',
    `teacher_id` BIGINT DEFAULT NULL COMMENT '班主任ID',
    `teacher_name` VARCHAR(50) DEFAULT NULL COMMENT '班主任姓名(冗余)',
    `student_count` INT NOT NULL DEFAULT 0 COMMENT '班级人数',
    `enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_major_id` (`major_id`),
    INDEX `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='班级表';

CREATE TABLE IF NOT EXISTS `teacher` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '教师ID',
    `teacher_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '工号',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `gender` VARCHAR(10) DEFAULT NULL COMMENT '性别',
    `title` VARCHAR(50) DEFAULT NULL COMMENT '职称',
    `major_id` BIGINT DEFAULT NULL COMMENT '所属专业ID',
    `major_name` VARCHAR(100) DEFAULT NULL COMMENT '专业名称(冗余)',
    `college` VARCHAR(100) DEFAULT NULL COMMENT '所属学院',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_teacher_no` (`teacher_no`),
    INDEX `idx_major_id` (`major_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='教师表';

CREATE TABLE IF NOT EXISTS `student` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '学生ID',
    `student_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '学号',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `gender` VARCHAR(10) DEFAULT NULL COMMENT '性别',
    `birth_date` DATE DEFAULT NULL COMMENT '出生日期',
    `class_id` BIGINT NOT NULL COMMENT '班级ID',
    `class_name` VARCHAR(100) NOT NULL COMMENT '班级名称(冗余)',
    `major_id` BIGINT NOT NULL COMMENT '专业ID',
    `major_name` VARCHAR(100) NOT NULL COMMENT '专业名称(冗余)',
    `grade` VARCHAR(20) NOT NULL COMMENT '年级',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `status` VARCHAR(20) NOT NULL DEFAULT '在读' COMMENT '状态(在读/休学/毕业)',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_student_no` (`student_no`),
    INDEX `idx_class_id` (`class_id`),
    INDEX `idx_major_id` (`major_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生表';