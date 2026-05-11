package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;

import java.util.List;

/**
 * 用户服务层接口
 * 定义用户相关的业务操作
 */
public interface UserService {

    /**
     * 获取所有用户列表
     *
     * @return 用户列表
     */
    List<User> getAllUsers();

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    User getUserById(Long id);

    /**
     * 创建用户
     *
     * @param userDTO 用户数据
     * @return 创建后的用户对象
     */
    User createUser(UserDTO userDTO);

    /**
     * 更新用户信息
     *
     * @param id      用户ID
     * @param userDTO 更新的用户数据
     * @return 更新后的用户对象
     */
    User updateUser(Long id, UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByUsername(String username);
}