package com.yzw.entity;

import java.io.Serializable;

/**
 * 结果工具类
 */
public class Result implements Serializable {
    // 成功或者失败
    private boolean success;
    // 提示消息
    private String message;
    // 总记录数
    private Long total;
    // 表示数据
    private Object data;

    public Result() {}

    /**
     * 用于增删改
     * @param success
     * @param message
     */
    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * 用于普通查询
     * @param success
     * @param message
     * @param data
     */
    public Result(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * 一般用于分页查询
     * @param success
     * @param message
     * @param total
     * @param data
     */
    public Result(boolean success, String message, Long total, Object data) {
        this.success = success;
        this.message = message;
        this.total = total;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

