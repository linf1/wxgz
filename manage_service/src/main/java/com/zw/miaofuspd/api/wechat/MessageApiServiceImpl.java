package com.zw.miaofuspd.api.wechat;

import com.api.service.wechat.IMessageApiService;
import com.wechat.model.TextMessage;
import com.wechat.util.MessageUtil;
import com.wechat.util.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
/**
 * 微信消息服务
 * @author luochaofang
 */
@Service(IMessageApiService.BEAN_ID)
public class MessageApiServiceImpl implements IMessageApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageApiServiceImpl.class);

    @Override
    public String getToTextMessage(Map<String, String> map) {
        // 发送方帐号（open_id）
        String fromUserName = map.get("FromUserName");
        // 公众帐号
        String toUserName = map.get("ToUserName");
        // 消息类型
        String msgType = map.get("MsgType");
        // 消息内容
        String content = map.get("Content");

        //自定义token
        String token = map.get("token");



        TextMessage textMessage = new TextMessage();
        textMessage.setContent(content);
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
        textMessage.setCreateTime(new Date().getTime()+"");
        String xmlString = MessageUtil.textMessageToXml(textMessage);

        //加密
        //WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
        return xmlString;
    }
}
