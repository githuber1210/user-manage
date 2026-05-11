package com.example.demo.mapper;

import com.example.demo.entity.ClassInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 班级Mapper接口
 */
@Mapper
public interface ClassInfoMapper {

    List<ClassInfo> findAll();

    ClassInfo findById(Long id);

    ClassInfo findByClassName(String className);

    List<ClassInfo> findByMajorId(Long majorId);

    void insert(ClassInfo classInfo);

    void update(ClassInfo classInfo);

    void deleteById(Long id);

    int countById(Long id);

    int countByClassName(String className);

    int countByMajorId(Long majorId);

    void updateStudentCount(Long classId, Integer count);
}