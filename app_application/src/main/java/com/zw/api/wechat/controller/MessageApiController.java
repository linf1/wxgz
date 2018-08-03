package com.zw.api.wechat.controller;

import com.api.service.wechat.IMessageApiService;
import com.base.util.AppRouterSettings;
import com.zw.web.base.AbsBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 消息API控制器
 * 接收微信消息
 * @author luochaofang
 */
@Controller
@RequestMapping(value = AppRouterSettings.VERSION  + "/message")
public class MessageApiController extends AbsBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageApiController.class);

    @Autowired
    private IMessageApiService messageApiService;

    /**
     * 发送文本消息
     * @return 消息xml
     */
    @PostMapping(AppRouterSettings.WECHAT_TEXT_MESSAGE)
    public void toSendTextMessage(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> requestMap = xmlToMap(request);
            String xmlString = messageApiService.getToTextMessage(requestMap);
            LOGGER.info("发送文本消息:{}", xmlString);
            outWrite(xmlString, response);
        } catch (Exception e) {
            LOGGER.info("发送文本消息失败" + e);
        }
    }
}
