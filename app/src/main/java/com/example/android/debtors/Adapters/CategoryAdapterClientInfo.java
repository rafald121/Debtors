package com.example.android.debtors.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.example.android.debtors.Fragments.FragmentSingleClientInfoPayments;
import com.example.android.debtors.Fragments.FragmentSingleClientInfoTransactions;

/**
 * Created by admin on 25.02.2017.
 */

public class CategoryAdapterClientInfo extends FragmentPagerAdapter {

    public CategoryAdapterClientInfo(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new FragmentSingleClientInfoPayments();
        if(position == 1)
            return new FragmentSingleClientInfoTransactions();

        return null;
    }

    public CharSequence getPageTitle(int position){
        if (position ==0)
            return "Payments";
        else if(position == 1)
            return "Transactions";

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
