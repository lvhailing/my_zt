package com.crecg.staffshield.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.lzy.imagepicker.view.SystemBarTintManager;
import com.crecg.staffshield.R;
//import com.crecg.staffshield.network.HtmlRequest;
import com.crecg.staffshield.utils.ActivityStack;


public class WebActivity extends Activity implements View.OnClickListener {
    private WebView mWebview;
    private String type = null;
    private String url = null;
    public static final String WEB_TYPE_SALARY_TREASURE_DETAIL = "salary_treasure_detail";  // 工资宝详情
    public static final String WEB_TYPE_REGULAR_FINANCING_DETAIL = "regular_financing_detail";  // 定期理财详情


    public static final String WEBTYPE_PLAN_BOOK = "plan_book";            //计划书
    public static final String WEBTYPE_BUY = "buy";            //购买链接
    public static final String WEB_TYPE_NOTICE = "notice_detail "; // 公告详情/其他消息详情
    public static final String WEBTYPE_SERVICE_AGREEMENT = "service_agreement "; // 服务协议
    public static final String WEB_TYPE_SIGN_AGREEMENT = "sign_agreement "; // 国恒保险协议
    public static final String WEB_TYPE_ABOUT_US = "about_us "; // 关于我们
    public static final String WEB_TYPE_SING = "signup_web"; // 注册协议
    public static final String WEBTYPE_VERSION = "version "; // 版本号
    public static final String WEBTYPE_PROJECT_MATERIAL_DETAIL = "project_material_detail "; //项目材料

    public String title;
    private TextView tv_web_title; // 标题
    private ImageView iv_back; // 返回按钮
    private ImageView iv_btn_share; // 分享按钮
    private TextView tv_right_txt; // 标题右侧文字
    private LinearLayout ll_salary_treasure_btn; // 工资宝详情页买入与赎回布局
    private Button btn_redeem; // 赎回
    private Button btn_buy; // 买入
    private String btnFlag = "0"; // 0:默认选中买入   1：赎回
    private String productId; // 理财产品id

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web);

        type = getIntent().getStringExtra("type");
        url = getIntent().getStringExtra("url");

        initView();
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initView() {

        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

        mWebview = findViewById(R.id.web_view);
        tv_web_title = findViewById(R.id.tv_web_title);
        iv_back = findViewById(R.id.iv_back);
        tv_right_txt = findViewById(R.id.tv_right_txt);
        iv_btn_share = findViewById(R.id.iv_btn_share);
        ll_salary_treasure_btn = findViewById(R.id.ll_salary_treasure_btn);
        btn_redeem = findViewById(R.id.btn_redeem);
        btn_buy = findViewById(R.id.btn_buy);

        iv_back.setOnClickListener(this);
        iv_btn_share.setOnClickListener(this);
        tv_right_txt.setOnClickListener(this);
        btn_redeem.setOnClickListener(this);
        btn_buy.setOnClickListener(this);

        mWebview.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.setWebViewClient(new WebViewClient() {
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
        mWebview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setTextZoom(100);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.addJavascriptInterface(new MyJavaScriptinterface(), "NativeApp");

        if (type.equals(WEB_TYPE_SALARY_TREASURE_DETAIL)) {// 工资宝详情
            title = getIntent().getExtras().getString("title");
            tv_web_title.setText(title);
            tv_right_txt.setVisibility(View.VISIBLE);
            tv_right_txt.setText("明细");
            ll_salary_treasure_btn.setVisibility(View.VISIBLE);
        } else if (type.equals(WEB_TYPE_REGULAR_FINANCING_DETAIL)) { // 定期理财详情
            title = getIntent().getExtras().getString("title");
            tv_web_title.setText(title);
            productId = getIntent().getStringExtra("productId");
        }
// else if (type.equals(WEBTYPE_SERVICE_AGREEMENT)) {
//            tv_web_title.setText(getIntent().getExtras().getString("title"));
//        } else if (type.equals(WEB_TYPE_ABOUT_US)) { // 关于我们
//            tv_web_title.setText(getIntent().getExtras().getString("title"));
//
//        }else if (type.equals(WEB_TYPE_SING)) { // 注册协议
//            tv_web_title.setText(getIntent().getExtras().getString("title"));
//
//        } else if (type.equals(WEB_TYPE_SIGN_AGREEMENT)) { // 国恒保险协议
//            tv_web_title.setText(getIntent().getExtras().getString("title"));
//        } else if (type.equals(WEBTYPE_VERSION)) { // 版本号
//            tv_web_title.setText(getIntent().getExtras().getString("title"));
//        } else if (type.equals(WEBTYPE_PROJECT_MATERIAL_DETAIL)) {
//            tv_web_title.setText(getIntent().getExtras().getString("title"));
//
//        }else if (type.equals(WEBTYPE_PLAN_BOOK)) {//计划书
//            tv_web_title.setText(getIntent().getExtras().getString("title"));
//
//        }else if (type.equals(WEBTYPE_BUY)) {//购买链接
//            tv_web_title.setText(getIntent().getExtras().getString("title"));
//        }


//        HtmlRequest.synCookies(this, url);

        mWebview.loadUrl(url);

    }

    public class MyJavaScriptinterface {
        @JavascriptInterface
        public void result() {
            /*if (type.equals(WEBTYPE_WITHDRAW)) {
                setResult(RESULT_OK);
			} */
            WebActivity.this.finish();
        }

        @JavascriptInterface
        public void buyRegularFinacial() {
            if (type.equals(WEB_TYPE_REGULAR_FINANCING_DETAIL)) {
                Intent intent = new Intent(WebActivity.this, RegularFinancialManagementBuyingActivity.class);
                intent.putExtra("productId", productId);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right_txt: // (工资宝详情页头部右侧)明细
                intent = new Intent(WebActivity.this, BillCenterActivity.class);
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
                intent = new Intent(WebActivity.this, WageTreasureRedemptionActivity.class);
                intent.putExtra("whereToEnterFlag", "2");
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
                intent = new Intent(WebActivity.this, WageTreasureBuyingActivity.class);
                intent.putExtra("whereToEnterFlag", "2"); // 表示从工资宝详情页进
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mWebview.reload();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack stack = ActivityStack.getActivityManage();
        stack.removeActivity(this);
    }

}
