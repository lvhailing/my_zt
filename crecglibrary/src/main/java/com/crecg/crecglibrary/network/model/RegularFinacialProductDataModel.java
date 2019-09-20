package com.crecg.crecglibrary.network.model;

/**
 * 7.1 理财产品买入信息 model
 */

public class RegularFinacialProductDataModel {
    public String flag; // true/false 成功或失败
    public String message; // 失败信息说明
    public String localDepositAvailbal; // 账户可用余额
    public String productName; // 产品名称
    public String tenderInitAmount; // 起投金额
    public String tenderIncreaseAmount; // 递增金额
    public String highSingleInvest; // 单笔最大购买额度
    public String syAmount; // 剩余可购买额度

    /**
     * 产品状态 ：可用于调用H5买入页面时使用
     * init：初始状态
     * tender：募集中 --只在当前状态下可进行购买
     * fail：募集失败
     * success：募集成功
     *  repaying: 计息中
     *  repayed：已还清
     *  prepayed提前还款结清
     */
    public String status;
    public String tenderStartTime; // 产品开售时间
}
