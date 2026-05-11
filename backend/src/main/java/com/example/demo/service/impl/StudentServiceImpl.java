package com.example.demo.service.impl;

import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.ClassInfo;
import com.example.demo.entity.Major;
import com.example.demo.entity.Student;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.ClassInfoMapper;
import com.example.demo.mapper.MajorMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final ClassInfoMapper classInfoMapper;
    private final MajorMapper majorMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper, ClassInfoMapper classInfoMapper,
                             MajorMapper majorMapper) {
        this.studentMapper = studentMapper;
        this.classInfoMapper = classInfoMapper;
        this.majorMapper = majorMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentMapper.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        Student student = studentMapper.findById(id);
        if (student == null) {
            throw new BusinessException("学生不存在，ID: " + id);
        }
        return student;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByClassId(Long classId) {
        return studentMapper.findByClassId(classId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByMajorId(Long majorId) {
        return studentMapper.findByMajorId(majorId);
    }

    @Override
    public Student createStudent(StudentDTO studentDTO) {
        ClassInfo classInfo = classInfoMapper.findById(studentDTO.getClassId());
        if (classInfo == null) {
            throw new BusinessException("班级不存在，ID: " + studentDTO.getClassId());
        }

        if (studentMapper.findByStudentNo(studentDTO.getStudentNo()) != null) {
            throw new BusinessException("学号已存在: " + studentDTO.getStudentNo());
        }

        Major major = majorMapper.findById(classInfo.getMajorId());
        if (major == null) {
            throw new BusinessException("专业不存在，ID: " + classInfo.getMajorId());
        }

        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        student.setClassName(classInfo.getClassName());
        student.setMajorId(classInfo.getMajorId());
        student.setMajorName(major.getMajorName());
        student.setGrade(classInfo.getGrade());
        studentMapper.insert(student);

        classInfoMapper.updateStudentCount(classInfo.getId(), classInfo.getStudentCount() + 1);
        return student;
    }

    @Override
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentMapper.findById(id);
        if (student == null) {
            throw new BusinessException("学生不存在，ID: " + id);
        }

        Long oldClassId = student.getClassId();
        ClassInfo classInfo = classInfoMapper.findById(studentDTO.getClassId());
        if (classInfo == null) {
            throw new BusinessException("班级不存在，ID: " + studentDTO.getClassId());
        }

        Student existingStudent = studentMapper.findByStudentNo(studentDTO.getStudentNo());
        if (existingStudent != null && !existingStudent.getId().equals(id)) {
            throw new BusinessException("学号已被使用: " + studentDTO.getStudentNo());
        }

        Major major = majorMapper.findById(classInfo.getMajorId());
        if (major == null) {
            throw new BusinessException("专业不存在，ID: " + classInfo.getMajorId());
        }

        BeanUtils.copyProperties(studentDTO, student);
        student.setId(id);
        student.setClassName(classInfo.getClassName());
        student.setMajorId(classInfo.getMajorId());
        student.setMajorName(major.getMajorName());
        student.setGrade(classInfo.getGrade());
        studentMapper.update(student);

        if (!oldClassId.equals(classInfo.getId())) {
            classInfoMapper.updateStudentCount(oldClassId, 
                classInfoMapper.findById(oldClassId).getStudentCount() - 1);
            classInfoMapper.updateStudentCount(classInfo.getId(), classInfo.getStudentCount() + 1);
        }

        return student;
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentMapper.findById(id);
        if (student == null) {
            throw new BusinessException("学生不存在，ID: " + id);
        }

        studentMapper.deleteById(id);
        
        ClassInfo classInfo = classInfoMapper.findById(student.getClassId());
        if (classInfo != null) {
            classInfoMapper.updateStudentCount(classInfo.getId(), classInfo.getStudentCount() - 1);
        }
    }
}