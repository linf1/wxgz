package com.api.model.shujumohe;

import com.api.model.common.BaseSettings;

/**
 * 魔盒配置实体
 * @author 陈清玉
 */
public class ShujumoheSettings extends BaseSettings {
    /**
     * 魔盒提供token
     */
    private String boxToken;
    /**
     * 运营API URL
     */
    private String operatorApi;
    /**
     * 魔盒提供partnerCode
     */
    private String partnerCode;
    /**
     * 魔盒提供partnerKey
     */
    private String partnerKey;

    public String getBoxToken() {
        return boxToken;
    }

    public void setBoxToken(String boxToken) {
        this.boxToken = boxToken;
    }

    public String getOperatorApi() {
        return operatorApi;
    }

    public void setOperatorApi(String operatorApi) {
        this.operatorApi = operatorApi;
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
}
