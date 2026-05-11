package com.example.demo.service;

import com.example.demo.dto.TeacherDTO;
import com.example.demo.entity.Teacher;

import java.util.List;

/**
 * 教师服务接口
 */
public interface TeacherService {

    List<Teacher> getAllTeachers();

    Teacher getTeacherById(Long id);

    List<Teacher> getTeachersByMajorId(Long majorId);

    Teacher createTeacher(TeacherDTO teacherDTO);

    Teacher updateTeacher(Long id, TeacherDTO teacherDTO);

    void deleteTeacher(Long id);
}