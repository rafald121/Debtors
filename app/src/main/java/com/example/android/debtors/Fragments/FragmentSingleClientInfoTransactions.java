package com.example.android.debtors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Adapters.AdapterDebtors;
import com.example.android.debtors.Adapters.AdapterTransacation;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.Model.Transaction;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 25.02.2017.
 */
public class FragmentSingleClientInfoTransactions extends Fragment {

    private static final String TAG = FragmentSingleClientInfoTransactions.class.getSimpleName();

    private List<TransactionForClient> listOfTransactionsForClient;
    private DatabaseTransactions dbTransactions;

    private long clientsID;

    private FragmentActivity fragmentActivity;


    public FragmentSingleClientInfoTransactions() {
        Log.i(TAG, "FragmentDebtorsForMe: START");
        // Required empty public constructor
    }

    public static Fragment newInstance(long clientID) {
        Log.i(TAG, "newInstance: start");
        FragmentSingleClientInfoTransactions f = new FragmentSingleClientInfoTransactions();
        f.clientsID = clientID;
//        Bundle args = new Bundle();
//
//        FragmentSingleClientInfoPayments fragment = new FragmentSingleClientInfoPayments();
//        fragment.setArguments(args);
//        return fragment;
        Log.i(TAG, "newInstance: before return ");
        return f;
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
        listOfTransactionsForClient = getTransactionsByClientId(clientsID);
        Log.i(TAG, "onCreateView: listOfTransactionsForClient: " + listOfTransactionsForClient.toString());
//        fab = (FloatingActionButton)

        View rootView = inflater.inflate(R.layout.recycler_view_with_viewpager, container, false);
        AdapterTransacation adapterTransactions = new AdapterTransacation(getContext(), listOfTransactionsForClient);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_with_viewpager);
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterTransactions);

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

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);//czy bedzie miala zmienny rozmiar podczas dzialania apki
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()
                .getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private List<TransactionForClient> getTransactionsByClient() {

        List<TransactionForClient> listOfTransactions = new ArrayList<>();
        return listOfTransactions;
    }

    @Override
    public void onAttach(Context context) {
        fragmentActivity = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private List<TransactionForClient> getTransactionsByClientId(long clientsID) {
        dbTransactions = new DatabaseTransactions(getContext());

        List<TransactionForClient> listofTransactions = dbTransactions.getTransactionFromClient(clientsID);

        return listofTransactions;
    }


}
