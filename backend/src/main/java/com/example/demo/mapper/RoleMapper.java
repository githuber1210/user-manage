package com.example.demo.mapper;

import com.example.demo.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色Mapper接口
 */
@Mapper
public interface RoleMapper {

    List<Role> findAll();

    Role findById(Long id);

    Role findByRoleCode(String roleCode);

    Role findByRoleName(String roleName);

    void insert(Role role);

    void update(Role role);

    void deleteById(Long id);

    int countById(Long id);

    int countByRoleCode(String roleCode);
}