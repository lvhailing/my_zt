package com.crecg.crecglibrary.network.model;

/**
 * 添加银行卡、身份证正反面认证数据model
 */
public class AddBankCardAndAuthenticationDataModel {
    public String flag; // true/false 成功或者失败
    public String message; // message
    public String uploadIdentity; // 实名认证状态  N=无实名认证   T=认证中  S=认证成功

    public String accountNo; // 电子账户
    public String uuid; // 晋商用户标识
}
