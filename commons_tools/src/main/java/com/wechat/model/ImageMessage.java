package com.wechat.model;

import com.api.model.common.WechatUserBase;

import java.io.Serializable;

/**
 * 图片消息
 * @author luochaofang
 */
public class ImageMessage extends WechatUserBase implements Serializable {

    private static final long serialVersionUID = 5030594775406641718L;
    /**
     * 图片链接（由系统生成）
     */
    private String PicUrl;

    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
     */
    private String MediaId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
