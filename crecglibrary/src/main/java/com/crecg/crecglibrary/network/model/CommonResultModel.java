package com.crecg.crecglibrary.network.model;

import java.io.Serializable;

/**
 *  所有Model数据共有的最外层数据
 * @param <T>（指：data里面数据类型） 第二层数据类型
 */
public class CommonResultModel<T> implements Serializable {

    public String check;
    public String code;
    public T data;
    public String msg;
}
