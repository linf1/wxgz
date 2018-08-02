package com.zw.app.util;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年02月21日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class AppConstant {
    /**
     * USER_INFO 用户信息
     */
    public static final String APP_USER_INFO = "app_user_info";

    /**
     * 短信验证码KEY
     */
    public static final String SMS_KEY  = "sms_code";

    /**
     * 请求订单号
     */
    public static final String MERCHANT_ORDER  = "merchant_order";
    /**
     * 请求流水号，每次请求唯一
     */
    public static final String MERCHANT_NEQNO  = "merchant_neqno";

    public static final String APP_SYS_ID_VALUE = "DAI_WO_ZOU";
    public static final String APP_SYS_ID_KEY = "APP_SYS_ID";
    /**
     * 图片验证码请求间隔时间 防止频繁请求系统 1秒延迟
     */
    public static final int CAPTCHA_INTERVAL_TIME = 1000;
    /**
     * 图片验证码超时时间 5分钟以后超时
     */
    public static final int SMS_CODE_OVERTIME = 60 * 5;
    /**
     * 手机验证码超时时间 1分钟以后超时
     */
    public static final int SMS_CODE_PHONE_OVERTIME = 60;






}

