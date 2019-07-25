package com.crecg.staffshield.utils;

import com.crecg.crecglibrary.network.model.BankInfoModel;
import com.crecg.crecglibrary.network.model.BankProgressModel;
import com.crecg.crecglibrary.network.model.ProductModelTestData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruifen on 7/14/19.
 */

public class DataUtil {

    public static List<ProductModelTestData> getProductList() {
        ProductModelTestData product1 = new ProductModelTestData();
        product1.annualizedReturn = "6.12%";
        product1.date = "2019-06-04 14:00";
        product1.day = "22";
        product1.name = "中铁1号";
        product1.investmentAmount = "10万起投";
        product1.flag = 1;

        ProductModelTestData product2 = new ProductModelTestData();
        product2.annualizedReturn = "6.22%";
        product2.date = "2029-06-04 24:00";
        product2.day = "22";
        product2.name = "中铁2号";
        product2.investmentAmount = "20万起投";
        product2.flag = 2;

        ProductModelTestData product3 = new ProductModelTestData();
        product3.annualizedReturn = "6.32%";
        product3.date = "2039-06-04 34:00";
        product3.day = "22";
        product3.name = "中铁3号";
        product3.investmentAmount = "30万起投";
        product3.flag = 1;

        List<ProductModelTestData> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);
        list.add(product3);
        return list;
    }

    public static BankInfoModel getBankInfo() {

        BankProgressModel process1 = new BankProgressModel();
        process1.time = "06-04 22:00";
        process1.title = "已转入";
        process1.state = 1;

        BankProgressModel process2 = new BankProgressModel();
        process2.time = "06-05 14:00";
        process2.title = "待审核";
        process2.state = 0;

        BankProgressModel process3 = new BankProgressModel();
        process3.time = "06-07 10:00";
        process3.title = "待驳回";
        process3.state = 0;

        List<BankProgressModel> processModels = new ArrayList<>();
        processModels.add(process1);
        processModels.add(process2);
        processModels.add(process3);

        BankInfoModel bankInfoModel = new BankInfoModel();
        bankInfoModel.cardType = "联名卡";
        bankInfoModel.createTime = "2039-06-04 34:00";
        bankInfoModel.flueId = "9846593459203794039405234957";
        bankInfoModel.price = 50000.00;
        bankInfoModel.progressList = processModels;

        return bankInfoModel;
    }


}
