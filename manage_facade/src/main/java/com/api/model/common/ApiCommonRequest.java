package com.api.model.common;

import java.io.Serializable;

/**
 * 公共请求参数
 * @author 陈清玉
 */
public abstract class ApiCommonRequest  implements Serializable {

    private static final long serialVersionUID = -3404871325497969822L;
    /**
     * 身份证号，不能为空
     */
    private String idNo;

    /**
     * 姓名，不能为空
     */
    private String name;

    /**
     * 手机号，不能为空
     */
    private String phone;

    /**
     * 银行卡号
     */
    private String bankNo;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * qq号码
     */
    private String qq;


    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String toString() {
        return "ApiCommonRequest{" +
                "idNo='" + idNo + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", bankNo='" + bankNo + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                '}';
    }
}
