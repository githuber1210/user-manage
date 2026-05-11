package com.example.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 专业数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MajorDTO {

    private Long id;

    @NotBlank(message = "专业名称不能为空")
    private String majorName;

    @NotBlank(message = "专业代码不能为空")
    private String majorCode;

    private String college;

    private String trainingProgram;

    @Positive(message = "学制必须为正整数")
    private Integer duration;

    private Boolean enabled = true;
}