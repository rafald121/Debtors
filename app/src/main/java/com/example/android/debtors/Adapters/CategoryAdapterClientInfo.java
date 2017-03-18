package com.example.android.debtors.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import com.example.android.debtors.Fragments.FragmentSingleClientInfo;
import com.example.android.debtors.Fragments.FragmentSingleClientInfoPayments;
import com.example.android.debtors.Fragments.FragmentSingleClientInfoTransactions;

/**
 * Created by admin on 25.02.2017.
 */

public class CategoryAdapterClientInfo extends FragmentPagerAdapter {

    private static final String TAG = CategoryAdapterClientInfo.class.getSimpleName();

    private long clientID;

    public CategoryAdapterClientInfo(FragmentManager fm) {
        super(fm);
    }

    public CategoryAdapterClientInfo(Long clientID, FragmentManager fm) {
        super(fm);
        this.clientID = clientID;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {

            Fragment fragmentSingleClientInfoPayments = FragmentSingleClientInfoPayments.newInstance(clientID);

            if (fragmentSingleClientInfoPayments != null) {
                return fragmentSingleClientInfoPayments;
            }

        } else if(position == 1) {

            Fragment fragmentSingleClientInfoTransactions = FragmentSingleClientInfoTransactions.newInstance(clientID);

            if (fragmentSingleClientInfoTransactions != null) {
                return fragmentSingleClientInfoTransactions;
            }

        }
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

}
