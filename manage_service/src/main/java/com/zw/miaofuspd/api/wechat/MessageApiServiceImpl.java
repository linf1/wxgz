package com.zw.miaofuspd.api.wechat;

import com.api.model.common.WechatSettings;
import com.api.model.wechat.AuthResult;
import com.api.service.wechat.IMessageApiService;
import com.constants.ApiConstants;
import com.wechat.model.TextMessage;
import com.wechat.model.WechatUserBase;
import com.wechat.util.AesException;
import com.wechat.util.MessageUtil;
import com.wechat.util.WXBizMsgCrypt;
import com.wechat.util.XMLParse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 微信消息服务
 * @author luochaofang
 */
@Service(IMessageApiService.BEAN_ID)
public class MessageApiServiceImpl implements IMessageApiService {
    @Resource
    private WechatSettings wechatSettings;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageApiServiceImpl.class);

    @Override
    public AuthResult getToTextMessage(Map<String, String> map) {
        //消息密文
        LOGGER.info("消息密文：{}"+ map.get("encrypt"));
        LOGGER.info("开始解密：{。。。。。。。。。。。。。。。。。。。}");
        try {
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(wechatSettings.getToken(), wechatSettings.getEncodingaeskey(), wechatSettings.getAppId());
            String resultXml = wxBizMsgCrypt.decryptMsg(map.get("msgSignature"),map.get("timestamp"),map.get("nonce"),map.get("encrypt"));
            LOGGER.info("解密之后：{}"+ resultXml);
            //提取xml中的数据
            List<String> tagNameList = new ArrayList<>();
            tagNameList.add("FromUserName");
            tagNameList.add("ToUserName");
            tagNameList.add("MsgType");
            tagNameList.add("CreateTime");
            tagNameList.add("MsgId");
            tagNameList.add("Content");
            Map<String, String>  result = XMLParse.extract(resultXml, tagNameList);
            LOGGER.info("消息内容：{}"+ result.get("Content"));
            //加密处理
            result.put("timestamp", map.get("timestamp"));
            result.put("nonce", map.get("nonce"));
            return AuthResult.ok(ApiConstants.STATUS_SUCCESS, toStrEncryptMsgByMap(wxBizMsgCrypt, result));
        } catch (AesException e) {
            e.printStackTrace();
            return AuthResult.error(ApiConstants.STATUS_ERROR, e.getMessage());
        }

    }

    private String toStrEncryptMsgByMap(WXBizMsgCrypt wxBizMsgCrypt, Map<String, String>  result) throws AesException {
        // 发送方帐号（open_id）
        String fromUserName = result.get("FromUserName");
        // 公众帐号
        String toUserName = result.get("ToUserName");
        // 消息类型
        String msgId = result.get("MsgId");
        // 消息内容
        String content = result.get("Content");
        String timestamp = result.get("timestamp");
        String nonce = result.get("nonce");

        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setMsgId(msgId);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime()+"");

        textMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
        textMessage.setContent("你大爷");
        String xmlString = MessageUtil.textMessageToXml(textMessage);
        LOGGER.info("需要加密的回复内容：{}"+ xmlString);
        return wxBizMsgCrypt.encryptMsg(xmlString, timestamp, nonce);
    }
}
