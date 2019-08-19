package com.crecg.crecglibrary.network.model;

import java.util.List;

/**
 * 账单中心的 model
 */

public class BillCenterDataModel {
    public String flag; // true/false 成功或失败
    public String message; // 失败信息说明
    public List<BillCenterItemOutDataModel> billList; // 产品   返回列表
}
