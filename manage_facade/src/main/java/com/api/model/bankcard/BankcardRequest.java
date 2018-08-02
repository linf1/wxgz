package com.api.model.bankcard;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行卡四要素请求实体
 * @author 陈清玉
 */
public class BankcardRequest implements Serializable {
    private static final long serialVersionUID = 6644558143229491994L;
    /**
     * merchantNumber	必要	string	商户号，碧有信分配
     */
    private String merchantNumber;
    /**
     * merchantOrder	必要	string	请求订单号
     */
    private String merchantOrder;
    /**
     * merchantNeqNo	必要	string	请求流水号，每次请求唯一
     */
    private String merchantNeqNo;
    /**
     * cardId	必要	string	身份证号
     */
    private String cardId;
    /**
     * realName	必要	string	真实姓名
     */
    private String realName;
    /**
     * rbankCardNo	必要	string	银行卡号必传
     */
    private String bankCardNo;
    /**
     * reservePhone	必要	string	预留手机号
     */
    private String reservePhone;
    /**
     * userId	非必要	string	用户ID，有则传
     */
    private String userId;
    /**
     * smsCode	必要	string	短信验证码
     */
    private String smsCode;

    private Date createTime;

    public String getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public String getMerchantOrder() {
        return merchantOrder;
    }

    public void setMerchantOrder(String merchantOrder) {
        this.merchantOrder = merchantOrder;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getReservePhone() {
        return reservePhone;
    }

    public void setReservePhone(String reservePhone) {
        this.reservePhone = reservePhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMerchantNeqNo() {
        return merchantNeqNo;
    }

    public void setMerchantNeqNo(String merchantNeqNo) {
        this.merchantNeqNo = merchantNeqNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BankcardRequest{" +
                "merchantNumber='" + merchantNumber + '\'' +
                ", merchantOrder='" + merchantOrder + '\'' +
                ", merchantNeqNo='" + merchantNeqNo + '\'' +
                ", cardId='" + cardId + '\'' +
                ", realName='" + realName + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", reservePhone='" + reservePhone + '\'' +
                ", userId='" + userId + '\'' +
                ", smsCode='" + smsCode + '\'' +
                '}';
    }
}
