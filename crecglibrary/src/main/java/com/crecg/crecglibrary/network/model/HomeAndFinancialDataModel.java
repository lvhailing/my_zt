package com.crecg.crecglibrary.network.model;

import java.io.Serializable;
import java.util.List;

/**
 * 首页&&定期理财列表数据接口model
 */
public class HomeAndFinancialDataModel implements Serializable{
    public String prodName; // 基金名称 -首页 (工资宝)
    public String prodId; // 基金代码
    public String prodSubId; // 基金标识码
    public String annualReturnBys; // 基金七日年化
    public String pageNum; // pageNum
    public List<HomeAndFinancialProductItemDataModel> productList; // 理财产品列表

}
