package com.api.model.common;


/**
 * 微信配置信息
 * @author luochaofang
 */
public class WechatSettings extends BaseSettings {
    /**
     * 开发者ID
     */
    private String appId ;

    /**
     * 消息加解密密钥
     */
    private String encodingaeskey;

    /**
     * 令牌(Token)
     */
    private String token;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getEncodingaeskey() {
        return encodingaeskey;
    }

    public void setEncodingaeskey(String encodingaeskey) {
        this.encodingaeskey = encodingaeskey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
