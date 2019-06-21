package com.crecg.crecglibrary.network.model;

import java.io.Serializable;

public class ResultModel<T> implements Serializable {
//    public int error_code = -1;
//    public String reason = "";
//    public T result;

    public String check;
    public String code;
    public T data;
    public String msg;
}
