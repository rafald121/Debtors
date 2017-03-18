package com.example.android.debtors.Adapters;

import android.hardware.camera2.CameraDevice;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Enum.FragmentsIDs;
import com.example.android.debtors.Fragments.FragmentDebtors;
import com.example.android.debtors.Fragments.FragmentDebtorsForMe;
import com.example.android.debtors.Fragments.FragmentDebtorsMeToOther;

/**
 * Created by Rafaello on 2017-02-18.
 */

public class CategoryAdapterDebtors extends FragmentPagerAdapter {

    private static final String TAG = CategoryAdapterDebtors.class.getSimpleName();

    private FragmentDebtorsForMe fragmentDebtorsForMe = null;
    private FragmentDebtorsMeToOther fragmentDebtorsMeToOther = null;

    public CategoryAdapterDebtors(FragmentManager fm) {
        super(fm);
        Log.i(TAG, "CategoryAdapterDebtors: 1");
        fragmentDebtorsForMe = new FragmentDebtorsForMe();
        fragmentDebtorsMeToOther = new FragmentDebtorsMeToOther();
        Log.i(TAG, "CategoryAdapterDebtors: 2");
    }


    @Override
    public Fragment getItem(int position) {
        if(position==0) {
            MainActivity.previousSubFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.DEBTORSFORME;
            if (fragmentDebtorsForMe != null) {
                return fragmentDebtorsForMe;
            }
        }
        if(position==1) {

            MainActivity.previousFragmentID = MainActivity.subFragmentID;
            MainActivity.subFragmentID = FragmentsIDs.DEBTORSMETOOTHER;
            if (fragmentDebtorsMeToOther != null) {
                return fragmentDebtorsMeToOther;
            }
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "For me";
        } else if (position == 1) {
            return "Me to other";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }



}
