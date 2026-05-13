package com.example.demo.controller;

import com.example.demo.dto.ResultDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生管理控制器
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<ResultDTO<List<Student>>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(ResultDTO.success("查询成功", students));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO<Student>> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(ResultDTO.success("查询成功", student));
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<ResultDTO<List<Student>>> getStudentsByClassId(@PathVariable Long classId) {
        List<Student> students = studentService.getStudentsByClassId(classId);
        return ResponseEntity.ok(ResultDTO.success("查询成功", students));
    }

    @GetMapping("/major/{majorId}")
    public ResponseEntity<ResultDTO<List<Student>>> getStudentsByMajorId(@PathVariable Long majorId) {
        List<Student> students = studentService.getStudentsByMajorId(majorId);
        return ResponseEntity.ok(ResultDTO.success("查询成功", students));
    }

    @PostMapping
    public ResponseEntity<ResultDTO<Student>> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        Student student = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultDTO.success("创建成功", student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO<Student>> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO studentDTO) {
        Student student = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(ResultDTO.success("更新成功", student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ResultDTO.success("删除成功"));
    }
}