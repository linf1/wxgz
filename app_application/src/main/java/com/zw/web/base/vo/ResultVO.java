package com.zw.web.base.vo;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * <strong>Title : 辅助进件提交接口<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年02月17日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:zh-pc <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String retCode = VOConst.SUCCESS;
    private String retMsg;
    private T retData;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getRetData() {
        return retData;
    }

    public void setRetData(T retData) {
        this.retData = retData;
    }


    /**
     * 扩展异常方法===============================================================
     * create by 陈清玉
     */
    public static ResultVO error() {
        return error(VOConst.FAIL, "未知异常，请联系管理员");
    }

    public static ResultVO error(String msg) {
        return error(VOConst.FAIL, msg);
    }

    public static ResultVO error(String code, String msg) {
        ResultVO r = new ResultVO();
        r.setRetCode(code);
        r.setRetMsg(msg);
        return r;
    }

    public static ResultVO ok(Object data) {
        ResultVO r = new ResultVO();
        r.setRetData(data);
        return r;
    }

    public static ResultVO ok(String msg ,Object  data) {
        ResultVO r = new ResultVO();
        r.setRetData(data);
        r.setRetMsg(msg);
        return r;
    }

    @Override
    public String toString() {
        JSONObject toJsonString = new JSONObject();
        toJsonString.put("retCode", retCode);
        toJsonString.put("retMsg", retMsg);
        toJsonString.put("retData", retData);
        return toJsonString.toString();
    }

    public void setErrorMsg(String errorCode, String errorMsg) {
        this.retCode = errorCode;
        this.retMsg = errorMsg;
    }

}
