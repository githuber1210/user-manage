package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果封装类
 * 用于统一返回格式，便于前端处理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> {

    /**
     * 响应状态码
     * 200: 成功
     * 400: 请求参数错误
     * 404: 资源未找到
     * 500: 服务器内部错误
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 成功响应（无数据）
     */
    public static <T> ResultDTO<T> success() {
        return new ResultDTO<>(200, "操作成功", null);
    }

    /**
     * 成功响应（有数据）
     */
    public static <T> ResultDTO<T> success(T data) {
        return new ResultDTO<>(200, "操作成功", data);
    }

    /**
     * 成功响应（自定义消息，无数据）
     */
    public static <T> ResultDTO<T> success(String message) {
        return new ResultDTO<>(200, message, null);
    }

    /**
     * 成功响应（自定义消息和数据）
     */
    public static <T> ResultDTO<T> success(String message, T data) {
        return new ResultDTO<>(200, message, data);
    }

    /**
     * 失败响应
     */
    public static <T> ResultDTO<T> error(Integer code, String message) {
        return new ResultDTO<>(code, message, null);
    }

    /**
     * 参数错误响应
     */
    public static <T> ResultDTO<T> badRequest(String message) {
        return new ResultDTO<>(400, message, null);
    }

    /**
     * 资源未找到响应
     */
    public static <T> ResultDTO<T> notFound(String message) {
        return new ResultDTO<>(404, message, null);
    }

    /**
     * 服务器错误响应
     */
    public static <T> ResultDTO<T> serverError(String message) {
        return new ResultDTO<>(500, message, null);
    }
}