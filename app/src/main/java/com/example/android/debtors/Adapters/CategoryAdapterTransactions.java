package com.example.android.debtors.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.debtors.Fragments.FragmentTransactionSales;
import com.example.android.debtors.Fragments.FragmentTransactionsPurchases;

/**
 * Created by Rafaello on 2017-02-21.
 */
public class CategoryAdapterTransactions extends FragmentPagerAdapter{

    public CategoryAdapterTransactions(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new FragmentTransactionSales();
        if(position == 1)
            return new FragmentTransactionsPurchases();

        return null;
    }

    public CharSequence getPageTitle(int position){
        if(position == 0){
            return "Sales";
        } else if (position == 1){
            return "Purchases";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
