package com.api.model.tongdun;

import java.io.Serializable;

/**
 *  同盾审核报告AO
 * @author 陈青玉
 */
public class ReportAO implements Serializable {
    private static final long serialVersionUID = -3721527294663526053L;

    /**
     * 是否调用成功
     */
    private boolean success;
    /**
     * 报告Id
     */
    private String reportId;

    /**
     * 失败原因代码
     */
    private String reasonCode;

    /**
     * 失败原因描述
     */
    private String reasonDesc;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    @Override
    public String toString() {
        return "ReportAO{" +
                "success=" + success +
                ", reportId='" + reportId + '\'' +
                ", reasonCode='" + reasonCode + '\'' +
                ", reasonDesc='" + reasonDesc + '\'' +
                '}';
    }
}
