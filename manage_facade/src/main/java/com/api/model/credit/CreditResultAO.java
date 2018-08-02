package com.api.model.credit;

import java.io.Serializable;

/**
 *  征信验证结果AO
 * @author luochaofang
 */
public class CreditResultAO implements Serializable {
    private static final long serialVersionUID = -3721527294663526053L;

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 结果代码
     */
    private String code;

    /**
     * 结果原因描述
     */
    private String message;

    /**
     * token
     */
    private String token;

    /**
     * pbc_token
     */
    private String pbc_token;

    /**
     * 个人征信h5页面
     */
    private String creditH5Url;

    public String getPbc_token() {
        return pbc_token;
    }

    public void setPbc_token(String pbc_token) {
        this.pbc_token = pbc_token;
    }

    public String getCreditH5Url() {
        return creditH5Url;
    }

    public void setCreditH5Url(String creditH5Url) {
        this.creditH5Url = creditH5Url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
