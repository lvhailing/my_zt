package com.crecg.crecglibrary.network.model;

import java.util.List;

/**
 * 账单中心 数据model(相当于是billList的一项item)
 */

public class BillCenterItemOutDataModel {
    public String month;
    public List<BillCenterItemInnerDataModel> data; // 内层list
}
