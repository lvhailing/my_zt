package com.crecg.staffshield.utils;

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


}
