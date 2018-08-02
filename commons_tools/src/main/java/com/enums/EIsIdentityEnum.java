package com.enums;

/**
 * 是否通过实名认证（1，已认证，2未认证）
 * @author 陈清玉
 */
public enum EIsIdentityEnum {
    /**
     * 已认证
     */
    CERTIFIED("1","已认证"),
    /**
     *未认证
     */
    UN_CERTIFIED("2","未认证");

    EIsIdentityEnum(String code,String name){
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

    public EIsIdentityEnum getBycode(String code){
        for (EIsIdentityEnum identityEnum : EIsIdentityEnum.values()) {
            if(identityEnum.code.equals(code)){
                return identityEnum;
            }
        }
        return null;
    }
}
