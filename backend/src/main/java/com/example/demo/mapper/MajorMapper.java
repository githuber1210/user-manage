package com.example.demo.mapper;

import com.example.demo.entity.Major;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 专业Mapper接口
 */
@Mapper
public interface MajorMapper {

    List<Major> findAll();

    Major findById(Long id);

    Major findByMajorCode(String majorCode);

    Major findByMajorName(String majorName);

    void insert(Major major);

    void update(Major major);

    void deleteById(Long id);

    int countById(Long id);

    int countByMajorCode(String majorCode);
}