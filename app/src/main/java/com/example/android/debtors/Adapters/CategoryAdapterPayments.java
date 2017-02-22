package com.example.android.debtors.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.debtors.Fragments.FragmentPaymentsGiven;
import com.example.android.debtors.Fragments.FragmentPaymentsReceived;

/**
 * Created by Rafaello on 2017-02-21.
 */

public class CategoryAdapterPayments extends FragmentPagerAdapter {

    public CategoryAdapterPayments(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new FragmentPaymentsReceived();
        if(position == 1)
            return new FragmentPaymentsGiven();
        return null;
    }

    public CharSequence getPageTitle(int position){
        if(position == 0 ){
            return "Received";
        } else if (position == 1){
            return "Given";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }



}
