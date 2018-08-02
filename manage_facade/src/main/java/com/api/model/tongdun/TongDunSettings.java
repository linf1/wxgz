package com.api.model.tongdun;



/**
 * 同盾配置信息
 * @author 陈清玉
 */
public class TongDunSettings {
    /**
     * 获取报告ID 的url
     */
    private String reportIdUrl ;

    /**
     *  获取信息的url
     */
    private String reportInfoUrl ;

    /**
     * 合作方标识  必填 由同盾分配，权限校验用
     */
    private String partnerCode ;
    /**
     *合作方密钥 必填 由同盾分配，权限校验用
     */
    private String partnerKey;
    /**
     * 应用名称  必填 由同盾分配
     */
    private String appName ;


    public String getReportUrl(){
        return  reportIdUrl + "?partner_code="+ partnerCode + "&partner_key="+ partnerKey +"&app_name="+ appName;
    }
    public String getReportUrl(String reportId){
        return  reportInfoUrl + "?partner_code="+ partnerCode + "&partner_key="+ partnerKey +"&app_name="+ appName +"&report_id="+reportId;
    }

    public String getReportIdUrl() {
        return reportIdUrl;
    }

    public void setReportIdUrl(String reportIdUrl) {
        this.reportIdUrl = reportIdUrl;
    }

    public String getReportInfoUrl() {
        return reportInfoUrl;
    }

    public void setReportInfoUrl(String reportInfoUrl) {
        this.reportInfoUrl = reportInfoUrl;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerKey() {
        return partnerKey;
    }

    public void setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "TongDunSettings{" +
                "reportIdUrl='" + reportIdUrl + '\'' +
                ", reportInfoUrl='" + reportInfoUrl + '\'' +
                ", partnerCode='" + partnerCode + '\'' +
                ", partnerKey='" + partnerKey + '\'' +
                ", appName='" + appName + '\'' +
                '}';
    }



}
