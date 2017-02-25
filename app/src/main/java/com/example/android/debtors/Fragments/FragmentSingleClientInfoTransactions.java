package com.example.android.debtors.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Adapters.AdapterDebtors;
import com.example.android.debtors.Adapters.AdapterTransacation;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Model.Transaction;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 25.02.2017.
 */
public class FragmentSingleClientInfoTransactions extends Fragment {

    private List<TransactionForClient> listOfTransactionsForClient;
    private DatabaseTransactions dbTransactions;


    private static final String TAG = FragmentSingleClientInfoTransactions.class.getSimpleName();

    public FragmentSingleClientInfoTransactions() {
        Log.i(TAG, "FragmentDebtorsForMe: START");
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: start");
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate: end");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated: START");
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: END");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: START");
//        TODO make db is reading in another thread \/
//        TODO if listOfClients = null - zabezpieczyc, tak samo jak w innych fragmentach\/
        listOfTransactionsForClient = getTransactionsByClient();

//        fab = (FloatingActionButton)

        View rootView = inflater.inflate(R.layout.recycler_view_with_viewpager, container, false);
//        AdapterTransacation adapterTransactions = new AdapterTransacation();
//        AdapterDebtors adapterDebtors = new AdapterDebtors(fragmentActivity, listOfClients);
//        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_with_viewpager);
//        setupRecyclerView(recyclerView);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
//                if (dy > 0 ||dy<0 && fab.isShown())
//                    fab.hide();
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//
//                if (newState == RecyclerView.SCROLL_STATE_IDLE){
//                    fab.show();
//                }
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
        Log.i(TAG, "onCreateView: END");

//        recyclerView.setAdapter(adapterDebtors);

        return rootView;

    }

    private List<TransactionForClient> getTransactionsByClient() {

        List<TransactionForClient> listOfTransactions = new ArrayList<>();
        return listOfTransactions;
    }
}