package com.wechat.model;


import java.io.Serializable;

/**
 * 文本消息
 * @author luochaofang
 */
public class TextMessage extends WechatUserBase implements Serializable {

    private static final long serialVersionUID = 5030594775406641718L;
    /**
     * 文本消息内容
     */
    private String Content;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
