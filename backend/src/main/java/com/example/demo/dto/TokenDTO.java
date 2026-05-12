package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    private String accessToken;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private Long userId;
    private String username;
    private String nickname;
    private Long roleId;
    private String roleCode;
}