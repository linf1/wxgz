package com.zw.miaofuspd.api.wechat;

import com.api.model.common.WechatSettings;
import com.api.service.wechat.IAuthApiService;
import com.base.util.AppRouterSettings;
import com.constants.ApiConstants;
import com.api.model.wechat.AuthResult;
import com.wechat.util.AesException;
import com.wechat.util.MessageUtil;
import com.wechat.util.WXBizMsgCrypt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Map;


/**
 * 身份验证服务
 * @author luochaofang
 */
@Service(IAuthApiService.BEAN_ID)
public class AuthApiServiceImpl implements IAuthApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthApiServiceImpl.class);

    @Autowired
    private WechatSettings wechatSettings;


    @Override
    public AuthResult validSignature(String signature, String timestamp, String nonce, Map<String, String> postDataMap) {
        try {
            LOGGER.info("token:{}"+ wechatSettings.getToken());
           /* // 发送方帐号（open_id）
            String fromUserName = postDataMap.get("FromUserName");
            // 公众帐号
            String toUserName = postDataMap.get("ToUserName");

            // 消息内容
            String content = postDataMap.get("Content");*/
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(wechatSettings.getToken(), wechatSettings.getEncodingaeskey(), wechatSettings.getAppId());
            String result = wxBizMsgCrypt.decryptMsg(signature, timestamp, nonce, postDataMap.get("Encrypt"));
            String url = "";
            if(StringUtils.isNotBlank(result)) {
                //验签通过，判断是否是含有消息类型
                if(postDataMap.containsKey("MsgType")) {
                    // 消息类型
                    LOGGER.info("解密之前："+ postDataMap.get("MsgType"));
                    String result1 = wxBizMsgCrypt.decryptMsg(signature, timestamp, nonce, postDataMap.get("MsgType"));
                    LOGGER.info("解密之后："+ result1);
                    String msgType = result1;
                    //返回url
                    switch (msgType) {
                        case MessageUtil
                                .REQ_MESSAGE_TYPE_TEXT:
                            url = AppRouterSettings.API_MODULE + AppRouterSettings.MESSAGE_WECHAT
                                        + AppRouterSettings.WECHAT_TEXT_MESSAGE;
                            break;
                        case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
                            url = AppRouterSettings.API_MODULE + AppRouterSettings.MESSAGE_WECHAT
                                    + AppRouterSettings.WECHAT_IMAGE_MESSAGE;
                            break;
                        case MessageUtil.REQ_MESSAGE_TYPE_VOICE:
                            url = AppRouterSettings.API_MODULE + AppRouterSettings.MESSAGE_WECHAT
                                    + AppRouterSettings.WECHAT_VOICE_MESSAGE;
                            break;
                        default:
                    }
                }
            }
            //密钥，公众账号的app secret
            LOGGER.info("app_secret："+ result);
            return AuthResult.ok(ApiConstants.STATUS_SIGNATURE_SUCCESS, url);
        } catch (AesException e) {
            return AuthResult.error(ApiConstants.STATUS_SIGNATURE_ERROR, e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {

        //
        // 第三方回复公众平台
        //

        // 需要加密的明文
        String encodingAesKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
        String token = "pamtest";
        String timestamp = "1409304348";
        String nonce = "xxxxxx";
        String appId = "wxb11529c136998cb6";
        String replyMsg = " 中文<xml><ToUserName><![CDATA[oia2TjjewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";

        WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
        String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
        System.out.println("加密后: " + mingwen);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(mingwen);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);

        Element root = document.getDocumentElement();
        NodeList nodelist1 = root.getElementsByTagName("Encrypt");
        NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

        String encrypt = nodelist1.item(0).getTextContent();
        String msgSignature = nodelist2.item(0).getTextContent();

        String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
        String fromXML = String.format(format, encrypt);

        //
        // 公众平台发送消息给第三方，第三方处理
        //

        // 第三方收到公众号平台发送的消息
        String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
        System.out.println("解密后明文: " + result2);

        //pc.verifyUrl(null, null, null, null);
    }
}
