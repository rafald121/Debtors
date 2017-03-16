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

            return fragmentPaymentsAll;
        }
        if(position == 1) {
            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.PAYMENTSRECEIVED;

            return fragmentPaymentsReceived;
        }
        if(position == 2) {
            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.PAYMENTSGIVEN;

            return fragmentPaymentsGiven;
        }
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


    public void showFAB() {
        Log.i(TAG, "showFAB: current sub tag: " + MainActivity.subFragmentID);

        switch (MainActivity.subFragmentID){

            case FragmentsIDs.PAYMENTSALL:
                fragmentPaymentsAll.showFAB();
                break;

            case FragmentsIDs.PAYMENTSRECEIVED:
                fragmentPaymentsReceived.showFAB();
                break;

            case FragmentsIDs.PAYMENTSGIVEN:
                fragmentPaymentsGiven.showFAB();
                break;

            default:
                Log.d(TAG, "showFAB() called");
                Log.e(TAG, "showFAB: ERROR, subFragmentID: " + MainActivity.subFragmentID);

        }
    }

    public void hideFAB() {
        Log.i(TAG, "showFAB: current sub tag: " + MainActivity.subFragmentID);

        switch (MainActivity.subFragmentID){

            case FragmentsIDs.PAYMENTSALL:

                fragmentPaymentsAll.hideFAB();
                break;

            case FragmentsIDs.PAYMENTSRECEIVED:

                fragmentPaymentsReceived.hideFAB();
                break;

            case FragmentsIDs.PAYMENTSGIVEN:

                fragmentPaymentsGiven.hideFAB();
                break;

            default:
                Log.d(TAG, "showFAB() called");
                Log.e(TAG, "showFAB: ERROR, subFragmentID: " + MainActivity.subFragmentID);

        }

    }


}
