package com.example.android.debtors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.R;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created by admin on 25.02.2017.
 */
public class FragmentSingleClientInfoPayments extends Fragment {

    private static final String TAG = FragmentSingleClientInfoPayments.class.getSimpleName();
    private long clientsID;
    private List<Payment> listOfPayments;
    private DatabasePayments dbPayments;

    public FragmentSingleClientInfoPayments() {

    }

    public static Fragment newInstance(long clientID) {
        Log.i(TAG, "newInstance: start");
        FragmentSingleClientInfoPayments f = new FragmentSingleClientInfoPayments();
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
//        clientsID = getArguments().getLong("");

        Log.i(TAG, "onCreate: end");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: HALO? xD");
        Log.i(TAG, "onCreateView: clientsID ??: " + clientsID);
        listOfPayments = getPaymentsByClientId(clientsID);


        for( Payment p : listOfPayments ){
            Log.i(TAG, "onCreateView: payment : " + p.toString(true));
        }


        View rootView = inflater.inflate(R.layout.recycler_view_with_viewpager,container, false);
        AdapterClientInfo adapterClientInfo = new AdapterClientInfo(getContext());
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_with_viewpager);
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterClientInfo);
//        AdapterPayment adapterPayments = new AdapterPayment(getContext(),false);
//        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_with_viewpager);
//        setupRecyclerView(recyclerView);
//        recyclerView.setAdapter(adapterPayments);

//        } else
//            Log.e(TAG, "onCreateView: listOfpayment is null");

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
        Log.i(TAG, "onViewCreated: END");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private List<Payment> getPaymentsByClientId(long clientsID) {
        dbPayments = new DatabasePayments(getContext());

        List<Payment> listOfPayments = dbPayments.getPaymentsFromClient(clientsID);

        return listOfPayments;
    }


}
