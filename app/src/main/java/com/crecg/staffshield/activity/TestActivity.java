package com.crecg.staffshield.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.CrecgObserverAdapter;
import com.crecg.crecglibrary.network.model.DataModel;
import com.crecg.crecglibrary.network.model.DataWrapper;
import com.crecg.crecglibrary.network.model.ResultModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.staffshield.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class TestActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    private String MESSAGE_RECEIVED_ACTION = "com.crecg.staffshield.MESSAGE_RECEIVED_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.tv);
        imageView = findViewById(R.id.iv);

        getBooks("");

        registerMessageReceiver();

        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = intent.getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            ToastUtil.showCustom(title);
            ToastUtil.showCustom(content);
        }
    }

    private void getBooks(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "caijing");
        params.put("key", "cf2e8c721799bbc8f3c9d639a4d0a9e6");

        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getBookListByPost(params)
//                .getBookListByGet()   //get请求样例
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CrecgObserverAdapter<ResultModel<DataWrapper<DataModel>>>() {
                    @Override
                    public void onMyError(Throwable e) {
                        //server取单据失败
                        ToastUtil.showCustom("调接口失败");
                    }

                    @Override
                    public void onMySuccess(ResultModel<DataWrapper<DataModel>> result) {
                        //server取单据成功
                        if (result != null && result.result != null
                                && result.result.data != null && result.result.data.size() > 0) {
                            List<DataModel> list = result.result.data;

                            textView.setText(list.get(1).category);

                            Glide.with(TestActivity.this)
                                    .load(list.get(0).thumbnail_pic_s)
                                    .into(imageView);
                        }
                    }
                });
    }

    public void registerMessageReceiver() {
        MyMsgReceiver receiver = new MyMsgReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(receiver, filter);
    }

    public class MyMsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String msg = intent.getStringExtra("msg");
                String extras = intent.getStringExtra("extra");
                ToastUtil.showCustom(msg);
                ToastUtil.showCustom(extras);
                Log.i("aaa", "msg: " + msg);
                Log.i("aaa", "extra: " + extras);
            }
        }
    }
}