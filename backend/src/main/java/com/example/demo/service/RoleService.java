package com.example.demo.service;

import com.example.demo.dto.RoleDTO;
import com.example.demo.entity.Role;

import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService {

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    Role getRoleByCode(String roleCode);

    Role createRole(RoleDTO roleDTO);

    Role updateRole(Long id, RoleDTO roleDTO);

    void deleteRole(Long id);
}