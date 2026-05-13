package com.example.demo.controller;

import com.example.demo.dto.ClassDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.entity.ClassInfo;
import com.example.demo.service.ClassInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级管理控制器
 */
@RestController
@RequestMapping("/api/classes")
public class ClassInfoController {

    private final ClassInfoService classInfoService;

    @Autowired
    public ClassInfoController(ClassInfoService classInfoService) {
        this.classInfoService = classInfoService;
    }

    @GetMapping
    public ResponseEntity<ResultDTO<List<ClassInfo>>> getAllClasses() {
        List<ClassInfo> classes = classInfoService.getAllClasses();
        return ResponseEntity.ok(ResultDTO.success("查询成功", classes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO<ClassInfo>> getClassById(@PathVariable Long id) {
        ClassInfo classInfo = classInfoService.getClassById(id);
        return ResponseEntity.ok(ResultDTO.success("查询成功", classInfo));
    }

    @GetMapping("/major/{majorId}")
    public ResponseEntity<ResultDTO<List<ClassInfo>>> getClassesByMajorId(@PathVariable Long majorId) {
        List<ClassInfo> classes = classInfoService.getClassesByMajorId(majorId);
        return ResponseEntity.ok(ResultDTO.success("查询成功", classes));
    }

    @PostMapping
    public ResponseEntity<ResultDTO<ClassInfo>> createClass(@Valid @RequestBody ClassDTO classDTO) {
        ClassInfo classInfo = classInfoService.createClass(classDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultDTO.success("创建成功", classInfo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO<ClassInfo>> updateClass(
            @PathVariable Long id,
            @Valid @RequestBody ClassDTO classDTO) {
        ClassInfo classInfo = classInfoService.updateClass(id, classDTO);
        return ResponseEntity.ok(ResultDTO.success("更新成功", classInfo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO<Void>> deleteClass(@PathVariable Long id) {
        classInfoService.deleteClass(id);
        return ResponseEntity.ok(ResultDTO.success("删除成功"));
    }
}