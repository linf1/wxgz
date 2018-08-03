package com.base.util;

/**
 * app路由管理
 * @author luochaofang
 */
public interface AppRouterSettings {

    String VERSION = "/1.0.0";

    String API_MODULE = "/api";

    String APPLY_MODULE = "/apply";

    String BASIC_MODUAL = "/basic";

    String LINKMAN_MODUAL = "/linkMan";


    //////////////////////// ----------------  微信请求 mapping url  --------------

    String WECHAT_TEXT_MESSAGE = "/toSendTextMessage";

    String WECHAT_IMAGE_MESSAGE = "/toSendImageEMessage";

    String WECHAT_VOICE_MESSAGE = "/toSendVoiceMessage";


}
