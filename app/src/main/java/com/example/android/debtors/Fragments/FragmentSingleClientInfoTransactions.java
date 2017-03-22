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

import com.example.android.debtors.Adapters.AdapterDebtors;
import com.example.android.debtors.Adapters.AdapterTransacation;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Dialogs.DialogPayment;
import com.example.android.debtors.Dialogs.DialogTransaction;
import com.example.android.debtors.EventBus.ToggleFabWhenDrawerMove;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.Model.Transaction;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by admin on 25.02.2017.
 */
public class FragmentSingleClientInfoTransactions extends Fragment {

    private static final String TAG = FragmentSingleClientInfoTransactions.class.getSimpleName();

    private List<TransactionForClient> listOfTransactionsForClient;
    private DatabaseTransactions dbTransactions;

    private long clientsID;

    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;

    private View rootView = null;
    private RecyclerView recyclerView = null;
    private AdapterTransacation adapterTransacation = null;

    private List<TransactionForClient> listofTransactions = null;

    public FragmentSingleClientInfoTransactions() {
        Log.i(TAG, "FragmentDebtorsForMe: START");
        // Required empty public constructor
    }

    public static Fragment newInstance(long clientID) {
        Log.i(TAG, "newInstance: start");
        FragmentSingleClientInfoTransactions f = new FragmentSingleClientInfoTransactions();
        f.clientsID = clientID;
        Log.i(TAG, "newInstance: before return ");
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: start");
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: end");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: START");
//        TODO make db is reading in another thread \/
//        TODO if listOfClients = null - zabezpieczyc, tak samo jak w innych fragmentach\/
        listOfTransactionsForClient = getTransactionsByClientId(clientsID);

        rootView = inflater.inflate(R.layout.fragment_singleclient_transactions, container, false);
        adapterTransacation = new AdapterTransacation(getContext(), listOfTransactionsForClient);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_singleclient_transactions_recycler);
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


        Log.i(TAG, "onCreateView: END");

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_singleclient_transactions_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogTransaction dialogTransaction = new DialogTransaction(fragmentActivity, clientsID, new CallbackAddInDialog() {
                    @Override
                    public void reloadRecycler() {
                        //TODO when transaction with payment, payment page isn't reload
                    adapterTransacation.updateList(getTransactionsByClientId(clientsID));
                    }
                });
                dialogTransaction.show();
            }
        });
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
        EventBus.getDefault().register(this); // this == your class instance

    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);

    }

    public void onEvent(ToggleFabWhenDrawerMove toggleFabWhenDrawerMove){
        if(toggleFabWhenDrawerMove.isDirection())
            fab.show();
        else
            fab.hide();
    }

    private List<TransactionForClient> getTransactionsByClientId(long clientsID) {
        dbTransactions = new DatabaseTransactions(getContext());

        List<TransactionForClient> listofTransactions = dbTransactions.getTransactionFromClient(clientsID);

        return listofTransactions;
    }


}
