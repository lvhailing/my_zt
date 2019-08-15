package com.crecg.crecglibrary.network.model;

import java.io.Serializable;

/**
 * 定期理财列表
 * Created by junde on 2019/8/6.
 */

public class HomeAndFinancialProductItemDataModel implements Serializable{

    public String id; // 产品id
    public String name; // 产品名称
    public Number amount; // 融资规模
    public String annualRate; // 预期年化收益率
    public String tenderStartTime; // 融资开始时间
    public String tenderEndTime; // 融资结束时间
    public Number tenderInitAmount; // 起投金额
    public Number tenderIncreaseAmount; // 递增金额
    public Number tenderAmount; // 产品投标总额
    public Number tenderUsers; // 产品投标总人数
    public Number timeLimit; // 产品期限
    public String bfbAmount; // 规模占用百分比  例子 0.001
    public String syAmount; // 剩余可投金额

    /**
     * 七种状态，六个标签：
     *  init：初始状态--即将开卖
     *  tender：融资中--募集中
     *  success：融资成功--已满标
     *  fail：融资失败---募集失败
     *  repaying: 还款中--计息中
     *  repayed：已还清--已回款
     *  prepayed: 提前还款结清--已回款
     *
     *  产品状态顺序：即将开售(init)-->募集中(tender)-->包含：已满标(产品已满标，但钱还未打给借款人)和募集失败-->计息中（表示钱已打给借款人）-->已回款
     */
    public String status;
    public Number highSingleInvest; // 单笔最大购买金额
}
