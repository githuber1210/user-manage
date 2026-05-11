package com.example.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;

    @NotBlank(message = "学号不能为空")
    private String studentNo;

    @NotBlank(message = "姓名不能为空")
    private String name;

    private String gender;

    private String birthDate;

    @NotNull(message = "班级不能为空")
    private Long classId;

    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String status = "在读";
}