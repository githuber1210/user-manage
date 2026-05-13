package com.example.demo.controller;

import com.example.demo.dto.ResultDTO;
import com.example.demo.dto.RoleDTO;
import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ResultDTO<List<Role>>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(ResultDTO.success("查询成功", roles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO<Role>> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.ok(ResultDTO.success("查询成功", role));
    }

    @GetMapping("/code/{roleCode}")
    public ResponseEntity<ResultDTO<Role>> getRoleByCode(@PathVariable String roleCode) {
        Role role = roleService.getRoleByCode(roleCode);
        return ResponseEntity.ok(ResultDTO.success("查询成功", role));
    }

    @PostMapping
    public ResponseEntity<ResultDTO<Role>> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        Role role = roleService.createRole(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultDTO.success("创建成功", role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO<Role>> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleDTO roleDTO) {
        Role role = roleService.updateRole(id, roleDTO);
        return ResponseEntity.ok(ResultDTO.success("更新成功", role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO<Void>> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(ResultDTO.success("删除成功"));
    }
}