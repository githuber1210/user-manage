package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教师数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {

    private Long id;

    @NotBlank(message = "工号不能为空")
    private String teacherNo;

    @NotBlank(message = "姓名不能为空")
    private String name;

    private String gender;

    private String title;

    private Long majorId;

    private String college;

    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;

    private Boolean enabled = true;
}