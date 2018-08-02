package com.api.model.credit;


import com.api.model.common.BaseSettings;

/**
 * 征信配置信息
 * @author luochaofang
 */
public class CreditSettings extends BaseSettings {
    /**
     * 获取个人征信 的host
     */
    private String creditHost ;

    /**
     * 个人征信回调 的url
     */
    private String callbackUrl;

    /**
     * 个人征信获取token
     */
    private String creditTokenUrl;

    /**
     * 合作方标识  必填，权限校验用
     */
    private String appKey ;

    /**
     * 合作方标识  必填，权限校验用
     */
    private String ak ;

    /**
     * 合作方标识  必填，权限校验用
     */
    private String sk;

    /**
     *任务类型 必填，权限校验用
     */
    private String taskType;

    /**
     * 请求类型
     */
    private String requestTypeResult;

    public String getCreditHost() {
        return creditHost;
    }

    public void setCreditHost(String creditHost) {
        this.creditHost = creditHost;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCreditTokenUrl() {
        return creditTokenUrl;
    }

    public void setCreditTokenUrl(String creditTokenUrl) {
        this.creditTokenUrl = creditTokenUrl;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getRequestTypeResult() {
        return requestTypeResult;
    }

    public void setRequestTypeResult(String requestTypeResult) {
        this.requestTypeResult = requestTypeResult;
    }
}
