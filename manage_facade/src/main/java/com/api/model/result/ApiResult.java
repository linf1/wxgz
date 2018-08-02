package com.api.model.result;

import java.io.Serializable;
import java.util.Date;

/**
 * 远程请求数据存储实体
 * @author 陈清玉
 */
public class ApiResult implements Serializable {

    private static final long serialVersionUID = 1483849209037994531L;

    private String id;
    /**
     * 接口返回码
     */
    private String code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * '渠道子类型名称 如何魔盒下的：社保，等
     */
    private String sourceChildName;
    /**
     *渠道子类型码
     */
    private String sourceChildCode;
    /**
     *'渠道数据源 ：如魔盒，同盾，个人征信码
     */
    private String sourceCode;
    /**
     *'渠道数据源 ：如魔盒，同盾，个人征信名称
     */
    private String sourceName;
    /**
     *真实姓名
     */
    private String realName;
    /**
     *手机号
     */
    private String userMobile;
    /**
     *用户名称
     */
    private String userName;
    /**
     *身份证号码
     */
    private String identityCode;
    /**
     * 唯一键
     */
    private String onlyKey;

    /**
     * 结果数据
     */
    private String resultData;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 调用API返回的查询报告ID
     */
    private String apiReturnId;

    /**
     * 1:有效，0：无效
     */
    private Integer state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSourceChildName() {
        return sourceChildName;
    }

    public void setSourceChildName(String sourceChildName) {
        this.sourceChildName = sourceChildName;
    }

    public String getSourceChildCode() {
        return sourceChildCode;
    }

    public void setSourceChildCode(String sourceChildCode) {
        this.sourceChildCode = sourceChildCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getOnlyKey() {
        return onlyKey;
    }

    public void setOnlyKey(String onlyKey) {
        this.onlyKey = onlyKey;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getApiReturnId() {
        return apiReturnId;
    }

    public void setApiReturnId(String apiReturnId) {
        this.apiReturnId = apiReturnId;
    }
}
