package com.api.model.wechat;

import java.io.Serializable;

/**
 * 身份验证结果实体
 * @author luochaofang
 */
public class AuthResult implements Serializable {

    private static final long serialVersionUID = 1483849209037994531L;

    /**
     * 接口返回码
     */
    private String code;
    /**
     * 返回信息
     */
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static AuthResult error(String code, String msg) {
        AuthResult r = new AuthResult();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static AuthResult ok(String code, String msg) {
        AuthResult r = new AuthResult();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
