package com.example.demo.service.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException("用户不存在，ID: " + id);
        }
        return user;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        if (userMapper.findByUsername(userDTO.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }

        if (userMapper.findByEmail(userDTO.getEmail()) != null) {
            throw new BusinessException("邮箱已被使用");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userMapper.insert(user);
        return user;
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException("用户不存在，ID: " + id);
        }

        User existingUser = userMapper.findByUsername(userDTO.getUsername());
        if (existingUser != null && !existingUser.getId().equals(id)) {
            throw new BusinessException("用户名已被使用");
        }

        existingUser = userMapper.findByEmail(userDTO.getEmail());
        if (existingUser != null && !existingUser.getId().equals(id)) {
            throw new BusinessException("邮箱已被使用");
        }

        BeanUtils.copyProperties(userDTO, user);
        user.setId(id);
        userMapper.update(user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        if (userMapper.countById(id) == 0) {
            throw new BusinessException("用户不存在，ID: " + id);
        }
        userMapper.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userMapper.countByUsername(username) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userMapper.countByEmail(email) > 0;
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            userMapper.insert(user);
        } else {
            userMapper.update(user);
        }
        return user;
    }
}