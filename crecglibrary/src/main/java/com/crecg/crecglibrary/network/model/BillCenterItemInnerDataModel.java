package com.crecg.crecglibrary.network.model;

import java.util.List;

/**
 * 账单中心数据 model（相当于是data这个list的一项item）
 */

public class BillCenterItemInnerDataModel {
    public String id; // 当前记录id
    public String payNo; // 付款账户
    public String withdradalNo; // 收款账户
    public String  billTrsAmount; // 交易金额 保留两位

    /**
     * WTHI = 勘设联名卡-银行卡 (提现)  -6000.00
     * RCGI = 银行卡-勘设联名卡(充值)  +1000.00
     *
     * XYSG = 工资宝买入(勘设联名卡买入工资宝)  -5000.00
     * XYSH = 工资宝赎回 (工资宝赎回到勘设联名卡) +6000.00
     * XYSY = 工资宝收益   +10.00

     *
     * CPDJ = 勘设联名卡-理财（投标）  -5000.00
     * CPJD = 理财-勘设联名卡（流标）
     * CPHK = 理财-勘设联名卡（回款）  +6000.00
     */
    public String rtxnTypeCode;
    public String orderId; // 交易单号
    public String transId; // 原交易流水号-查看详情时传入
    public String createTime; // 交易时间
}
