package com.crecg.staffshield.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.HomeViewPagerAdapter;



public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private ViewPager vp_home_pager;
    private BottomNavigationView bv_home_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        vp_home_pager = findViewById(R.id.vp_home_pager);
        bv_home_navigation = findViewById(R.id.bv_home_navigation);

        vp_home_pager.setAdapter(new HomeViewPagerAdapter(this));

        // 限制页面数量
        vp_home_pager.setOffscreenPageLimit(3);
        vp_home_pager.addOnPageChangeListener(this);

        // 不使用图标默认变色
        bv_home_navigation.setItemIconTintList(null);
        bv_home_navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                bv_home_navigation.setSelectedItemId(R.id.home_financing);
                break;
            case 1:
                bv_home_navigation.setSelectedItemId(R.id.home_found);
                break;
            case 2:
                bv_home_navigation.setSelectedItemId(R.id.home_me);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_financing:
                vp_home_pager.setCurrentItem(0);
                return true;
            case R.id.home_found:
                vp_home_pager.setCurrentItem(1);
                return true;
            case R.id.home_me:
                vp_home_pager.setCurrentItem(3);
                return true;
        }
        return false;
    }
}
