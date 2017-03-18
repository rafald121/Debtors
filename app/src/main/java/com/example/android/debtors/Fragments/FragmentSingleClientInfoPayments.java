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

import com.example.android.debtors.Adapters.AdapterClientInfo;
import com.example.android.debtors.Adapters.AdapterPayment;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Dialogs.DialogPayment;
import com.example.android.debtors.Dialogs.DialogTransaction;
import com.example.android.debtors.EventBus.ToggleFabWhenDrawerMove;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.R;

import java.util.List;
import java.util.concurrent.RecursiveAction;

import de.greenrobot.event.EventBus;

/**
 * Created by admin on 25.02.2017.
 */
public class FragmentSingleClientInfoPayments extends Fragment{

    private static final String TAG = FragmentSingleClientInfoPayments.class.getSimpleName();
    private long clientsID;
    private List<Payment> listOfPayments;
    private DatabasePayments dbPayments;

    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;


    public FragmentSingleClientInfoPayments() {

    }

    public static Fragment newInstance(long clientID) {
        Log.i(TAG, "newInstance: start");
        FragmentSingleClientInfoPayments f = new FragmentSingleClientInfoPayments();
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
        listOfPayments = getPaymentsByClientId(clientsID);

        for( Payment p : listOfPayments ){
            Log.i(TAG, "onCreateView: payment : " + p.toString(true));
        }

        View rootView = inflater.inflate(R.layout.fragment_singleclient_payments,container, false);
        AdapterClientInfo adapterClientInfo = new AdapterClientInfo(getContext(),listOfPayments);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_singleclient_payments_recycler);
        setupRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapterClientInfo);

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
        Log.i(TAG, "onViewCreated: START");
        super.onViewCreated(view, savedInstanceState);

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_singleclient_payments_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "payments given ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                DialogTransaction dialogTransaction = new DialogTransaction(fragmentActivity, true);
                dialogTransaction.show();
            }
        });

        Log.i(TAG, "onViewCreated: END");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;
        EventBus.getDefault().register(this); // this == your class instance

    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);

    }

    private List<Payment> getPaymentsByClientId(long clientsID) {
        dbPayments = new DatabasePayments(getContext());

        List<Payment> listOfPayments = dbPayments.getPaymentsFromClient(clientsID);

        return listOfPayments;
    }

    public void onEvent(ToggleFabWhenDrawerMove toggleFabWhenDrawerMove){
        if(toggleFabWhenDrawerMove.isDirection())
            fab.show();
        else
            fab.hide();
    }

}
