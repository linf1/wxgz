package com.api.service.wechat;


import com.api.model.wechat.AuthResult;

import java.util.Map;

/**
 * 身份验证服务接口
 * @author luochaofang
 */
public interface IAuthApiService {
    String BEAN_ID = "authApiServiceImpl";

    /**
     * 接入微信
     * @param msgSignature 签名串，对应URL参数的msg_signature
     * @param timeStamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     * @param echoStr 随机串，对应URL参数的echostr
     * @return
     */
    AuthResult validConnectWechat(String msgSignature, String  timeStamp, String nonce, String echoStr);

    /**
     * filter 中验证身份
     * @param msgSignature 签名串，对应URL参数的msg_signature
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param postDataMap 密文，对应POST请求的数据
     * @return 返回验证结果或者url
     */
    AuthResult validSignature(String msgSignature, String  timestamp, String nonce, Map<String, String> postDataMap);
}
