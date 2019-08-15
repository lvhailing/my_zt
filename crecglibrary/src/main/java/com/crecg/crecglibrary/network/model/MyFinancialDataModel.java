package com.crecg.crecglibrary.network.model;

import java.io.Serializable;
import java.util.List;

/**
 * 我的理财--持有中和已回款列表数据接口model
 */
public class MyFinancialDataModel implements Serializable{
    public String flag; // true/false 获取成功或失败
    public String message; // 信息说明
    public String productAllSum; // 持仓总额
    public String ljProfit; // 累积收益
    public String dsProfit; // 待收收益
    public String productType; // 持有中:possession    已结束:end
    public List<MyFinancialProductItemDataModel> listProducts; // 理财产品列表

}
