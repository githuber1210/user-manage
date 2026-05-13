package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 班级数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {

    private Long id;

    @NotBlank(message = "班级名称不能为空")
    private String className;

    @NotNull(message = "所属专业不能为空")
    private Long majorId;

    @NotBlank(message = "年级不能为空")
    private String grade;

    private Long teacherId;

    private Boolean enabled = true;
}