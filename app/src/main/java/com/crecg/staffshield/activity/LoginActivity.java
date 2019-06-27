package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.DataModel;
import com.crecg.crecglibrary.network.model.DataWrapper;
import com.crecg.crecglibrary.network.model.LoginModel;
import com.crecg.crecglibrary.network.model.ResultModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录
 * Created by hong on 2018/12/24.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_login_phone; // 手机号
    private EditText et_login_password; // 登录密码
    private TextView tv_login_forget_password; // 忘记密码
    private TextView tv_login_sign; // 新用户注册
    private Button btn_login; // 登录
    private ImageView iv_login_delete_phone; // 删除手机号
    private ImageView iv_login_delete_password; // 删除登录密码
    private String loginPhone;
    private String loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    protected void initView() {
        et_login_phone = findViewById(R.id.et_login_phone);
        et_login_password = findViewById(R.id.et_login_password);
        tv_login_forget_password = findViewById(R.id.tv_login_forget_password);
        tv_login_sign = findViewById(R.id.tv_login_sign);
        iv_login_delete_phone = findViewById(R.id.iv_fine_password_delete_phone);
        iv_login_delete_password = findViewById(R.id.iv_login_delete_password);
        btn_login = findViewById(R.id.btn_login);

        tv_login_forget_password.setOnClickListener(this);
        tv_login_sign.setOnClickListener(this);
        iv_login_delete_phone.setOnClickListener(this);
        iv_login_delete_password.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget_password: // 忘记密码
                break;
            case R.id.iv_fine_password_delete_phone: // 删除手机号
                break;
            case R.id.iv_login_delete_password: // 删除登录密码
                break;
            case R.id.btn_login: // 登录
                loginPhone = et_login_phone.getText().toString();
                loginPassword = et_login_password.getText().toString();
                getLoginByPost();
                break;
            case R.id.tv_login_sign: // 新用户注册
                break;
        }
    }

    /**
     *  登录
     */
    private void getLoginByPost() {
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", "13593262371");
        params.put("password", "aa111111");
        DESUtil.encMap(params);

//        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
//                .getLoginByPost(params)
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CommonObserverAdapter<String, LoginModel>() {
//            @Override
//            public void onMyError() {
//                //server取单据失败
//                ToastUtil.showCustom("登录失败");
//            }
//
//            @Override
//            public void onMySuccess(ResultModel<LoginModel> result) {
//                //server取单据成功
//                if (result != null && result.code != null && result.data != null) {
//                    LoginModel loginData = result.data;
//                    String userId = loginData.userId;
//                    if ("true".equals(loginData.flag)) {
//                        ToastUtil.showCustom("登录成功");
//                    }else{
//                        ToastUtil.showCustom(loginData.flag);
//                    }
//                }
//            }
//        });
    }
}
