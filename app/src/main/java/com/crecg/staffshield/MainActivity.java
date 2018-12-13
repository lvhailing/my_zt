package com.crecg.staffshield;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv);
        imageView = findViewById(R.id.iv);

        getBooks("");
    }

    private void getBooks(String id) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("type", "caijing");
//        params.put("key", "cf2e8c721799bbc8f3c9d639a4d0a9e6");

        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
//                .getBookListByPost(params)
                .getBookListByGet()
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

                            Glide.with(MainActivity.this)
                                    .load(list.get(0).thumbnail_pic_s)
                                    .into(imageView);
                        }
                    }
                });
    }
}
