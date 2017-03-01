package com.example.android.debtors.ItemListener;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.android.debtors.Fragments.FragmentPaymentsGiven;
import com.example.android.debtors.Fragments.FragmentPaymentsReceived;

/**
 * Created by admin on 01.03.2017.
 */

public class RecyclerOnScrollListener extends RecyclerView.OnScrollListener{

    private static final String TAG = RecyclerOnScrollListener.class.getSimpleName();

    public interface HideOrShowFab{
        void hideFab();
        void showFab();
    }
    private final HideOrShowFab mHideOrShowFab;
    private final RecyclerView.OnScrollListener onScrollListener;


    public RecyclerOnScrollListener(HideOrShowFab hideOrShowFab) {
        mHideOrShowFab = hideOrShowFab;

        onScrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(dy > 0 ) {
                    Log.i(TAG, "onScrolled: w dol");
                    mHideOrShowFab.hideFab();
                }
                else if(dy < 0) {
                    Log.i(TAG, "onScrolled: w gore");
                    mHideOrShowFab.showFab();
                }
//                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
//                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }


        };
    }
}
