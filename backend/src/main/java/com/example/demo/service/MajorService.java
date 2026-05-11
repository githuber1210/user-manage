package com.example.demo.service;

import com.example.demo.dto.MajorDTO;
import com.example.demo.entity.Major;

import java.util.List;

/**
 * 专业服务接口
 */
public interface MajorService {

    List<Major> getAllMajors();

    Major getMajorById(Long id);

    Major createMajor(MajorDTO majorDTO);

    Major updateMajor(Long id, MajorDTO majorDTO);

    void deleteMajor(Long id);
}