package com.crecg.crecglibrary.network.model;

import java.util.List;

// 联名卡布局
public class BankInfoModel {
    public double price;    //金额
    public String cardType; //勘设联盟卡
    public String createTime;
    public String flueId;   //流水号
    public List<BankProgressModel> progressList;

}
