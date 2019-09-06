package com.crecg.crecglibrary.network.model;

import java.util.List;

/**
 * 5.2 转入工资宝 数据model
 */
public class BuyFundModel {
    public String flag; // true/false 成功或者失败
    public String message; // 信息说明
    public String oriJnlNo; // 渠道流水 当flag为true时返回
    public String txnTime; // 交易时间
    public String type; // 状态：RT04：申购 RT06：赎回
    public String productName; // 产品名称
    public String trsAmount; // 交易金额
    public List<BuyFundProgressModel> prodList; // 进度条列表

}
