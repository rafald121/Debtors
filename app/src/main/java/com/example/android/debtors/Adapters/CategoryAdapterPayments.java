package com.example.android.debtors.Adapters;

import android.media.CamcorderProfile;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Enum.FragmentsIDs;
import com.example.android.debtors.Fragments.FragmentPaymentsAll;
import com.example.android.debtors.Fragments.FragmentPaymentsGiven;
import com.example.android.debtors.Fragments.FragmentPaymentsReceived;
import com.example.android.debtors.Interfaces.InterfaceViewPager;

/**
 * Created by Rafaello on 2017-02-21.
 */

public class CategoryAdapterPayments extends FragmentPagerAdapter {

    private static final String TAG = CategoryAdapterPayments.class.getSimpleName();
    private FragmentPaymentsAll fragmentPaymentsAll = null;
    private FragmentPaymentsReceived fragmentPaymentsReceived = null;
    private FragmentPaymentsGiven fragmentPaymentsGiven = null;


    public CategoryAdapterPayments(FragmentManager fm) {
        super(fm);
        fragmentPaymentsAll = new FragmentPaymentsAll();
        fragmentPaymentsReceived = new FragmentPaymentsReceived();
        fragmentPaymentsGiven = new FragmentPaymentsGiven();
        MainActivity.subFragmentID = FragmentsIDs.PAYMENTSALL;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.PAYMENTSALL;
            if (fragmentPaymentsAll != null) {
                return fragmentPaymentsAll;
            }
        }
        if(position == 1) {
            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.PAYMENTSRECEIVED;
            if (fragmentPaymentsReceived !=null) {
                return fragmentPaymentsReceived;
            }
        }
        if(position == 2) {
            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.PAYMENTSGIVEN;
            if (fragmentPaymentsGiven != null) {
                return fragmentPaymentsGiven;
            }
        }
        Log.e(TAG, "getItem: ERROR, return null");
        return null;
    }

    public CharSequence getPageTitle(int position){
        if(position == 0 ){
            return "All";
        } else if (position == 1){
            return "Received";
        } else if (position == 2){
            return "Given";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
