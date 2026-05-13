package com.example.demo.controller;

import com.example.demo.dto.MajorDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.entity.Major;
import com.example.demo.service.MajorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 专业管理控制器
 */
@RestController
@RequestMapping("/api/majors")
public class MajorController {

    private final MajorService majorService;

    @Autowired
    public MajorController(MajorService majorService) {
        this.majorService = majorService;
    }

    @GetMapping
    public ResponseEntity<ResultDTO<List<Major>>> getAllMajors() {
        List<Major> majors = majorService.getAllMajors();
        return ResponseEntity.ok(ResultDTO.success("查询成功", majors));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO<Major>> getMajorById(@PathVariable Long id) {
        Major major = majorService.getMajorById(id);
        return ResponseEntity.ok(ResultDTO.success("查询成功", major));
    }

    @PostMapping
    public ResponseEntity<ResultDTO<Major>> createMajor(@Valid @RequestBody MajorDTO majorDTO) {
        Major major = majorService.createMajor(majorDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultDTO.success("创建成功", major));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO<Major>> updateMajor(
            @PathVariable Long id,
            @Valid @RequestBody MajorDTO majorDTO) {
        Major major = majorService.updateMajor(id, majorDTO);
        return ResponseEntity.ok(ResultDTO.success("更新成功", major));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO<Void>> deleteMajor(@PathVariable Long id) {
        majorService.deleteMajor(id);
        return ResponseEntity.ok(ResultDTO.success("删除成功"));
    }
}