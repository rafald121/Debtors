package com.example.android.debtors.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Enum.FragmentsIDsAndTags;
import com.example.android.debtors.Fragments.FragmentTransactionsAll;
import com.example.android.debtors.Fragments.FragmentTransactionsSales;
import com.example.android.debtors.Fragments.FragmentTransactionsPurchases;

/**
 * Created by Rafaello on 2017-02-21.
 */
public class CategoryAdapterTransactions extends FragmentPagerAdapter {

    private static final String TAG = CategoryAdapterTransactions.class.getSimpleName();

    private FragmentTransactionsAll fragmentTransactionsAll = null;
    private FragmentTransactionsSales fragmentTransactionsSales = null;
    private FragmentTransactionsPurchases fragmentTransactionsPurchases = null;

    public CategoryAdapterTransactions(FragmentManager fm) {
        super(fm);
        fragmentTransactionsAll = new FragmentTransactionsAll();
        fragmentTransactionsSales = new FragmentTransactionsSales();
        fragmentTransactionsPurchases = new FragmentTransactionsPurchases();
        MainActivity.fragmentID = FragmentsIDsAndTags.TRANSACTIONSALL;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            MainActivity.previousFragmentID = MainActivity.fragmentID;
            MainActivity.fragmentID = FragmentsIDsAndTags.TRANSACTIONSALL;
            if (fragmentTransactionsAll != null) {
                return fragmentTransactionsAll;
            }
        }
        if (position == 1) {
            MainActivity.previousFragmentID = MainActivity.fragmentID;
            MainActivity.fragmentID = FragmentsIDsAndTags.TRANSACTIONSSALES;
            if (fragmentTransactionsSales != null) {
                return fragmentTransactionsSales;
            }
        }
        if (position == 2) {
            MainActivity.CURRENT_SUB_TAG = "tagTransactionsPurchases";

            MainActivity.previousFragmentID = MainActivity.fragmentID;
            MainActivity.fragmentID = FragmentsIDsAndTags.TRANSACTIONSPURCHASES;
            if (fragmentTransactionsPurchases != null) {
                return fragmentTransactionsPurchases;
            }
        }
        Log.e(TAG, "getItem: ERROR, return null");
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

}
