package com.api.service.wechat;


import com.api.model.credit.CreditRequest;
import com.api.model.credit.CreditResultAO;

import java.io.IOException;
import java.util.Map;

/**
 * 微信消息服务接口
 * @author luochaofang
 */
public interface IMessageApiService {
    String BEAN_ID = "messageApiServiceImpl";
    /**
     * 文本消息
     * @throws  IOException 调用异常
     * @return 返回对象
     */
    String getToTextMessage(Map<String, String> map);

}
