package com.crecg.crecglibrary.network.model;

import java.io.Serializable;

public class ResultModel<T> implements Serializable {

    public String check;
    public String code;
    public T data;
    public String msg;
}
