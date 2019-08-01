package com.crecg.staffshield.activity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 工资宝详情（加载H5）
 * Created by junde on 2019/7/4.
 */

public class SalaryTreasureDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_right_txt;
    private WebView webView;
    private Button btn_redeem;
    private Button btn_buy;
    private String btnFlag = "0"; // 0:默认选中买入   1：赎回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        baseSetContentView(R.layout.activity_salary_treasure_detail);
        initView();

    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        tv_right_txt = findViewById(R.id.tv_right_txt);
        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("我的工资宝");
        tv_right_txt.setVisibility(View.VISIBLE);
        tv_right_txt.setText("明细");

        webView = findViewById(R.id.web_view);
        btn_redeem = findViewById(R.id.btn_redeem);
        btn_buy = findViewById(R.id.btn_buy);

        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();

                super.onReceivedSslError(view, handler, error);

            }
        });
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setTextZoom(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
//        webView.addJavascriptInterface(new WebActivity.MyJavaScriptinterface(), "click");
        webView.loadUrl("https://www.baidu.com/");

        iv_back.setOnClickListener(this);
        tv_right_txt.setOnClickListener(this);
        btn_redeem.setOnClickListener(this);
        btn_buy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right_txt: // 明细
                intent = new Intent(SalaryTreasureDetailActivity.this, BillCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_redeem: // 赎回
                if ("0".equals(btnFlag)) {
                    btn_redeem.setBackground(getResources().getDrawable(R.drawable.shape_rect_btn_blue2));
                    btn_redeem.setTextColor(getResources().getColor(R.color.white));
                    btn_buy.setBackground(getResources().getDrawable(R.drawable.shape_rect_btn_white));
                    btn_buy.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
                    btnFlag = "1";
                }
                intent = new Intent(SalaryTreasureDetailActivity.this, WageTreasureRedemptionActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_buy: // 买入 (买入时需要判断是否开户，开户的话直接跳买入页，没开户的话需要先跳绑卡页开户再进买入页)
                if ("1".equals(btnFlag)) {
                    btn_buy.setBackground(getResources().getDrawable(R.drawable.shape_rect_btn_blue2));
                    btn_buy.setTextColor(getResources().getColor(R.color.white));
                    btn_redeem.setBackground(getResources().getDrawable(R.drawable.shape_rect_btn_white));
                    btn_redeem.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
                    btnFlag = "0";
                }
                intent = new Intent(SalaryTreasureDetailActivity.this, WageTreasureBuyingActivity.class);
                intent.putExtra("whereToEnterFlag", "2");
                startActivity(intent);
                break;
        }
    }
}
