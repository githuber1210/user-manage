package com.example.demo.controller;

import com.example.demo.dto.ResultDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 * 提供用户相关的RESTful API接口
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取所有用户列表
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<ResultDTO<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(ResultDTO.success("查询成功", users));
    }

    /**
     * 根据ID获取用户信息
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(ResultDTO.success("查询成功", user));
    }

    /**
     * 创建新用户
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<ResultDTO<User>> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResultDTO.success("创建成功", user));
    }

    /**
     * 更新用户信息
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO<User>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        User user = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(ResultDTO.success("更新成功", user));
    }

    /**
     * 删除用户
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ResultDTO.success("删除成功"));
    }
}