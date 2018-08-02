package com.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * API常量
 * @author  陈清玉
 */
public class ApiConstants {

    public static Map<String, String> statusMap = new HashMap<String, String>();

    public static final String STATUS_INPUT_PHONE_ERROR_MSG = "手机号不合法";
    public static final String STATUS_INPUT_ID_ERROR_MSG = "身份证号不合法";
    public static final String STATUS_INPUT_NAME_ERROR_MSG = "姓名不合法";

    public static final String STATUS_SUCCESS = "0";
    public static final String STATUS_SUCCESS_MSG = "操作成功";

    public static final String STATUS_ERROR = "1";
    public static final String STATUS_ERROR_MSG = "操作失败";
    /**
     * zw_api_result 表 state 有效
     */
    public static final int STATUS_CODE_STATE = 1;
    /**
     * zw_api_result 表 state 无效
     */
    public static final int STATUS_CODE_NO_STATE = 0;

    public static final String STATUS_CREATE_SUCCESS = "2";
    public static final String STATUS_CREATE_SUCCESS_MSG = "阶段一操作成功";

    public static final String STATUS_SYS_ERROR = "100001";
    public static final String STATUS_SYS_ERROR_MSG = "系统错误";

    public static final String STATUS_DATASOURCE_ERROR = "100002";
    public static final String STATUS_DATASOURCE_ERROR_MSG = "网络错误";

    public static final String STATUS_DATASOURCE_TIME_OUT = "100003";
    public static final String STATUS_DATASOURCE_TIME_OUT_MSG = "数据源连接超时";

    public static final String STATUS_DATASOURCE_INPUT_ERROR = "100004";
    public static final String STATUS_DATASOURCE_INPUT_ERROR_MSG = "数据源输入参数验证失败";

    public static final String STATUS_DATASOURCE_INTERNAL_ERROR = "100005";
    public static final String STATUS_DATASOURCE_INTERNAL_ERROR_MSG = "数据源内部错误";

    public static final String STATUS_SIGN_ERROR = "100006";
    public static final String STATUS_SIGN_ERROR_MSG = "验签失败";

    public static final String STATUS_INPUT_ERROR = "100007";
    public static final String STATUS_INPUT_ERROR_MSG = "必填参数缺失或不合法";

    public static final String STATUS_DONE_TIMEOUT="100008";
    public static final String STATUS_DONE_TIMEOUT_MSG="短信验证码输入错误或超时,请重新输入";

    public static final String STATUS_INFO_NOT_FOUND="100009";
    public static final String STATUS_INFO_NOT_FOUND_MSG="未查询到数据";

    public static final String STATUS_ACCOUNT_PASSWORD_ERROR="100010";
    public static final String STATUS_ACCOUNT_PASSWORD_MSG="账号或者密码错误";

    public static final String STATUS_VERIF_CODE_ERROR="100011";
    public static final String STATUS_VERIF_CODE_MSG="短信验证码错误";

    public static final String STATUS_CREDIT_INFO_ERROR="100012";
    public static final String STATUS_CREDIT_INFO_MSG="输入信息有误";

    /**
     * 同盾API常量
     */
    public static final String REPORT_SUCCESS_KEY="success";
    public static final String REASON_CODE_KEY="reason_code";
    public static final String REASON_DESC_KEY="reason_desc";
    public static final String API_TONGDUN_KEY = "tongdun";
    public static final String API_TONGDUN_TITLE = "同盾";

    /**
     * 个人征信API常量
     */
    public static final String API_SUCCESS_KEY="success";
    public static final String API_MESSAGE_KEY="message";
    public static final String API_CODE_KEY="code";
    public static final String API_TOKEN_KEY="token";
    public static final String API_DATA_KEY="data";
    public static final String API_TASK_STATUS_KEY="taskStatus";
    public static final String API_TASK_RESULT_KEY = "taskResult";
    public static final String API__PBC_1_CODE_KEY = "pbc_1";
    public static final String API__PBC_2_CODE_KEY = "pbc_2";
    public static final String API_CREDIT_TITLE = "个人征信";


    /**
     * API常量
     */
    public static final String API_MOHE_KEY = "mohe";
    public static final String API_MOHE_TITLE = "魔盒";
    public static final String API_MOHE_YYS = "运营商";

    /**
     * 银行卡四要素发送短信成功状态码
     */
    public static final String AUTH_SEND_STATUS_SUCCESS = "4";
    /**
     * 银行卡四要素短信验证成功状态码
     */
    public static final String AUTH_FIRM_STATUS_SUCCESS = "1";


    //成功状态
    public static final String HUA_STATUS="1";

    static {
        statusMap.put(STATUS_SUCCESS, STATUS_SUCCESS_MSG);
        statusMap.put(STATUS_ERROR, STATUS_ERROR_MSG);
        statusMap.put(STATUS_SYS_ERROR, STATUS_SYS_ERROR_MSG);
        statusMap.put(STATUS_DATASOURCE_ERROR, STATUS_DATASOURCE_ERROR_MSG);
        statusMap.put(STATUS_DATASOURCE_TIME_OUT, STATUS_DATASOURCE_TIME_OUT_MSG);
        statusMap.put(STATUS_DATASOURCE_INPUT_ERROR, STATUS_DATASOURCE_INPUT_ERROR_MSG);
        statusMap.put(STATUS_DATASOURCE_INTERNAL_ERROR, STATUS_DATASOURCE_INTERNAL_ERROR_MSG);
        statusMap.put(STATUS_SIGN_ERROR, STATUS_SIGN_ERROR_MSG);
        statusMap.put(STATUS_INPUT_ERROR, STATUS_INPUT_ERROR_MSG);
        statusMap.put(STATUS_DONE_TIMEOUT,STATUS_DONE_TIMEOUT_MSG);
        statusMap.put(STATUS_INFO_NOT_FOUND,STATUS_INFO_NOT_FOUND_MSG);
    }
}
