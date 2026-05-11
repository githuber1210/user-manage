package com.example.demo.service.impl;

import com.example.demo.dto.RoleDTO;
import com.example.demo.entity.Role;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleMapper.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleById(Long id) {
        Role role = roleMapper.findById(id);
        if (role == null) {
            throw new BusinessException("角色不存在，ID: " + id);
        }
        return role;
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByCode(String roleCode) {
        return roleMapper.findByRoleCode(roleCode);
    }

    @Override
    public Role createRole(RoleDTO roleDTO) {
        if (roleMapper.findByRoleCode(roleDTO.getRoleCode()) != null) {
            throw new BusinessException("角色编码已存在: " + roleDTO.getRoleCode());
        }
        if (roleMapper.findByRoleName(roleDTO.getRoleName()) != null) {
            throw new BusinessException("角色名称已存在: " + roleDTO.getRoleName());
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        roleMapper.insert(role);
        return role;
    }

    @Override
    public Role updateRole(Long id, RoleDTO roleDTO) {
        Role role = roleMapper.findById(id);
        if (role == null) {
            throw new BusinessException("角色不存在，ID: " + id);
        }

        Role existingRole = roleMapper.findByRoleCode(roleDTO.getRoleCode());
        if (existingRole != null && !existingRole.getId().equals(id)) {
            throw new BusinessException("角色编码已被使用: " + roleDTO.getRoleCode());
        }

        existingRole = roleMapper.findByRoleName(roleDTO.getRoleName());
        if (existingRole != null && !existingRole.getId().equals(id)) {
            throw new BusinessException("角色名称已被使用: " + roleDTO.getRoleName());
        }

        BeanUtils.copyProperties(roleDTO, role);
        role.setId(id);
        roleMapper.update(role);
        return role;
    }

    @Override
    public void deleteRole(Long id) {
        if (roleMapper.countById(id) == 0) {
            throw new BusinessException("角色不存在，ID: " + id);
        }
        roleMapper.deleteById(id);
    }
}