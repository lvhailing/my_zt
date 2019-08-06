package com.crecg.crecglibrary.network.model;

import java.io.Serializable;

/**
 * 定期理财列表
 * Created by junde on 2019/8/6.
 */

public class HomeAndFinancialProductList implements Serializable{

    public String id; // 产品id
    public String name; // 产品名称
    public String amount; // 融资规模
    public String annualRate; // 预期年化收益率
    public String tenderStartTime; // 融资开始时间
    public String tenderEndTime; // 融资结束时间
    public String tenderInitAmount; // 起投金额
    public String tenderIncreaseAmount; // 递增金额
    public String tenderAmount; // 产品投标总额
    public String tenderUsers; // 产品投标总人数
    public String timeLimit; // 产品期限

    /**
     *  init：初始状态--即将开卖
     *  tender：融资中--热卖中
     *  fail：融资失败---已卖完
     *  success：融资成功--已卖完
     *  repaying: 还款中--计息中
     *  repayed：已还清--已还款
     *  prepayed: 提前还款结清--已还款
     *
     *  产品状态顺序：热卖中(tender)-->即将开售(init)-->已售馨(产品已满标，但钱还未打给借款人)-->计息中（表示钱已打给借款人）-->已回款
     */
    public String status;
    public String highSingleInvest; // 单笔最大购买金额
}
