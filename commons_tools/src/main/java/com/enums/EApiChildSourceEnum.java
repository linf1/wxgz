package com.enums;

/**
 * api来源枚举
 * @author 陈清玉
 */
public enum EApiChildSourceEnum {
    MOHE_YYS("11","运营商信息查询"),
    TODONG_CHILD("21","同盾贷前审核"),
    CREDIT_CHILD("31","人行个人征信查询");
    private String code;
    private String name;
    EApiChildSourceEnum(String code, String name){
        this.code = code;
        this.name = name;
    }
    public EApiChildSourceEnum getByCode(String code){
        for (EApiChildSourceEnum sourceEnum : EApiChildSourceEnum.values()) {
            if(sourceEnum.code.equals(code)){
                return sourceEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
