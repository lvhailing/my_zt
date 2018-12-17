package com.crecg.staffshield.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.fragment.FinfancingFragment;
import com.crecg.staffshield.fragment.FoundFragment;
import com.crecg.staffshield.fragment.MeFragment;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static android.content.ContentValues.TAG;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private SwipeRefreshLayout swipe_refresh;
    private ViewPager vp;

    private LinearLayout ll_tab_financing;
    private LinearLayout ll_tab_found;
    private LinearLayout ll_tab_me;

    private ImageView iv_tab_financing;
    private ImageView iv_tab_found;
    private ImageView iv_tab_me;

    private TextView tv_tab_financing;
    private TextView tv_tab_found;
    private TextView tv_tab_me;

    private ArrayList<Fragment> tabFragments;
    private FinfancingFragment financingFragment;
    private FoundFragment foundFragment;
    private MeFragment meFragment;
    private FragmentPagerAdapter fragmentAdapter;
    private int selectPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initJpush();
        initView();
        initVP();
        setSelect(selectPage);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
//            selectPage = getIntent().getIntExtra("selectIndex", 0);
//            setSelect(selectPage);
        }
    }

    private void initView() {
//        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        vp = (ViewPager) findViewById(R.id.vp);

        ll_tab_financing = (LinearLayout) findViewById(R.id.ll_tab_financing);
        ll_tab_found = (LinearLayout) findViewById(R.id.ll_tab_found);
        ll_tab_me = (LinearLayout) findViewById(R.id.ll_tab_me);

        iv_tab_financing = (ImageView) findViewById(R.id.iv_tab_financing);
        iv_tab_found = (ImageView) findViewById(R.id.iv_tab_found);
        iv_tab_me = (ImageView) findViewById(R.id.iv_tab_me);

        tv_tab_financing = (TextView) findViewById(R.id.tv_tab_financing);
        tv_tab_found = (TextView) findViewById(R.id.tv_tab_found);
        tv_tab_me = (TextView) findViewById(R.id.tv_tab_me);

        ll_tab_financing.setOnClickListener(this);
        ll_tab_found.setOnClickListener(this);
        ll_tab_me.setOnClickListener(this);
    }

    private void initVP() {
        tabFragments = new ArrayList<>();
        financingFragment = new FinfancingFragment();
        foundFragment = new FoundFragment();
        meFragment = new MeFragment();

        tabFragments.add(financingFragment);
        tabFragments.add(foundFragment);
        tabFragments.add(meFragment);

        fragmentAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                Log.i("aa","tabFragments个数是："+tabFragments.size());
                return tabFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return tabFragments.get(position);
            }
        };

        vp.setAdapter(fragmentAdapter);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                int currentItem = vp.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tab_financing: // 理财
                setSelect(0);
                break;
            case R.id.ll_tab_found: // 发现
                setSelect(1);
                break;
            case R.id.ll_tab_me: // 我的
                setSelect(2);
                break;
        }
    }

    public void setSelect(int i) {
//        if (PreferenceUtil.isLogin()) {
//            Log.i("hh", this + "判断当前用户是否登录：" + PreferenceUtil.isLogin());
//            if (i == 3) {
//                tab_mine.requestData();
//            }
//            if (i==0){
//                tab_home.requestAppData();
//            }
//        }
        setTab(i);
        vp.setCurrentItem(i);
    }

    private void setTab(int pos) {
        resetTvs();
        resetImages();

        switch (pos) {
            case 0:
                tv_tab_financing.setTextColor(getResources().getColor(R.color.financing_tv_slected_66000000));
                iv_tab_financing.setImageResource(R.mipmap.financing_tab_icon_pressed);
                break;
            case 1:
                tv_tab_found.setTextColor(getResources().getColor(R.color.financing_tv_slected_66000000));
                iv_tab_found.setImageResource(R.mipmap.found_tab_icon_pressed);
                break;
            case 2:
                tv_tab_me.setTextColor(getResources().getColor(R.color.financing_tv_slected_66000000));
                iv_tab_me.setImageResource(R.mipmap.me_tab_icon_pressed);
                break;

        }
    }

    // 改变底部tab文字颜色
    private void resetTvs() {
        tv_tab_financing.setTextColor(Color.parseColor("#999999"));
        tv_tab_found.setTextColor(Color.parseColor("#999999"));
        tv_tab_me.setTextColor(Color.parseColor("#999999"));
    }

    // 给底部tab设置 未选中时的背景图片
    private void resetImages() {
        iv_tab_financing.setImageResource(R.mipmap.financing_tab_icon_normal);
        iv_tab_found.setImageResource(R.mipmap.found_tab_icon_normal);
        iv_tab_me.setImageResource(R.mipmap.me_tab_icon_normal);
    }


    private void initJpush() {
        JPushInterface.init(getApplicationContext());
        JPushInterface.resumePush(getApplicationContext());
        Set<String> tagSet = new LinkedHashSet<String>();
        tagSet.add("djkfir");
        JPushInterface.setAliasAndTags(getApplicationContext(), null, tagSet, mTagsCallback);
    }

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("aaa", logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("aaa", logs);
            }

        }

    };

    private long lastTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    // 双击退出函数
    private void exitBy2Click() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime < 2000) {
            finish();
        } else {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            lastTime = currentTime;
        }
    }

}
