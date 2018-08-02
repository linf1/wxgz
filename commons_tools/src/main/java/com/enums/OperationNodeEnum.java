package com.enums;

/**
 * order_operation_record 表 操作结果
 * 【操作节点【订单申请（申请提交）、
 * 自动化审核（通过、拒绝）、人工审核（通过、拒绝、回退）、
 * 签约（同意、放弃）、放款审批（放款）、还款】
 * @author 陈淸玉 create on 2018-07-18
 */
public enum OperationNodeEnum {
    /**
     * 订单申请（申请提交）
     */
    APPLY("订单申请",1),
    /**
     * 自动化审核（通过、拒绝）
     */
    AUTO_AUDIT("自动化审核",2),
    /**
     * 人工审核（通过、拒绝、回退）
     */
    AUDIT("人工审核",3),
    /**
     * 签约（同意、放弃）
     */
    SIGN("签约",4),
    /**
     * 放款审批（放款）
     */
    LOAN_AUDIT("放款审批",5),
    /**
     * 还款
     */
    REPAYMENT("还款",6);

    OperationNodeEnum(String label, int code ) {
        this.code = code;
        this.label = label;
    }

    private int code;
    private String label;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static OperationNodeEnum getByCode(int code){
        for (OperationNodeEnum operationResultEnum : OperationNodeEnum.values()) {
            if (operationResultEnum.getCode() == code) {
              return operationResultEnum;
            }
        }
        return null;
    }
}
