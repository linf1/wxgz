package com.api.model.bankcard;

import com.api.model.common.BaseSettings;

/**
 * 银行卡四要素配置实体
 * @author 陈清玉
 */
public class BankcardSettings extends BaseSettings {
    private String  merchantNumber;
    /**
     * 银行列表url
     */
    private String  bankListUrl;
    /**
     * 支行列表url
     */
    private String  subBankListUrl;
    /**
     * 市列表url
     */
    private String  cityListUrl;
    /**
     * 省列表url
     */
    private String  provinceListUrl;

    private String authConfirmUrl;

    public String getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public String getBankListUrl() {
        return bankListUrl;
    }

    public void setBankListUrl(String bankListUrl) {
        this.bankListUrl = bankListUrl;
    }

    public String getSubBankListUrl() {
        return subBankListUrl;
    }

    public void setSubBankListUrl(String subBankListUrl) {
        this.subBankListUrl = subBankListUrl;
    }

    public String getCityListUrl() {
        return cityListUrl;
    }

    public void setCityListUrl(String cityListUrl) {
        this.cityListUrl = cityListUrl;
    }

    public String getProvinceListUrl() {
        return provinceListUrl;
    }

    public void setProvinceListUrl(String provinceListUrl) {
        this.provinceListUrl = provinceListUrl;
    }

    public String getAuthConfirmUrl() {
        return authConfirmUrl;
    }

    public void setAuthConfirmUrl(String authConfirmUrl) {
        this.authConfirmUrl = authConfirmUrl;
    }
}
