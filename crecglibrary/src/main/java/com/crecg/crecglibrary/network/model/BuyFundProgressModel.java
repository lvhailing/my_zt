package com.crecg.crecglibrary.network.model;

import java.io.Serializable;

/**
 * Created by junde on 2019/9/6.
 */

public class BuyFundProgressModel implements Serializable{
    public String progressValue; // 图标: 0:蓝色 1：灰色
    public String progressTitle; // 标题
    public String progressTime; // 交易时间
}
