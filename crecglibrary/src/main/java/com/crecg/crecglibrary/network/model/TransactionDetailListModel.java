package com.crecg.crecglibrary.network.model;

import java.io.Serializable;

/**
 * 基金、理财账单详情进度数据
 */

public class TransactionDetailListModel implements Serializable {
    public String progressValue; // 图标: 0:蓝色 1：灰色
    public String progressTitle; // 标题
    public String progressTime; // 交易时间 理财详情时 progressValue=1 progressTime返回字符串null
}
