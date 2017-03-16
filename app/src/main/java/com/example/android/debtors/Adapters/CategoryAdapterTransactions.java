package com.example.android.debtors.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Fragments.FragmentTransactionsAll;
import com.example.android.debtors.Fragments.FragmentTransactionsSales;
import com.example.android.debtors.Fragments.FragmentTransactionsPurchases;

/**
 * Created by Rafaello on 2017-02-21.
 */
public class CategoryAdapterTransactions extends FragmentPagerAdapter {

    private static final String TAG = CategoryAdapterTransactions.class.getSimpleName();

    private FragmentTransactionsAll fragmentTransactionsAll;
    private FragmentTransactionsSales fragmentTransactionsSales;
    private FragmentTransactionsPurchases fragmentTransactionsPurchases;

    public CategoryAdapterTransactions(FragmentManager fm) {
        super(fm);
        fragmentTransactionsAll = new FragmentTransactionsAll();
        fragmentTransactionsSales = new FragmentTransactionsSales();
        fragmentTransactionsPurchases = new FragmentTransactionsPurchases();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            MainActivity.CURRENT_SUB_TAG = "tagTransactionsAll";
            return fragmentTransactionsAll;
        }
        if (position == 1) {
            MainActivity.CURRENT_SUB_TAG = "tagTransactionsSales";
            return fragmentTransactionsSales;
        }
        if (position == 2) {
            MainActivity.CURRENT_SUB_TAG = "tagTransactionsPurchases";
            return fragmentTransactionsPurchases;
        }
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


    public void showFAB() {
        Log.i(TAG, "showFAB: current sub tag: " + MainActivity.CURRENT_SUB_TAG);
        switch (MainActivity.CURRENT_SUB_TAG){
            case "tagTransactionsAll":
                Log.i(TAG, "showFAB: tagTransactionsAll");
                fragmentTransactionsAll.showFAB();
                break;
            case "tagTransactionsSales":
                Log.i(TAG, "showFAB: tagTransactionsSales ");
                fragmentTransactionsSales.showFAB();
                break;
            case "tagTransactionsPurchases":
                Log.i(TAG, "showFAB: tagTransactionsPurchases");
                fragmentTransactionsPurchases.showFAB();
                break;
            default:
                Log.e(TAG, "showFAB: ERROR");

        }
    }

    public void hideFAB() {
        switch (MainActivity.CURRENT_SUB_TAG){
            case "tagTransactionsAll":
                fragmentTransactionsAll.hideFAB();
                break;
            case "tagTransactionsSales":
                fragmentTransactionsSales.hideFAB();
                break;
            case "tagTransactionsPurchases":
                fragmentTransactionsPurchases.hideFAB();
                break;
            default:
                Log.e(TAG, "hideFAB: ERROR");

        }
//        if (fragmentTransactionsSales != null) {
//            fragmentTransactionsSales.hideFAB();
//        }

    }
}
