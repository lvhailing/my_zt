package com.crecg.crecglibrary.network.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 账单中心
 */
public class BillCenterListData<T> implements Serializable{
    public int type;
    public String time;
    public ArrayList<BillCenterModelData> jsonData;

}
