package com.example.demo.service.impl;

import com.example.demo.dto.MajorDTO;
import com.example.demo.entity.Major;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.ClassInfoMapper;
import com.example.demo.mapper.MajorMapper;
import com.example.demo.service.MajorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MajorServiceImpl implements MajorService {

    private final MajorMapper majorMapper;
    private final ClassInfoMapper classInfoMapper;

    @Autowired
    public MajorServiceImpl(MajorMapper majorMapper, ClassInfoMapper classInfoMapper) {
        this.majorMapper = majorMapper;
        this.classInfoMapper = classInfoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Major> getAllMajors() {
        return majorMapper.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Major getMajorById(Long id) {
        Major major = majorMapper.findById(id);
        if (major == null) {
            throw new BusinessException("专业不存在，ID: " + id);
        }
        return major;
    }

    @Override
    public Major createMajor(MajorDTO majorDTO) {
        if (majorMapper.findByMajorCode(majorDTO.getMajorCode()) != null) {
            throw new BusinessException("专业代码已存在: " + majorDTO.getMajorCode());
        }
        if (majorMapper.findByMajorName(majorDTO.getMajorName()) != null) {
            throw new BusinessException("专业名称已存在: " + majorDTO.getMajorName());
        }

        Major major = new Major();
        BeanUtils.copyProperties(majorDTO, major);
        majorMapper.insert(major);
        return major;
    }

    @Override
    public Major updateMajor(Long id, MajorDTO majorDTO) {
        Major major = majorMapper.findById(id);
        if (major == null) {
            throw new BusinessException("专业不存在，ID: " + id);
        }

        Major existingMajor = majorMapper.findByMajorCode(majorDTO.getMajorCode());
        if (existingMajor != null && !existingMajor.getId().equals(id)) {
            throw new BusinessException("专业代码已被使用: " + majorDTO.getMajorCode());
        }

        existingMajor = majorMapper.findByMajorName(majorDTO.getMajorName());
        if (existingMajor != null && !existingMajor.getId().equals(id)) {
            throw new BusinessException("专业名称已被使用: " + majorDTO.getMajorName());
        }

        BeanUtils.copyProperties(majorDTO, major);
        major.setId(id);
        majorMapper.update(major);
        return major;
    }

    @Override
    public void deleteMajor(Long id) {
        if (majorMapper.countById(id) == 0) {
            throw new BusinessException("专业不存在，ID: " + id);
        }

        if (classInfoMapper.countByMajorId(id) > 0) {
            throw new BusinessException("该专业下存在班级，无法删除");
        }

        majorMapper.deleteById(id);
    }
}