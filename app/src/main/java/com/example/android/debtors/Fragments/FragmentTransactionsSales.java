package com.example.android.debtors.Fragments;

import android.content.Context;
import android.net.Uri;
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

import com.example.android.debtors.Adapters.AdapterTransacation;
import com.example.android.debtors.Dialogs.DialogTransaction;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
import com.example.android.debtors.R;

public class FragmentTransactionsSales extends Fragment implements InterfaceViewPager{


    private static final String TAG = FragmentTransactionsSales.class.getSimpleName();

    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;

    View rootView = null;
    AdapterTransacation adapterTransacation = null;
    RecyclerView recyclerView = null;

    public FragmentTransactionsSales() {
    }

    public static FragmentTransactionsSales newInstance() {
        FragmentTransactionsSales fragment = new FragmentTransactionsSales();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: sales");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: sales");
        rootView = inflater.inflate(R.layout.fragment_transactions_sales, container, false);
        adapterTransacation = new AdapterTransacation(getContext(), true);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_transactions_sales_recycler);
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterTransacation);

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

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_transactions_sales_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "transaction sales", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                DialogTransaction dialogTransaction = new DialogTransaction(fragmentActivity, true, new CallbackAddInDialog() {
                    @Override
                    public void reloadRecycler() {
                        adapterTransacation = new AdapterTransacation(getContext(), true);
                        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_transactions_sales_recycler);
                        recyclerView.setAdapter(adapterTransacation);
                    }
                });
                dialogTransaction.show();
            }
        });

    }

    public void onButtonPressed(Uri uri) {
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
        fab.show();
    }


    public void hideFAB(){
        fab.hide();
    }

    @Override
    public void notifyWhenSwitched() {
        Log.i(TAG, "notifyWhenSwitched: sales");
        fab.show();
    }
}
