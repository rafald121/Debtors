package com.example.android.debtors.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.debtors.Fragments.FragmentTransactionsAll;
import com.example.android.debtors.Fragments.FragmentTransactionsSales;
import com.example.android.debtors.Fragments.FragmentTransactionsPurchases;

/**
 * Created by Rafaello on 2017-02-21.
 */
public class CategoryAdapterTransactions extends FragmentPagerAdapter {

    private FragmentTransactionsSales fragmentTransactionsSales;

    public CategoryAdapterTransactions(FragmentManager fm) {
        super(fm);
        fragmentTransactionsSales = new FragmentTransactionsSales();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new FragmentTransactionsAll();
        if (position == 1) {
            return fragmentTransactionsSales;
        }
        if (position == 2)
            return new FragmentTransactionsPurchases();
        return null;
    }

    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "All";
        } else if (position == 1) {
            return "Sales";
        } else if (position == 2) {
            return "Purchases";

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public void hideFAB() {
        if (fragmentTransactionsSales != null) {
            fragmentTransactionsSales.hideFAB();
        }

    }
}
