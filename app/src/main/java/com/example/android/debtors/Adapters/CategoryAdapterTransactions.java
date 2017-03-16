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
        Log.i(TAG, "CategoryAdapterTransactions: 1");
        fragmentTransactionsAll = new FragmentTransactionsAll();
        fragmentTransactionsSales = new FragmentTransactionsSales();
        fragmentTransactionsPurchases = new FragmentTransactionsPurchases();
        Log.i(TAG, "CategoryAdapterTransactions: 2");
        MainActivity.subFragmentID = FragmentsIDs.TRANSACTIONSALL;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            MainActivity.CURRENT_SUB_TAG = "tagTransactionsAll";

            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.TRANSACTIONSALL;

            return fragmentTransactionsAll;
        }
        if (position == 1) {
            MainActivity.CURRENT_SUB_TAG = "tagTransactionsSales";

            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.TRANSACTIONSSALES;

            return fragmentTransactionsSales;
        }
        if (position == 2) {
            MainActivity.CURRENT_SUB_TAG = "tagTransactionsPurchases";

            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.TRANSACTIONSPURCHASES;

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
        Log.i(TAG, "showFAB: current sub tag: " + MainActivity.subFragmentID);

        switch (MainActivity.subFragmentID){

            case FragmentsIDs.TRANSACTIONSALL:

                Log.i(TAG, "showFAB: tagTransactionsAll");
                fragmentTransactionsAll.showFAB();
                break;

            case FragmentsIDs.TRANSACTIONSSALES:

                Log.i(TAG, "showFAB: tagTransactionsSales ");
                fragmentTransactionsSales.showFAB();
                break;

            case FragmentsIDs.TRANSACTIONSPURCHASES:

                Log.i(TAG, "showFAB: tagTransactionsPurchases");
                fragmentTransactionsPurchases.showFAB();
                break;

            default:
                Log.d(TAG, "showFAB() called");
                Log.e(TAG, "showFAB: ERROR, subFragmentID: " + MainActivity.subFragmentID);

        }
    }

    public void hideFAB() {
        Log.i(TAG, "hideFAB: current sub tag: " + MainActivity.subFragmentID);

        switch (MainActivity.subFragmentID){

            case FragmentsIDs.TRANSACTIONSALL:
                fragmentTransactionsAll.hideFAB();
                break;

            case FragmentsIDs.TRANSACTIONSSALES:
                fragmentTransactionsSales.hideFAB();
                break;

            case FragmentsIDs.TRANSACTIONSPURCHASES:
                fragmentTransactionsPurchases.hideFAB();
                break;

            default:
                Log.e(TAG, "showFAB: ERROR, subFragmentID: " + MainActivity.subFragmentID);

        }


    }

}
