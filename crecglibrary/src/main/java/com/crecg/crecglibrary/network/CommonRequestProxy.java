package com.crecg.crecglibrary.network;


import com.crecg.crecglibrary.network.model.DataModel;
import com.crecg.crecglibrary.network.model.DataWrapper;
import com.crecg.crecglibrary.network.model.LoginModel;
import com.crecg.crecglibrary.network.model.ResultModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 本类说明：通用网络接口
 */
public interface CommonRequestProxy {
    String BASE_URL = UrlRoot.ROOT_URL;

    //测试接口
    @FormUrlEncoded
    @POST("toutiao/index")
    Observable<ResultModel<DataWrapper<DataModel>>> getBookListByPost(@FieldMap Map<String, Object> params);

    //登录
    @FormUrlEncoded
    @POST("android/login")
    Observable<ResultModel<LoginModel>> getLoginByPost(@FieldMap Map<String, Object> params);

    //测试接口
    @POST("shop/mattersna/v1/getcreatestatus")
    Observable<ResultModel<DataWrapper<DataModel>>> canIEditReceipt();

    //测试接口
    @GET("toutiao/index?type=caijing&key=cf2e8c721799bbc8f3c9d639a4d0a9e6")
    Observable<ResultModel<DataWrapper<DataModel>>> getBookListByGet();

    //测试接口
    @GET("book/receipts/nav1/getgoodslist")
    Observable<ResultModel<DataWrapper<DataModel>>> getDeliveryReceiptDetail(@Query("receipt_id") String id, @Query("shop_id") String shopId);


    // 自己修改的接口
    @POST("toutiao/index")
    Observable<String> getZt(@FieldMap Map<String, Object> params);
}
