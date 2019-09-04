package com.crecg.crecglibrary.network.model;

import java.util.List;

/**
 *  基金详情页数据Model
 */
public class FundBillDetailDataModel {
    public String flag; // true/false 获取成功或失败
    public String message; // 信息说明
    public String productName; // 产品名称
    public String createTime; // 创建时间
    public String oriJnlNo; // 流水号
    public String trsAmount; // 交易金额
    public String type; // RT04：申购      RT06：赎回
    public List<FundDetailListModel> prodList; // 进度条列表






}
