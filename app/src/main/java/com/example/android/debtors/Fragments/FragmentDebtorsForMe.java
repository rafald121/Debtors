package com.example.android.debtors.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.R;

/**
 * Created by Rafaello on 2017-02-18.
 */
public class FragmentDebtorsForMe extends Fragment{
    
    private static final String TAG = FragmentDebtorsForMe.class.getSimpleName();
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: START");
        View rootView = inflater.inflate(R.layout.recycler, container, false);
        Log.i(TAG, "onCreateView: END");
        return rootView;

    }

   
}
