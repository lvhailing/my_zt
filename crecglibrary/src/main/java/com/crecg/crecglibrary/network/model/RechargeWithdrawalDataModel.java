package com.crecg.crecglibrary.network.model;

// 充值、提现详情页数据Model
public class RechargeWithdrawalDataModel {
    public String flag; // true/false 获取成功或失败
    public String trsType; // 当flag为true时返回 交易类型: RCGI=充值  WTHI=提现
    public String tradeStatus; // 当flag为true时返回 交易状态:0-交易成功, 1-交易失败, 2-交易正在进行中
    public String trsEndTime; // 交易完成时间 当flag为true tradeStatus为0或1时返回
    public String submitTime; // 充值或提现提交时间  当flag为true时返回
    public String trsAmount; // 交易金额   当flag为true时返回
    public String bankName; // 转入或转出的银行卡名称  当flag为true时返回
    public String createTime; // 创建时间 当flag为true时返回
    public String oriJnlNo; // 流水号   当flag为true时返回
    public String message; // 信息说明




}
