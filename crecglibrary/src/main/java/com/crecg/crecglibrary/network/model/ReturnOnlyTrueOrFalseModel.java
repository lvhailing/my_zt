package com.crecg.crecglibrary.network.model;

/**
 * 请求数据后只返回“成功或失败”的 model
 */

public class ReturnOnlyTrueOrFalseModel {
    public String flag; // true/false 成功或失败
    public String message; // 失败信息说明
}
