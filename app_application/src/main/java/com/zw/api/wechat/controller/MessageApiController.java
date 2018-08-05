package com.zw.api.wechat.controller;

import com.api.model.wechat.AuthResult;
import com.api.service.wechat.IMessageApiService;
import com.base.util.AppRouterSettings;
import com.constants.ApiConstants;
import com.wechat.model.CryptMessage;
import com.zw.web.base.AbsBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息API控制器
 * 接收微信消息
 * @author luochaofang
 */
@Controller
@RequestMapping(value = AppRouterSettings.API_MODULE  + AppRouterSettings.MESSAGE_WECHAT)
public class MessageApiController extends AbsBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageApiController.class);

    @Resource
    private IMessageApiService messageApiService;

    /**
     * 发送文本消息
     * @return 消息xml
     */
    @PostMapping(value = AppRouterSettings.WECHAT_TEXT_MESSAGE)
    @ResponseBody
    public void toSendTextMessage(HttpServletRequest request, HttpServletResponse response) {
        try {
            LOGGER.info("开始处理文本消息:{..............}");
            Map<String, String> requestMap = new HashMap<>(4);
            requestMap.put("msgSignature",request.getParameter("msg_signature"));
            requestMap.put("timestamp",request.getParameter("timestamp"));
            requestMap.put("nonce",request.getParameter("nonce"));
            requestMap.put("encrypt",request.getAttribute("encrypt").toString());
            AuthResult authResult = messageApiService.getToTextMessage(requestMap);
            if(ApiConstants.STATUS_SUCCESS.equals(authResult.getCode())) {
                outWrite(authResult.getMsg(), response);
                LOGGER.info("成功发送文本消息:{}", authResult.getMsg());
            } else {
                LOGGER.info("文本消息发送失败：{}"+ authResult.getMsg());
            }
        } catch (Exception e) {
            LOGGER.info("发送文本消息失败" + e);
        }
    }
}
