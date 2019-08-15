package com.crecg.crecglibrary.network.model;

import java.io.Serializable;

/**
 * 我的理财列表
 */

public class MyFinancialProductItemDataModel implements Serializable{

    public String productId; // 产品id
    public String name; // 产品名称
    public Number userInfoId; // 用户ID
    public String repayStartDate; // 投资开始时间 计息开始之后的投资周期
    public String repayEndDate; // 投资结束时间
    public String productSum; // 产品持仓总额
    public String yuJiPoFit; // 预计收益
    public String createTime; // 用户第一次购买时间 计息开始之前的投资周期

    /**
     *  tender：融资中--募集中
     *  fail：融资失败---募集失败
     *  success：融资成功--已满标
     *  repaying: 还款中--计息中
     *  repayed：已还清--已回款
     *  prepayed: 提前还款结清--已回款
     *
     *  产品状态顺序：即将开售(init)-->募集中(tender)-->包含：已满标(产品已满标，但钱还未打给借款人)和募集失败-->计息中（表示钱已打给借款人）-->已回款
     */
    public String status; // 产品状态
}
