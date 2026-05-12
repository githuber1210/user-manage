package com.example.demo.controller;

import com.example.demo.config.JwtUtil;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.expiration:86400000}")
    private Long expiration;

    @PostMapping("/login")
    public ResponseEntity<ResultDTO<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        User user = userService.findByUsername(loginDTO.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResultDTO.error(401, "用户名或密码错误"));
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResultDTO.error(401, "用户名或密码错误"));
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ResultDTO.error(403, "用户已被禁用"));
        }

        Role role = roleService.getRoleById(user.getRoleId());
        String roleCode = role != null ? role.getRoleCode() : "USER";

        String token = jwtUtil.generateToken(user.getUsername(), user.getId(), user.getRoleId(), roleCode);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(token);
        tokenDTO.setExpiresIn(expiration / 1000);
        tokenDTO.setUserId(user.getId());
        tokenDTO.setUsername(user.getUsername());
        tokenDTO.setNickname(user.getNickname());
        tokenDTO.setRoleId(user.getRoleId());
        tokenDTO.setRoleCode(roleCode);

        return ResponseEntity.ok(ResultDTO.success("登录成功", tokenDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<ResultDTO<Void>> register(@Valid @RequestBody RegisterDTO registerDTO) {
        if (userService.existsByUsername(registerDTO.getUsername())) {
            return ResponseEntity.badRequest().body(ResultDTO.error(400, "用户名已存在"));
        }

        if (userService.existsByEmail(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body(ResultDTO.error(400, "邮箱已被注册"));
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setNickname(registerDTO.getNickname());
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setRoleId(registerDTO.getRoleId() != null ? registerDTO.getRoleId() : 3L);
        user.setStatus("ACTIVE");

        userService.save(user);

        return ResponseEntity.ok(ResultDTO.success("注册成功"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResultDTO<Void>> logout() {
        return ResponseEntity.ok(ResultDTO.success("退出成功"));
    }

    @GetMapping("/me")
    public ResponseEntity<ResultDTO<Object>> getCurrentUser(@RequestAttribute(value = "currentUser", required = false) User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResultDTO.error(401, "未登录"));
        }

        return ResponseEntity.ok(ResultDTO.success("查询成功", user));
    }
}