package com.crecg.crecglibrary.network;


import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.DataModel;
import com.crecg.crecglibrary.network.model.DataWrapper;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
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

    // 自己修改的接口
    @POST("toutiao/index")
    Observable<String> getZt(@FieldMap Map<String, Object> params);
/*************************************************************************************************************************************************/

    //注册一（手机号认证）2.6
    @FormUrlEncoded
    @POST("android/regist")
    Observable<String> getRegisterOneStepByPost(@FieldMap Map<String, Object> param);

    //注册二（身份认证）2.7
    @FormUrlEncoded
    @POST("android/registTwo")
    Observable<String> getRegisterTwoStepByPost(@FieldMap Map<String, Object> param);

    //登录 2.5
    @FormUrlEncoded
    @POST("android/login")
    Observable<String> getLoginByPost(@FieldMap Map<String, Object> param);

    //发送短信验证码 2.1
    @FormUrlEncoded
    @POST("userinfo/mobile/send/verifycode")
    Observable<String> getVerifyCodeByPost(@FieldMap Map<String, Object> param);

    //找回密码 2.2
    @FormUrlEncoded
    @POST("userinfo/pwd/retrieve")
    Observable<String> getFindPwdByPost(@FieldMap Map<String, Object> param);

    //修改密码 2.3
    @FormUrlEncoded
    @POST("userinfo/pwd/update")
    Observable<String> modifyPasswordByPost(@FieldMap Map<String, Object> param);

    //重置交易密码 2.4
    @FormUrlEncoded
    @POST("userinfo/trspwd/reset")
    Observable<String> resetTransactionPwdByPost(@FieldMap Map<String, Object> param);

    // 首页轮播图 6.1.1
    @FormUrlEncoded
    @POST("homePage/rotaryPlantingMap")
    Observable<String> getHomePicListByPost(@FieldMap Map<String, Object> param);

    // 首页数据&&定期理财列表数据 6.1.2
    @FormUrlEncoded
    @POST("homePage")
    Observable<String>requestHomeAndFinancialData(@FieldMap Map<String,Object>param);

    //快捷支付签约短信验证码获取 3.3 (未调试)
    @FormUrlEncoded
    @POST("userInfo/paySigned/send/verifyCode")
    Observable<String> getFastPaymentVerifyCode(@FieldMap Map<String, Object> param);

    //快捷支付签约（点下一步按钮调此接口）3.4 (未调试)
    @FormUrlEncoded
    @POST("userInfo/paySigned/submit")
    Observable<String> getFastPaymentSubmit(@FieldMap Map<String, Object> param);

    //绑定银行卡接口(注册电子账户) 3.1.1
    @FormUrlEncoded
    @POST("balance/appBindCard")
    Observable<String> addBankCardData(@FieldMap Map<String, Object> param);

    //实名认证接口(身份证 正反面认证) 3.1.2
    @FormUrlEncoded
    @POST("balance/eleAccountAut")
    Observable<String> getAuthenticationData(@FieldMap Map<String, Object> param);

    //我的理财接口 6.7
    @FormUrlEncoded
    @POST("userBalance/myProductTender")
    Observable<String> getMyFinancialListData(@FieldMap Map<String, Object> param);

    //我的模块接口 2.9
    @FormUrlEncoded
    @POST("myBalance/myEAccount")
    Observable<String> getMyData(@FieldMap Map<String, Object> param);

    //账单中心接口 6.8
    @FormUrlEncoded
    @POST("type/billCenterList")
    Observable<String> getBillCenterListData(@FieldMap Map<String, Object> param);

    //上传图片 (参数加密，返回数据也加密) 3.1.4
    @FormUrlEncoded
    @POST("android/account/photo/upload")
    Observable<String> uploadImage(@FieldMap Map<String, Object> param);

    //上传图片(入参和返回数据都未加密)
    @FormUrlEncoded
    @POST("android/account/photo/upload")
    Observable<String> uploadImage1(@Field("photo") String photo, @Field("id") String userId, @Field("name") String name, @Field("photoType") String photoType);

    // 获取保险列表数据 2.1
    @FormUrlEncoded
    @POST("myInsuranceOrder/list/get")
    Observable<String>getInsuranceListData(@FieldMap Map<String, Object> param);

    // 银行卡充值和提现详情接口 4.2
    @FormUrlEncoded
    @POST("userInfo/transaction/order/detail")
    Observable<String>getRechargeWithdrawalData(@FieldMap Map<String, Object> param);

    // (从账单中心跳转详情)工资宝-资金转入详情接口 5.4
    @FormUrlEncoded
    @POST("userBalance/fundTransferRoll")
    Observable<String>getFundDetailData(@FieldMap Map<String, Object> param);

    // (从账单中心跳转详情)理财产品详情接口 7.3
    @FormUrlEncoded
    @POST("userBalance/fundTransferRollInfo")
    Observable<String>getFinancialDetailData(@FieldMap Map<String, Object> param);

    // 用户持仓信息 5.1
    @FormUrlEncoded
    @POST("userBalance/goTrsBalanceTime")
    Observable<String>getAccountInfoData(@FieldMap Map<String, Object> param);

    // 转入工资宝5.2
    @FormUrlEncoded
    @POST("userBalance/TransferFundIn")
    Observable<String>getBuyFundData(@FieldMap Map<String, Object> param);

    // 转出工资宝 5.3
    @FormUrlEncoded
    @POST("userBalance/payrollTransfer")
    Observable<String>getFundData(@FieldMap Map<String, Object> param);
}
