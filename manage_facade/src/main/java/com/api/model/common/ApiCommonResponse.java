package com.api.model.common;

import com.constants.ApiConstants;

import java.io.Serializable;

/**
 * 公共返回类
 * @author  陈清玉
 */
public class ApiCommonResponse implements Serializable {

    private static final long serialVersionUID = -6455622312142368070L;
    /**
     *  接口返回码[0：操作成功,1:操作失败,100001:系统错误,100002:数据源访问出错,100003:数据源连接超时,100004:数据源输入参数验证失败,100005:数据源内部错误,100006:验签失败,100007:输入参数错误,100008:短信验证码输入错误或超时,请重新输入,100009:未查询到数据]",required=true
     */
    private String responseCode = ApiConstants.STATUS_SUCCESS;

    /**
     * 接口返回信息",required=true
     */
    private String responseMsg = ApiConstants.statusMap.get(ApiConstants.STATUS_SUCCESS);


    /**
     * 消息体
     */
    private Object data;

    /**
     * 原始数据
     */
    private Object originalData;


    public static ApiCommonResponse error(String responseCode, String responseMsg,
                                       Object originalData) {
        return getApiCommonResponse(responseCode, responseMsg, originalData);
    }

    public static ApiCommonResponse success(String responseCode, String responseMsg,
                                         Object originalData) {
        return getApiCommonResponse(responseCode, responseMsg, originalData);
    }

    public static ApiCommonResponse exception(Object data,Object originalData) {
        ApiCommonResponse r = new ApiCommonResponse();
        r.setData(data);
        r.setOriginalData(originalData);
        return r;
    }

    private static ApiCommonResponse getApiCommonResponse(String responseCode, String responseMsg, Object originalData) {
        ApiCommonResponse r = new ApiCommonResponse();
        r.setResponseCode(responseCode);
        r.setResponseMsg(responseMsg);
        r.setOriginalData(originalData);
        return r;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getOriginalData() {
        return originalData;
    }

    public void setOriginalData(Object originalData) {
        this.originalData = originalData;
    }

}
