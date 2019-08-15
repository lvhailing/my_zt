package com.crecg.crecglibrary.network.model;

/**
 * 我的模块数据 model
 */

public class MyDataModel {
    public String flag; // true/false 成功或失败
    public String message; // 失败信息说明
    public String userName; // 姓名
    public String acNo; // 银行卡号  不存在：N
    public String account; // 联名卡-电子账户  不存在：N
    public String autStatus; // N:无实名认证  S:实名认证成功  T:实名认证中
    public String depositAvailBal; // 联名卡-电子账户 可用余额
    public String availBalance; // 账户-总资产
    public String userLastProfit; // 账户-昨日收益
    public String userLastProfitSum; // 账户-累计收益
    public String prodLastProfit; // 基金-昨日收益
    public String prodShare; // 基金-持仓总额
    public String productSum; // 理财产品-持仓总额
    public String dsProfit; // 理财产品-待收收益
}
