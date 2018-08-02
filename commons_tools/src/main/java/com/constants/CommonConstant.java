package com.constants;

public interface CommonConstant {
    /**
     * 申请人最小年龄
     */
    int MIN_AGE = 22;

    /**
     * 申请人最大年龄
     */
    int MAX_AGE = 50;

    /**
     * 订单-待申请状态
     */
    String ORDER_STATE_PREAPPLY = "1";
    /**
     * 订单-审核中状态
     */
    String ORDER_STATE_REVIEW = "2";
    /**
     * 订单-待签约状态
     */
    String ORDER_STATE_PRESIGNED = "3";
    /**
     * 订单-待放款状态
     */
    String ORDER_STATE_PRELOAN = "4";
    /**
     * 订单-待还款状态
     */
    String ORDER_STATE_PREREPAYMENT = "5";
    /**
     * 订单-已结清状态
     */
    String ORDER_STATE_CLOSEACOUNT = "6";
    /**
     * 订单-已取消状态
     */
    String ORDER_STATE_CANCEL = "7";
    /**
     * 订单-申请失败状态
     */
    String ORDER_STATE_FAILED = "8";

    /**
     * 白名单状态-1启用 0停用
     */
    String WHITE_STATUS_ON = "1";

    String WHITE_STATUS_OFF = "0";

    /**
     * 银行卡实名信息 1已实名
     */
    String IS_AUTHCARD_YES= "1";

}
