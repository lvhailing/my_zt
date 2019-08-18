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

    public static String getBankInfo1() {

        String result = "{\n" +
                "        \"billList\":[\n" +
                "            {\n" +
                "                \"month\":\"2019年08月\",\n" +
                "                \"data\":[\n" +
                "                    {\n" +
                "                        \"billTrsAmount\":\"-349.23\",\n" +
                "                        \"createTime\":\"2019.08.02 16:40:41\",\n" +
                "                        \"id\":\"19080216404168061098\",\n" +
                "                        \"orderId\":\"19072011103093222229\",\n" +
                "                        \"payNo\":\"0721\",\n" +
                "                        \"rtxnTypeCode\":\"CPDJ\",\n" +
                "                        \"transId\":\"19080216404168076739\",\n" +
                "                        \"withdradalNo\":\"3081\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"billTrsAmount\":\"-358.40\",\n" +
                "                        \"createTime\":\"2019.08.02 16:40:38\",\n" +
                "                        \"id\":\"19080216403862542353\",\n" +
                "                        \"orderId\":\"19072011103093222228\",\n" +
                "                        \"payNo\":\"0721\",\n" +
                "                        \"rtxnTypeCode\":\"CPDJ\",\n" +
                "                        \"transId\":\"19080216403862575128\",\n" +
                "                        \"withdradalNo\":\"3081\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"billTrsAmount\":\"-766.16\",\n" +
                "                        \"createTime\":\"2019.08.02 16:40:35\",\n" +
                "                        \"id\":\"19080216403546696481\",\n" +
                "                        \"orderId\":\"19072011103093222227\",\n" +
                "                        \"payNo\":\"0721\",\n" +
                "                        \"rtxnTypeCode\":\"CPDJ\",\n" +
                "                        \"transId\":\"19080216403546617037\",\n" +
                "                        \"withdradalNo\":\"3081\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"month\":\"2019年07月\",\n" +
                "                \"data\":[\n" +
                "                    {\n" +
                "                        \"billTrsAmount\":\"+34.00\",\n" +
                "                        \"createTime\":\"2019.07.02 16:40:26\",\n" +
                "                        \"id\":\"19080216402621134332\",\n" +
                "                        \"orderId\":\"19072011103093222224\",\n" +
                "                        \"payNo\":\"0721\",\n" +
                "                        \"rtxnTypeCode\":\"CPHK\",\n" +
                "                        \"transId\":\"19080216402621198921\",\n" +
                "                        \"withdradalNo\":\"3081\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"billTrsAmount\":\"-100.11\",\n" +
                "                        \"createTime\":\"2019.07.02 09:14:35\",\n" +
                "                        \"id\":\"19062515525866744645\",\n" +
                "                        \"orderId\":\"19071809293161639371\",\n" +
                "                        \"payNo\":\"0721\",\n" +
                "                        \"rtxnTypeCode\":\"WTHI\",\n" +
                "                        \"transId\":\"19071809293161631802\",\n" +
                "                        \"withdradalNo\":\"3081\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"billTrsAmount\":\"+60.00\",\n" +
                "                        \"createTime\":\"2019.07.02 09:14:33\",\n" +
                "                        \"id\":\"19062515525866744644\",\n" +
                "                        \"orderId\":\"19071809293157679770\",\n" +
                "                        \"payNo\":\"0721\",\n" +
                "                        \"rtxnTypeCode\":\"RCGI\",\n" +
                "                        \"transId\":\"19071809293157658963\",\n" +
                "                        \"withdradalNo\":\"3081\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"billTrsAmount\":\"-30.00\",\n" +
                "                        \"createTime\":\"2019.07.02 09:14:30\",\n" +
                "                        \"id\":\"19062515525866744147\",\n" +
                "                        \"orderId\":\"19071809293148613774\",\n" +
                "                        \"payNo\":\"0721\",\n" +
                "                        \"rtxnTypeCode\":\"WTHI\",\n" +
                "                        \"transId\":\"19071809293148567139\",\n" +
                "                        \"withdradalNo\":\"3081\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"month\":\"2019年06月\",\n" +
                "                \"data\":[\n" +
                "                    {\n" +
                "                        \"billTrsAmount\":\"+742.78\",\n" +
                "                        \"createTime\":\"2019.06.26 09:43:48\",\n" +
                "                        \"id\":\"19072609434868630666\",\n" +
                "                        \"orderId\":\"19072609434868634876\",\n" +
                "                        \"payNo\":\"0721\",\n" +
                "                        \"rtxnTypeCode\":\"XYSH\",\n" +
                "                        \"transId\":\"19072609434868652696\",\n" +
                "                        \"withdradalNo\":\"3081\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"billTrsAmount\":\"+813.37\",\n" +
                "                        \"createTime\":\"2019.06.26 09:43:45\",\n" +
                "                        \"id\":\"19072609434564388665\",\n" +
                "                        \"orderId\":\"19072609434564351402\",\n" +
                "                        \"payNo\":\"0721\",\n" +
                "                        \"rtxnTypeCode\":\"XYSH\",\n" +
                "                        \"transId\":\"19072609434564336986\",\n" +
                "                        \"withdradalNo\":\"3081\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"billTrsAmount\":\"+80.18\",\n" +
                "                        \"createTime\":\"2019.06.26 09:43:42\",\n" +
                "                        \"id\":\"19072609434253951065\",\n" +
                "                        \"orderId\":\"19072609434253977885\",\n" +
                "                        \"payNo\":\"0721\",\n" +
                "                        \"rtxnTypeCode\":\"XYSY\",\n" +
                "                        \"transId\":\"19072609434253974349\",\n" +
                "                        \"withdradalNo\":\"3081\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"flag\":\"true\",\n" +
                "        \"message\":\"返回成功\"\n" +
                "    }";

        return result;
    }

    public static String getBankInfo2() {

        String result = "{\"billList\":[{\"month\":\"2019年06月\",\"data\":[{\"billTrsAmount\":\"+563.11\",\"createTime\":\"2019.06.26 09:43:39\",\"id\":\"19072609433949655469\",\"orderId\":\"19072609433949687930\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSH\",\"transId\":\"19072609433949672691\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"+42.19\",\"createTime\":\"2019.06.26 09:43:30\",\"id\":\"19072609433003595678\",\"orderId\":\"19072609433003569571\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSY\",\"transId\":\"19072609433003552146\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"+957.09\",\"createTime\":\"2019.06.26 09:43:26\",\"id\":\"19072609432699179108\",\"orderId\":\"19072609432699191193\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSH\",\"transId\":\"19072609432699185752\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"+569.25\",\"createTime\":\"2019.06.26 09:42:55\",\"id\":\"19072609425598430120\",\"orderId\":\"19072609425598466804\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSH\",\"transId\":\"19072609425598450990\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"-836.83\",\"createTime\":\"2019.06.02 16:40:20\",\"id\":\"19080216402005434133\",\"orderId\":\"19072011103093222222\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"CPDJ\",\"transId\":\"19080216402005491382\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"+42.34\",\"createTime\":\"2019.06.02 16:40:16\",\"id\":\"19080216401693271941\",\"orderId\":\"19072011103093222221\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"CPJD\",\"transId\":\"19080216401693245196\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"-10.00\",\"createTime\":\"2019.06.02 09:14:43\",\"id\":\"19062515525866744649\",\"orderId\":\"19071809293192341514\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"WTHI\",\"transId\":\"19071809293192317934\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"+77.00\",\"createTime\":\"2019.06.02 09:14:41\",\"id\":\"19062515525866744648\",\"orderId\":\"19071809293189078558\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"RCGI\",\"transId\":\"19071809293189072593\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"-77.00\",\"createTime\":\"2019.06.02 09:14:39\",\"id\":\"19062515525866744647\",\"orderId\":\"19071809293184873927\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"WTHI\",\"transId\":\"19071809293184846000\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"+85.00\",\"createTime\":\"2019.06.02 09:14:37\",\"id\":\"19062515525866744646\",\"orderId\":\"19071809293181459384\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"RCGI\",\"transId\":\"19071809293181492655\",\"withdradalNo\":\"3081\"}]}],\"flag\":\"true\",\"message\":\"返回成功\"}";

        return result;
    }

    public static String getBankInfo3() {

        String result = "{\"billList\":[{\"month\":\"2019年05月\",\"data\":[{\"billTrsAmount\":\"-914.80\",\"createTime\":\"2019.05.26 09:43:17\",\"id\":\"19072609431772331203\",\"orderId\":\"19072609431772399851\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSG\",\"transId\":\"19072609431772315420\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"-219.55\",\"createTime\":\"2019.05.26 09:43:14\",\"id\":\"19072609431467994806\",\"orderId\":\"19072609431467986980\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSG\",\"transId\":\"19072609431467961618\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"-194.84\",\"createTime\":\"2019.05.26 09:43:11\",\"id\":\"19072609431160267300\",\"orderId\":\"19072609431160291000\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSG\",\"transId\":\"19072609431160236991\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"-456.16\",\"createTime\":\"2019.05.26 09:43:02\",\"id\":\"19072609430240845579\",\"orderId\":\"19072609430240880414\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSG\",\"transId\":\"19072609430240874975\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"+442.09\",\"createTime\":\"2019.05.26 09:42:59\",\"id\":\"19072609425905658473\",\"orderId\":\"19072609425905658469\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSY\",\"transId\":\"19072609425905696074\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"-25.00\",\"createTime\":\"2019.05.02 09:14:51\",\"id\":\"19062515525896744147\",\"orderId\":\"19071809293214880435\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"WTHI\",\"transId\":\"19071809293214856349\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"-99.00\",\"createTime\":\"2019.05.02 09:14:49\",\"id\":\"19062515525866755646\",\"orderId\":\"19071809293210779317\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"WTHI\",\"transId\":\"19071809293210759585\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"-9.00\",\"createTime\":\"2019.05.02 09:14:47\",\"id\":\"19062515525866744949\",\"orderId\":\"19071809293204876232\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"WTHI\",\"transId\":\"19071809293204887391\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"-76.00\",\"createTime\":\"2019.05.02 09:14:45\",\"id\":\"19062515525866744748\",\"orderId\":\"19071809293200847217\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"WTHI\",\"transId\":\"19071809293200876855\",\"withdradalNo\":\"3081\"}]},{\"month\":\"2019年04月\",\"data\":[{\"billTrsAmount\":\"-125.36\",\"createTime\":\"2019.04.26 09:43:23\",\"id\":\"19072609432389692749\",\"orderId\":\"19072609432389648173\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSG\",\"transId\":\"19072609432389636084\",\"withdradalNo\":\"3081\"}]}],\"flag\":\"true\",\"message\":\"返回成功\"}";
        return result;
    }

    public static String getBankInfo4() {

        String result = "{\"billList\":[{\"month\":\"2019年03月\",\"data\":[{\"billTrsAmount\":\"-914.80\",\"createTime\":\"2019.05.26 09:43:17\",\"id\":\"19072609431772331203\",\"orderId\":\"19072609431772399851\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSG\",\"transId\":\"19072609431772315420\",\"withdradalNo\":\"3081\"},{\"billTrsAmount\":\"-219.55\",\"createTime\":\"2019.05.26 09:43:14\",\"id\":\"19072609431467994806\",\"orderId\":\"19072609431467986980\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSG\",\"transId\":\"19072609431467961618\",\"withdradalNo\":\"3081\"}]},{\"month\":\"2019年02月\",\"data\":[{\"billTrsAmount\":\"-125.36\",\"createTime\":\"2019.04.26 09:43:23\",\"id\":\"19072609432389692749\",\"orderId\":\"19072609432389648173\",\"payNo\":\"0721\",\"rtxnTypeCode\":\"XYSG\",\"transId\":\"19072609432389636084\",\"withdradalNo\":\"3081\"}]}],\"flag\":\"true\",\"message\":\"返回成功\"}";
//        String result = "{\"billList\":[],\"flag\":\"true\",\"message\":\"返回成功\"}";
        return result;
    }

    public static String getBankInfo0() {

        String result = "{\"billList\":[],\"flag\":\"true\",\"message\":\"返回成功\"}";
        return result;
    }
}
