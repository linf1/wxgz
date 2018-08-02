package com.enums;

/**
 * order_operation_record 表 操作结果【 1提交 2通过 3 拒绝 4 回退 5 同意  6放弃 7放款】
 * @author 陈淸玉 create on 2018-07-18
 */
public enum OperationResultEnum {
    /**
     * 提交
     */
    COMMIT("提交",1),
    /**
     * 通过
     */
    PASS("通过",2),
    /**
     * 拒绝
     */
    REFUSE("拒绝",3),
    /**
     * 回退
     */
    BACK("回退",4),
    /**
     * 同意
     */
    OK("同意",5),
    /**
     * 放弃
     */
    GIVE_UP("放弃",6),
    /**
     * 放款
     */
    LOAN("放款",7);

    OperationResultEnum(String label,int code ) {
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

    public static OperationResultEnum getByCode(int code){
        for (OperationResultEnum operationResultEnum : OperationResultEnum.values()) {
            if (operationResultEnum.getCode() == code) {
              return operationResultEnum;
            }
        }
        return null;
    }
}
