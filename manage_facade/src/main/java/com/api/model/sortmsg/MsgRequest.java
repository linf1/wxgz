package com.api.model.sortmsg;

import java.io.Serializable;

/**
 * 短信服务请求实体
 * @author 陈清玉
 */
public class MsgRequest implements Serializable {

    private static final long serialVersionUID = 5502226723331101223L;
    /**
     * 手机号码
     */
    private String  phone;
    /**
     * 短信内容（模板编号和短信内容不能都为空）
     */
    private String  content;

    private String smsCode;

    private int overTime;
    /**
     * 时间戳
     */
    private String t;
    /**
     * 类型 0 ： 手机短信验证码，1：图片验证码
     */
    private String type;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public int getOverTime() {
        return overTime;
    }

    public void setOverTime(int overTime) {
        this.overTime = overTime;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MsgRequest{" +
                "phone='" + phone + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
