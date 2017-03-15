package com.example.android.debtors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Adapters.AdapterPayment;
import com.example.android.debtors.Adapters.AdapterTransacation;
import com.example.android.debtors.Dialogs.DialogPayment;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.R;

/**
 * Created by admin on 10.03.2017.
 */

public class FragmentPaymentsAll extends Fragment {

    private static final String TAG = FragmentPaymentsAll.class.getSimpleName();

    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;

    View rootView = null;
    AdapterPayment adapterPayment = null;
    RecyclerView recyclerView = null;

    public FragmentPaymentsAll(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_payments_all, container, false);
        adapterPayment = new AdapterPayment(getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_payments_all_recycler);
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterPayment);

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

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_payments_all_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, "onClick: halo fab");
//                dodac domyslna wartosc zaleznie od czestotliwosci typu platnosci \/
                DialogPayment dialogPayment = new DialogPayment(fragmentActivity, true, new CallbackAddInDialog() {
                    @Override
                    public void reloadRecycler() {

                        Log.i(TAG, "reloadRecycler: ");
                        adapterPayment = new AdapterPayment(getContext());
                        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_payments_all_recycler);
                        recyclerView.setAdapter(adapterPayment);

                    }
                });
                Log.i(TAG, "onClick: 4");
                dialogPayment.show();
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
}
