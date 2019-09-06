package com.crecg.crecglibrary.network.model;

/**
 * 5.1 用户持仓信息 数据model
 */
public class AccountInfoModel {
    public String flag; // true/false 成功或者失败
    public String message; // 信息说明
    public String localDepositAvailbal; // 账户可用余额
    public String userProdAmount; // 基金持仓总额
    public String today; // 当前日期
    public String bearTime; // 预计收益日期
    public String status; // 是否开通电子账户  N = 未开通   Y = 开通
    public String account; // 电子账户  status = N 时不返回
    public String acNo; // 银行卡号   status = N 时不返回
    public String idNo; // 身份证号
    public String userName; // 姓名
}
