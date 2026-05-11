package com.example.demo.controller;

import com.example.demo.dto.ResultDTO;
import com.example.demo.dto.TeacherDTO;
import com.example.demo.entity.Teacher;
import com.example.demo.service.TeacherService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师管理控制器
 */
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<ResultDTO<List<Teacher>>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(ResultDTO.success("查询成功", teachers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO<Teacher>> getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherService.getTeacherById(id);
        return ResponseEntity.ok(ResultDTO.success("查询成功", teacher));
    }

    @GetMapping("/major/{majorId}")
    public ResponseEntity<ResultDTO<List<Teacher>>> getTeachersByMajorId(@PathVariable Long majorId) {
        List<Teacher> teachers = teacherService.getTeachersByMajorId(majorId);
        return ResponseEntity.ok(ResultDTO.success("查询成功", teachers));
    }

    @PostMapping
    public ResponseEntity<ResultDTO<Teacher>> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO) {
        Teacher teacher = teacherService.createTeacher(teacherDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultDTO.success("创建成功", teacher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO<Teacher>> updateTeacher(
            @PathVariable Long id,
            @Valid @RequestBody TeacherDTO teacherDTO) {
        Teacher teacher = teacherService.updateTeacher(id, teacherDTO);
        return ResponseEntity.ok(ResultDTO.success("更新成功", teacher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO<Void>> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok(ResultDTO.success("删除成功"));
    }
}