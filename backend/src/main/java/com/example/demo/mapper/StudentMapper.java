package com.example.demo.mapper;

import com.example.demo.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学生Mapper接口
 */
@Mapper
public interface StudentMapper {

    List<Student> findAll();

    Student findById(Long id);

    Student findByStudentNo(String studentNo);

    List<Student> findByClassId(Long classId);

    List<Student> findByMajorId(Long majorId);

    void insert(Student student);

    void update(Student student);

    void deleteById(Long id);

    int countById(Long id);

    int countByStudentNo(String studentNo);

    int countByClassId(Long classId);
}