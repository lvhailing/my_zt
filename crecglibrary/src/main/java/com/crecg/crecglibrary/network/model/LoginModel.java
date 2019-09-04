package com.crecg.crecglibrary.network.model;

/**
 * 登录接口model
 */

public class LoginModel {
    public String flag; // true/false 成功或失败
    public String message; // 信息说明
    public String userId;  // 用户ID
    public String mobile;  // 用户手机号
    public String idNo;  // 用户身份证号
    public String userName;  // 用户姓名
    public String autStatus;  // 实名认证状态  N:无实名认证   S:实名认证成功   T:实名认证中

}
