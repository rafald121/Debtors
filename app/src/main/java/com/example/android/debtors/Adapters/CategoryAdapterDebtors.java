package com.example.android.debtors.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.debtors.Fragments.FragmentDebtorsForMe;
import com.example.android.debtors.Fragments.FragmentDebtorsMeToOther;

/**
 * Created by Rafaello on 2017-02-18.
 */

public class CategoryAdapterDebtors extends FragmentPagerAdapter {


    public CategoryAdapterDebtors(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new FragmentDebtorsForMe();
        if(position==1)
            return new FragmentDebtorsMeToOther();

        return null;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "For me";
        } else if (position == 1) {
            return "Me to other";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
