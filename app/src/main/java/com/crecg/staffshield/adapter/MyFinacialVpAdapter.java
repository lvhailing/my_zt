package com.crecg.staffshield.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.crecg.staffshield.fragment.MyFinacialHoldFragment;
import com.crecg.staffshield.fragment.MyFinacialPaymentReturnedFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * 我的理财列表  viewPager  Adapter类
 */

public class MyFinacialVpAdapter extends FragmentStatePagerAdapter {

    private final String[] mTitles;
    private List<Fragment> fragments = null;

    public MyFinacialVpAdapter(FragmentManager fm, String[] titles, Context context) {
        super(fm);
        mTitles = titles;

        initFragments();
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(MyFinacialHoldFragment.newInstance("持有中"));
        fragments.add(MyFinacialPaymentReturnedFragment.newInstance("已回款"));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) { //获取标题
        return mTitles[position];
    }
}
