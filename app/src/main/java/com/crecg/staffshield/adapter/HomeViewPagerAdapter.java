package com.crecg.staffshield.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


import com.crecg.crecglibrary.base.BaseFragmentPagerAdapter;
import com.crecg.staffshield.common.CommonLazyFragment;

import java.util.List;

/**
 *    desc:主页界面 ViewPager + Fragment 适配器
 */
public final class HomeViewPagerAdapter extends BaseFragmentPagerAdapter<CommonLazyFragment> {

    public HomeViewPagerAdapter(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected void init(FragmentManager fm, List<CommonLazyFragment> list) {
//        list.add(TestFragmentA.newInstance());
//        list.add(TestFragmentB.newInstance());
//        list.add(TestFragmentC.newInstance());
//        list.add(TestFragmentD.newInstance());
    }
}