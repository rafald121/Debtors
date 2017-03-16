package com.example.android.debtors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Adapters.AdapterTransacation;
import com.example.android.debtors.Dialogs.DialogTransaction;
import com.example.android.debtors.Enum.FragmentsIDs;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
import com.example.android.debtors.R;

/**
 * Created by admin on 10.03.2017.
 */

public class FragmentTransactionsAll extends Fragment implements InterfaceViewPager{

    private static final String TAG = FragmentTransactionsAll.class.getSimpleName();

    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;

    View rootView = null;
    AdapterTransacation adapterTransaction = null;
    RecyclerView recyclerView = null;

    public FragmentTransactionsAll(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_transactions_all, container, false);
        adapterTransaction = new AdapterTransacation(getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_transactions_all_recycler);
        setupRecyclerView(recyclerView);

        recyclerView.setAdapter(adapterTransaction);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0  && fab.isShown())
                    fab.hide();
                else if(dy<0 && !fab.isShown())
                    fab.show();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {


                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    Log.i(TAG, "onScrollStateChanged: ");
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return rootView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);//czy bedzie miala zmienny rozmiar podczas dzialania apki
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()
                .getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = (FloatingActionButton) view.findViewById(R.id.fragment_transactions_all_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "transaction purchases", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                DialogTransaction dialogTransaction = new DialogTransaction(fragmentActivity, true, new CallbackAddInDialog() {
                    @Override
                    public void reloadRecycler() {
//                       // zaleznei od ustawien albo domyslnie true albo false    \/
                        adapterTransaction = new AdapterTransacation(getContext(), true);
                        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_transactions_all_recycler);
                        recyclerView.setAdapter(adapterTransaction);
                    }
                });
                dialogTransaction.show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void showFAB() {
        if(!fab.isShown())
            fab.show();
        else
            Log.e(TAG, "showFAB: ");
    }


    public void hideFAB(){
        if(fab.isShown())
            fab.hide();
        else
            Log.e(TAG, "hideFAB: ");
    }

    @Override
    public void notifyWhenSwitched() {
        Log.i(TAG, "notifyWhenSwitched: halo");
        MainActivity.subFragmentID = FragmentsIDs.TRANSACTIONSALL;
        Log.i(TAG, "notifyWhenSwitched: all");
        Log.i(TAG, "notifyWhenSwitched: subfragment: " + MainActivity.subFragmentID);
        fab.show();
    }
}
