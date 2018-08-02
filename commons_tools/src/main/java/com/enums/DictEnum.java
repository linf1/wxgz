package com.enums;

/**
 * 消息模板
 * @author 韩梅生
 */
public enum DictEnum {

    /**
     *提交申请
     */
    SUB_APPLY("jksq","消息内容");

    DictEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

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

    public DictEnum getBycode(String code){
        for (DictEnum identityEnum : DictEnum.values()) {
            if(identityEnum.code.equals(code)){
                return identityEnum;
            }
        }
        return null;
    }
}
