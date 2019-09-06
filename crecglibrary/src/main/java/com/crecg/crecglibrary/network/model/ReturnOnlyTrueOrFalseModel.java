package com.crecg.crecglibrary.network.model;

/**
 * 请求数据后只返回“成功或失败”的 model
 */

public class ReturnOnlyTrueOrFalseModel {
    public String flag; // true/false 成功或失败
    public String message; // 失败信息说明
    public String mobile; // 手机号 （注册时会返回）

    public String uploadIdentity; // 实名认证状态 （实名认证接口返回）
}
