package com.example.android.debtors.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Enum.FragmentsIDs;
import com.example.android.debtors.Fragments.FragmentTransactionsAll;
import com.example.android.debtors.Fragments.FragmentTransactionsSales;
import com.example.android.debtors.Fragments.FragmentTransactionsPurchases;
import com.example.android.debtors.R;
import com.example.android.debtors.Utils.Utils;

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
        MainActivity.subFragmentID = FragmentsIDs.TRANSACTIONSALL;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.TRANSACTIONSALL;
            if (fragmentTransactionsAll != null) {
                return fragmentTransactionsAll;
            }
        }
        if (position == 1) {
            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.TRANSACTIONSSALES;
            if (fragmentTransactionsSales != null) {
                return fragmentTransactionsSales;
            }
        }
        if (position == 2) {
            MainActivity.CURRENT_SUB_TAG = "tagTransactionsPurchases";

            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.TRANSACTIONSPURCHASES;
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
