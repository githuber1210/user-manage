package com.example.demo.service;

import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Student;

import java.util.List;

/**
 * 学生服务接口
 */
public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    List<Student> getStudentsByClassId(Long classId);

    List<Student> getStudentsByMajorId(Long majorId);

    Student createStudent(StudentDTO studentDTO);

    Student updateStudent(Long id, StudentDTO studentDTO);

    void deleteStudent(Long id);
}