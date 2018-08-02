package com.api.model.credit;



import java.io.Serializable;

/**
 * 征信请求类
 * @author luochaofang
 */
public class CreditRequest implements Serializable {

    /** 校验登录 */
    private String token;

    /** h5返回地址 */
    private String callbackUrl;

    /** 客户id */
    private String customerId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
