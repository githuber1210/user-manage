-- 角色数据 (字段: id, role_name, role_code, description, permissions, enabled, created_at, updated_at)
INSERT IGNORE INTO role (id, role_name, role_code, description, permissions, enabled, created_at, updated_at) VALUES
(1, '管理员', 'ADMIN', '系统管理员，拥有最高权限', 'user:manage,role:manage,major:manage,class:manage,teacher:manage,student:manage', 1, NOW(), NOW()),
(2, '教师', 'TEACHER', '任课教师', 'student:view,class:view,major:view', 1, NOW(), NOW()),
(3, '学生', 'STUDENT', '在校学生', 'student:self,class:view', 1, NOW(), NOW());

-- 专业数据 (字段: id, major_name, major_code, college, training_program, duration, enabled, created_at, updated_at)
INSERT INTO major (id, major_name, major_code, college, training_program, duration, enabled, created_at, updated_at) VALUES
(1, '计算机科学与技术', 'CS', '信息学院', '培养具备计算机科学与技术基础理论、专业知识和实践能力的高级专门人才', 4, 1, NOW(), NOW()),
(2, '软件工程', 'SE', '信息学院', '培养掌握软件工程基本理论和专业知识，具备软件开发能力的工程技术人才', 4, 1, NOW(), NOW()),
(3, '网络工程', 'NE', '信息学院', '培养掌握网络技术、网络安全等方面知识的高级技术人才', 4, 1, NOW(), NOW()),
(4, '数据科学与大数据技术', 'DS', '信息学院', '培养掌握数据科学理论和大数据技术的复合型人才', 4, 1, NOW(), NOW()),
(5, '电子信息工程', 'EE', '电子工程学院', '培养掌握电子信息领域基本理论和技术的高级工程技术人才', 4, 1, NOW(), NOW());

-- 教师数据 (字段: id, teacher_no, name, gender, title, major_id, major_name, college, phone, email, enabled, created_at, updated_at)
INSERT INTO teacher (id, teacher_no, name, gender, title, major_id, major_name, college, phone, email, enabled, created_at, updated_at) VALUES
(1, 'T001', '张明', '男', '教授', 1, '计算机科学与技术', '信息学院', '13800138001', 'zhangming@example.com', 1, NOW(), NOW()),
(2, 'T002', '李华', '女', '副教授', 1, '计算机科学与技术', '信息学院', '13800138002', 'lihua@example.com', 1, NOW(), NOW()),
(3, 'T003', '王强', '男', '讲师', 2, '软件工程', '信息学院', '13800138003', 'wangqiang@example.com', 1, NOW(), NOW()),
(4, 'T004', '陈芳', '女', '副教授', 3, '网络工程', '信息学院', '13800138004', 'chenfang@example.com', 1, NOW(), NOW()),
(5, 'T005', '刘伟', '男', '教授', 4, '数据科学与大数据技术', '信息学院', '13800138005', 'liuwei@example.com', 1, NOW(), NOW());

-- 班级数据 (字段: id, class_name, major_id, major_name, grade, teacher_id, teacher_name, student_count, enabled, created_at, updated_at)
INSERT INTO class_info (id, class_name, major_id, major_name, grade, teacher_id, teacher_name, student_count, enabled, created_at, updated_at) VALUES
(1, '2024级计算机1班', 1, '计算机科学与技术', '2024', 1, '张明', 35, 1, NOW(), NOW()),
(2, '2024级计算机2班', 1, '计算机科学与技术', '2024', 2, '李华', 32, 1, NOW(), NOW()),
(3, '2024级软件工程1班', 2, '软件工程', '2024', 3, '王强', 30, 1, NOW(), NOW()),
(4, '2023级计算机1班', 1, '计算机科学与技术', '2023', 1, '张明', 34, 1, NOW(), NOW()),
(5, '2024级网络工程1班', 3, '网络工程', '2024', 4, '陈芳', 28, 1, NOW(), NOW()),
(6, '2024级数据科学1班', 4, '数据科学与大数据技术', '2024', 5, '刘伟', 31, 1, NOW(), NOW());

-- 学生数据 (字段: id, student_no, name, gender, birth_date, class_id, class_name, major_id, major_name, grade, phone, email, status, created_at, updated_at)
INSERT INTO student (id, student_no, name, gender, birth_date, class_id, class_name, major_id, major_name, grade, phone, email, status, created_at, updated_at) VALUES
(1, '2024001', '张三', '男', '2005-03-15', 1, '2024级计算机1班', 1, '计算机科学与技术', '2024', '13900139001', 'zhangsan@example.com', '在读', NOW(), NOW()),
(2, '2024002', '李四', '女', '2005-06-20', 1, '2024级计算机1班', 1, '计算机科学与技术', '2024', '13900139002', 'lisi@example.com', '在读', NOW(), NOW()),
(3, '2024003', '王五', '男', '2005-09-10', 1, '2024级计算机1班', 1, '计算机科学与技术', '2024', '13900139003', 'wangwu@example.com', '在读', NOW(), NOW()),
(4, '2024004', '赵六', '女', '2005-12-05', 2, '2024级计算机2班', 1, '计算机科学与技术', '2024', '13900139004', 'zhaoliu@example.com', '在读', NOW(), NOW()),
(5, '2024005', '孙七', '男', '2006-01-20', 2, '2024级计算机2班', 1, '计算机科学与技术', '2024', '13900139005', 'sunqi@example.com', '在读', NOW(), NOW()),
(6, '2024006', '周八', '女', '2005-04-18', 3, '2024级软件工程1班', 2, '软件工程', '2024', '13900139006', 'zhouba@example.com', '在读', NOW(), NOW()),
(7, '2024007', '吴九', '男', '2005-07-25', 3, '2024级软件工程1班', 2, '软件工程', '2024', '13900139007', 'wujiu@example.com', '在读', NOW(), NOW()),
(8, '2024008', '郑十', '女', '2005-10-08', 4, '2023级计算机1班', 1, '计算机科学与技术', '2023', '13900139008', 'zhengshi@example.com', '在读', NOW(), NOW()),
(9, '2024009', '钱十一', '男', '2004-11-30', 4, '2023级计算机1班', 1, '计算机科学与技术', '2023', '13900139009', 'qianshiyi@example.com', '在读', NOW(), NOW()),
(10, '2024010', '刘十二', '女', '2005-02-14', 5, '2024级网络工程1班', 3, '网络工程', '2024', '13900139010', 'liushier@example.com', '在读', NOW(), NOW()),
(11, '2024011', '陈十三', '男', '2005-05-12', 6, '2024级数据科学1班', 4, '数据科学与大数据技术', '2024', '13900139011', 'chenshisan@example.com', '在读', NOW(), NOW()),
(12, '2024012', '杨十四', '女', '2005-08-08', 1, '2024级计算机1班', 1, '计算机科学与技术', '2024', '13900139012', 'yangshisi@example.com', '在读', NOW(), NOW()),
(13, '2024013', '黄十五', '男', '2005-11-22', 1, '2024级计算机1班', 1, '计算机科学与技术', '2024', '13900139013', 'huangshiwu@example.com', '休学', NOW(), NOW()),
(14, '2024014', '林十六', '女', '2005-01-05', 2, '2024级计算机2班', 1, '计算机科学与技术', '2024', '13900139014', 'linshiliu@example.com', '在读', NOW(), NOW()),
(15, '2024015', '何十七', '男', '2005-04-30', 3, '2024级软件工程1班', 2, '软件工程', '2024', '13900139015', 'heshiyi@example.com', '在读', NOW(), NOW());

-- 用户数据 (字段: id, username, password, nickname, email, phone, role_id, status, age, created_at, updated_at)
-- 密码均为: password (BCrypt加密)
INSERT INTO user (id, username, password, nickname, email, phone, role_id, status, age, created_at, updated_at) VALUES
(1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '管理员', 'admin@example.com', '13800138000', 1, 'ACTIVE', 35, NOW(), NOW()),
(2, 'zhangming', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '张明', 'zhangming@example.com', '13800138001', 2, 'ACTIVE', 45, NOW(), NOW()),
(3, 'lihua', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '李华', 'lihua@example.com', '13800138002', 2, 'ACTIVE', 38, NOW(), NOW()),
(4, 'zhangsan', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '张三', 'zhangsan@example.com', '13900139001', 3, 'ACTIVE', 20, NOW(), NOW()),
(5, 'lisi', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '李四', 'lisi@example.com', '13900139002', 3, 'ACTIVE', 19, NOW(), NOW());