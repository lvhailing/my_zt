package com.crecg.staffshield.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.crecg.staffshield.fragment.BillCenterFragment;
import com.crecg.staffshield.fragment.BillCenterBankCardFragment;
import com.crecg.staffshield.fragment.BillCenterRegularFinancialFragment;
import com.crecg.staffshield.fragment.BillCenterSalaryTreasureFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 账单中心 VpAdapter
 * Created by junde on 2019/7/6.
 */

public class BillCenterVpAdapter extends FragmentStatePagerAdapter {

    private final String[] mTitles;
    private List<Fragment> fragments = null;

    public BillCenterVpAdapter(FragmentManager fm, String[] titles, Context context) {
        super(fm);
        mTitles = titles;

        initFragments();
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(BillCenterFragment.newInstance("全部"));
        fragments.add(BillCenterBankCardFragment.newInstance("银行卡"));
        fragments.add(BillCenterSalaryTreasureFragment.newInstance("工资宝"));
        fragments.add(BillCenterRegularFinancialFragment.newInstance("定期理财"));
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
    public CharSequence getPageTitle(int position) {  //获取标题
        return mTitles[position];

    }
}
