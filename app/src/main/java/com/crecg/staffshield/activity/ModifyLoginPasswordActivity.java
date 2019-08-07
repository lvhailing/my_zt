package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.ReturnOnlyTrueOrFalseModel;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 修改登录密码
 * Created by junde on 2018/12/25.
 */

public class ModifyLoginPasswordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_common_title;
    private EditText et_old_password;
    private EditText et_new_password;
    private EditText et_confirm_password;
    private ImageView iv_delete_new_password; // 删除新密码
    private ImageView iv_delete_old_password; // 删除旧密码
    private ImageView iv_delete_confirm_password; // 删除确认密码
    private Button btn_sure; // 确定
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_login_password);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        iv_back = findViewById(R.id.iv_back);
        tv_common_title.setText(getResources().getString(R.string.title_modify_login_password));

        et_old_password = findViewById(R.id.et_old_password);
        et_new_password = findViewById(R.id.et_new_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        iv_delete_new_password = findViewById(R.id.iv_delete_new_password);
        iv_delete_old_password = findViewById(R.id.iv_delete_old_password);
        iv_delete_confirm_password = findViewById(R.id.iv_delete_confirm_password);
        btn_sure = findViewById(R.id.btn_sure);


        iv_back.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_sure:
                oldPassword = et_old_password.getText().toString().trim();
                newPassword = et_new_password.getText().toString().trim();
                confirmPassword = et_confirm_password.getText().toString().trim();
                if (TextUtils.isEmpty(oldPassword)) {
                    ToastUtil.showCustom("请输入旧密码");
                    return;
                }
                if (TextUtils.isEmpty(newPassword)) {
                    ToastUtil.showCustom("请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    ToastUtil.showCustom("请输入确认密码");
                    return;
                }
                if (!StringUtil.checkPassword(oldPassword)) {
                    ToastUtil.showCustom("请输入6至8位字母数字组合密码");
                    return;
                }
                if (!StringUtil.checkPassword(newPassword)) {
                    ToastUtil.showCustom("请输入6至8位字母数字组合密码");
                    return;
                }
                if (!newPassword.equals(confirmPassword)) {
                    ToastUtil.showCustom("两次密码输入不一致，请重新输入");
                    return;
                }
                modifyPasswordByPost();
                break;
        }


    }

    /**
     *  重新修改密码
     */
    private void modifyPasswordByPost() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("oldPassword", oldPassword);
        param.put("newPassword",newPassword );
        String data = DESUtil.encMap(param);
//        Log.i("hh", "手机号与用户Id:" + userPhone + "---" + userId);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .modifyPasswordByPost(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String, ReturnOnlyTrueOrFalseModel>() {
                    @Override
                    public void onMyError() {
                        ToastUtil.showCustom("获取数据失败");
                    }

                    @Override
                    public void onMySuccess(String result) {
                        if (result == null) {
                            return;
                        }
                        CommonResultModel<ReturnOnlyTrueOrFalseModel> verifyCodeModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<ReturnOnlyTrueOrFalseModel>>() {
                        }.getType());
                        if (verifyCodeModel.data == null) {
                            return;
                        }
                        if (Boolean.parseBoolean(verifyCodeModel.data.flag)) {
                            ToastUtil.showCustom(verifyCodeModel.data.message);
                            finish();
                            Intent intent = new Intent(ModifyLoginPasswordActivity.this, LoginActivity.class);
//                            intent.putExtra("GOTOMAIN", LoginActivity.GOTOMAIN);
                            startActivity(intent);
                        }else {
                            ToastUtil.showCustom(verifyCodeModel.data.message);
                        }
                    }
                });
    }
}
