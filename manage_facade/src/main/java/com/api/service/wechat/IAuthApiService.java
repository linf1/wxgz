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
     * filter 中验证身份
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param postDataMap 密文，对应POST请求的数据
     * @return 返回验证结果或者url
     */
    AuthResult validSignature(String signature, String  timestamp, String nonce, Map<String, String> postDataMap);
}
