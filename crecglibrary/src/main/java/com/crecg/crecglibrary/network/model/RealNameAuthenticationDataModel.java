package com.crecg.crecglibrary.network.model;

/**
 * 实名认证数据model
 */
public class RealNameAuthenticationDataModel {
    public String flag; // true/false 成功或者失败
    public String message; // message
    public String uploadIdentity; // 实名认证状态  N=无实名认证   T=认证中  S=认证成功

}
