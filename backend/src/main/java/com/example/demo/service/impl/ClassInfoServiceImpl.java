package com.example.demo.service.impl;

import com.example.demo.dto.ClassDTO;
import com.example.demo.entity.ClassInfo;
import com.example.demo.entity.Major;
import com.example.demo.entity.Teacher;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.ClassInfoMapper;
import com.example.demo.mapper.MajorMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.service.ClassInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassInfoServiceImpl implements ClassInfoService {

    private final ClassInfoMapper classInfoMapper;
    private final MajorMapper majorMapper;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;

    @Autowired
    public ClassInfoServiceImpl(ClassInfoMapper classInfoMapper, MajorMapper majorMapper,
                               TeacherMapper teacherMapper, StudentMapper studentMapper) {
        this.classInfoMapper = classInfoMapper;
        this.majorMapper = majorMapper;
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassInfo> getAllClasses() {
        return classInfoMapper.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ClassInfo getClassById(Long id) {
        ClassInfo classInfo = classInfoMapper.findById(id);
        if (classInfo == null) {
            throw new BusinessException("班级不存在，ID: " + id);
        }
        return classInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassInfo> getClassesByMajorId(Long majorId) {
        return classInfoMapper.findByMajorId(majorId);
    }

    @Override
    public ClassInfo createClass(ClassDTO classDTO) {
        Major major = majorMapper.findById(classDTO.getMajorId());
        if (major == null) {
            throw new BusinessException("专业不存在，ID: " + classDTO.getMajorId());
        }

        if (classInfoMapper.findByClassName(classDTO.getClassName()) != null) {
            throw new BusinessException("班级名称已存在: " + classDTO.getClassName());
        }

        Teacher teacher = null;
        if (classDTO.getTeacherId() != null) {
            teacher = teacherMapper.findById(classDTO.getTeacherId());
            if (teacher == null) {
                throw new BusinessException("班主任不存在，ID: " + classDTO.getTeacherId());
            }
        }

        ClassInfo classInfo = new ClassInfo();
        BeanUtils.copyProperties(classDTO, classInfo);
        classInfo.setMajorName(major.getMajorName());
        classInfo.setTeacherName(teacher != null ? teacher.getName() : null);
        classInfo.setStudentCount(0);
        classInfoMapper.insert(classInfo);
        return classInfo;
    }

    @Override
    public ClassInfo updateClass(Long id, ClassDTO classDTO) {
        ClassInfo classInfo = classInfoMapper.findById(id);
        if (classInfo == null) {
            throw new BusinessException("班级不存在，ID: " + id);
        }

        Major major = majorMapper.findById(classDTO.getMajorId());
        if (major == null) {
            throw new BusinessException("专业不存在，ID: " + classDTO.getMajorId());
        }

        ClassInfo existingClass = classInfoMapper.findByClassName(classDTO.getClassName());
        if (existingClass != null && !existingClass.getId().equals(id)) {
            throw new BusinessException("班级名称已被使用: " + classDTO.getClassName());
        }

        Teacher teacher = null;
        if (classDTO.getTeacherId() != null) {
            teacher = teacherMapper.findById(classDTO.getTeacherId());
            if (teacher == null) {
                throw new BusinessException("班主任不存在，ID: " + classDTO.getTeacherId());
            }
        }

        BeanUtils.copyProperties(classDTO, classInfo);
        classInfo.setId(id);
        classInfo.setMajorName(major.getMajorName());
        classInfo.setTeacherName(teacher != null ? teacher.getName() : null);
        classInfoMapper.update(classInfo);
        return classInfo;
    }

    @Override
    public void deleteClass(Long id) {
        if (classInfoMapper.countById(id) == 0) {
            throw new BusinessException("班级不存在，ID: " + id);
        }

        if (studentMapper.countByClassId(id) > 0) {
            throw new BusinessException("该班级下存在学生，无法删除");
        }

        classInfoMapper.deleteById(id);
    }
}