package com.example.demo.service;

import com.example.demo.dto.ClassDTO;
import com.example.demo.entity.ClassInfo;

import java.util.List;

/**
 * 班级服务接口
 */
public interface ClassInfoService {

    List<ClassInfo> getAllClasses();

    ClassInfo getClassById(Long id);

    List<ClassInfo> getClassesByMajorId(Long majorId);

    ClassInfo createClass(ClassDTO classDTO);

    ClassInfo updateClass(Long id, ClassDTO classDTO);

    void deleteClass(Long id);
}