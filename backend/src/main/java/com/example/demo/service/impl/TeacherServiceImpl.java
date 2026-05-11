package com.example.demo.service.impl;

import com.example.demo.dto.TeacherDTO;
import com.example.demo.entity.Major;
import com.example.demo.entity.Teacher;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.MajorMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;
    private final MajorMapper majorMapper;

    @Autowired
    public TeacherServiceImpl(TeacherMapper teacherMapper, MajorMapper majorMapper) {
        this.teacherMapper = teacherMapper;
        this.majorMapper = majorMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> getAllTeachers() {
        return teacherMapper.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Teacher getTeacherById(Long id) {
        Teacher teacher = teacherMapper.findById(id);
        if (teacher == null) {
            throw new BusinessException("教师不存在，ID: " + id);
        }
        return teacher;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> getTeachersByMajorId(Long majorId) {
        return teacherMapper.findByMajorId(majorId);
    }

    @Override
    public Teacher createTeacher(TeacherDTO teacherDTO) {
        if (teacherMapper.findByTeacherNo(teacherDTO.getTeacherNo()) != null) {
            throw new BusinessException("工号已存在: " + teacherDTO.getTeacherNo());
        }

        Major major = null;
        if (teacherDTO.getMajorId() != null) {
            major = majorMapper.findById(teacherDTO.getMajorId());
            if (major == null) {
                throw new BusinessException("专业不存在，ID: " + teacherDTO.getMajorId());
            }
        }

        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherDTO, teacher);
        teacher.setMajorName(major != null ? major.getMajorName() : null);
        teacherMapper.insert(teacher);
        return teacher;
    }

    @Override
    public Teacher updateTeacher(Long id, TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.findById(id);
        if (teacher == null) {
            throw new BusinessException("教师不存在，ID: " + id);
        }

        Teacher existingTeacher = teacherMapper.findByTeacherNo(teacherDTO.getTeacherNo());
        if (existingTeacher != null && !existingTeacher.getId().equals(id)) {
            throw new BusinessException("工号已被使用: " + teacherDTO.getTeacherNo());
        }

        Major major = null;
        if (teacherDTO.getMajorId() != null) {
            major = majorMapper.findById(teacherDTO.getMajorId());
            if (major == null) {
                throw new BusinessException("专业不存在，ID: " + teacherDTO.getMajorId());
            }
        }

        BeanUtils.copyProperties(teacherDTO, teacher);
        teacher.setId(id);
        teacher.setMajorName(major != null ? major.getMajorName() : null);
        teacherMapper.update(teacher);
        return teacher;
    }

    @Override
    public void deleteTeacher(Long id) {
        if (teacherMapper.countById(id) == 0) {
            throw new BusinessException("教师不存在，ID: " + id);
        }
        teacherMapper.deleteById(id);
    }
}