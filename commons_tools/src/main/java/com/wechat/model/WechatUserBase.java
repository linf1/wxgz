package com.wechat.model;

import java.io.Serializable;

/**
 * 基础settings
 * @author luochaofang
 */
public class WechatUserBase implements Serializable {

    private static final long serialVersionUID = 5030594775406641718L;
    /**
     * 开发者微信号
     */
    private String ToUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    private String FromUserName;

    /**
     * 消息创建时间 （整型）
     */
    private String CreateTime;

    /**
     * 消息类型
     */
    private String MsgType;

    /**
     * 消息id，64位整型
     */
    private String MsgId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
