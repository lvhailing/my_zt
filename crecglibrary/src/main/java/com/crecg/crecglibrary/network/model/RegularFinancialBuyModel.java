package com.crecg.crecglibrary.network.model;

import java.util.List;

/**
 * 7.2 理财买入 数据model
 */
public class RegularFinancialBuyModel {
    public String flag; // true/false 成功或者失败
    public String message; // 信息说明
    public String orderId; // 流水号
    public String trsAmount; // 交易金额
    public String productName; // 产品名称
    public String createTime; // 创建时间
    public String type; // 状态： CPDJ=买入 CPJD=流标CPHK=回款
    public List<TransactionDetailListModel> prodList; // 进度条列表

}
