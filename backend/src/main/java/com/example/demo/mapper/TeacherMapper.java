package com.example.demo.mapper;

import com.example.demo.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 教师Mapper接口
 */
@Mapper
public interface TeacherMapper {

    List<Teacher> findAll();

    Teacher findById(Long id);

    Teacher findByTeacherNo(String teacherNo);

    List<Teacher> findByMajorId(Long majorId);

    void insert(Teacher teacher);

    void update(Teacher teacher);

    void deleteById(Long id);

    int countById(Long id);

    int countByTeacherNo(String teacherNo);
}