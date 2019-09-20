package com.crecg.crecglibrary.network.model;

import java.io.Serializable;

/**
 * 4.3 提现接口 数据model
 */
public class CashWithdrawalDataModel implements Serializable {
    public String flag; // true/false 充值成功或失败
    public String message; // 信息说明

    // 以下字段均当flag为true时返回
    public String trsType; // 交易类型 RCGI=充值    WTHI=提现
    public String tradeStatus; // 交易状态 0:交易成功   1:交易失败  2:交易正在进行中
    public String acNo; // 银行卡号  当flag为true tradeStatus为0或1时返回
    public String trsAmount; // 提现金额
    public String bankName; // 转入或转出的银行卡名称
    public String submitTime; // 提交时间
    public String createTime; //创建时间
    public String oriJnlNo; // 交易流水号


}
