package com.crecg.crecglibrary.network;


import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.DataModel;
import com.crecg.crecglibrary.network.model.DataWrapper;

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
    Observable<CommonResultModel<DataWrapper<DataModel>>> getBookListByPost(@FieldMap Map<String, Object> params);

    //测试接口
    @POST("shop/mattersna/v1/getcreatestatus")
    Observable<CommonResultModel<DataWrapper<DataModel>>> canIEditReceipt();

    //测试接口
    @GET("toutiao/index?type=caijing&key=cf2e8c721799bbc8f3c9d639a4d0a9e6")
    Observable<CommonResultModel<DataWrapper<DataModel>>> getBookListByGet();

    //测试接口
    @GET("book/receipts/nav1/getgoodslist")
    Observable<CommonResultModel<DataWrapper<DataModel>>> getDeliveryReceiptDetail(@Query("receipt_id") String id, @Query("shop_id") String shopId);
/*************************************************************************************************************************************************/
    // 自己修改的接口
    @POST("toutiao/index")
    Observable<String> getZt(@FieldMap Map<String, Object> params);

    //注册一（手机号认证）
    @FormUrlEncoded
    @POST("android/regist")
    Observable<String> getRegisterOneStepByPost(@FieldMap Map<String, Object> param);

    //注册二（身份认证）
    @FormUrlEncoded
    @POST("android/registTwo")
    Observable<String> getRegisterTwoStepByPost(@FieldMap Map<String, Object> param);

    //登录
    @FormUrlEncoded
    @POST("android/login")
    Observable<String> getLoginByPost(@FieldMap Map<String, Object> param);

    //发送短信验证码
    @FormUrlEncoded
    @POST("userinfo/mobile/send/verifycode")
    Observable<String> getVerifyCodeByPost(@FieldMap Map<String, Object> param);

    //找回密码
    @FormUrlEncoded
    @POST("userinfo/pwd/retrieve")
    Observable<String> getFindPwdByPost(@FieldMap Map<String, Object> param);

    //修改密码
    @FormUrlEncoded
    @POST("userinfo/pwd/update")
    Observable<String> modifyPasswordByPost(@FieldMap Map<String, Object> param);

    //重置交易密码
    @FormUrlEncoded
    @POST("userinfo/trspwd/reset")
    Observable<String> resetTransactionPwdByPost(@FieldMap Map<String, Object> param);

    // 首页轮播图
    @FormUrlEncoded
    @POST("homePage/rotaryPlantingMap")
    Observable<String> getHomePicListByPost(@FieldMap Map<String, Object> param);

    // 首页数据&&定期理财列表数据
    @FormUrlEncoded
    @POST("homePage")
    Observable<String>requestHomeAndFinancialData(@FieldMap Map<String,Object>param);

    //快捷支付签约短信验证码获取 (未调试)
    @FormUrlEncoded
    @POST("userInfo/paySigned/send/verifyCode")
    Observable<String> getFastPaymentVerifyCode(@FieldMap Map<String, Object> param);

    //快捷支付签约（点下一步按钮调此接口） (未调试)
    @FormUrlEncoded
    @POST("userInfo/paySigned/submit")
    Observable<String> getFastPaymentSubmit(@FieldMap Map<String, Object> param);
}
