package com.example.android.debtors.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.debtors.Adapters.AdapterPayment;
import com.example.android.debtors.Adapters.AdapterPaymentType;
import com.example.android.debtors.Adapters.AdapterTransacation;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Dialogs.DialogPayment;
import com.example.android.debtors.EventBus.DialogMenuPaymentsApplyReceivedOrGiven;
import com.example.android.debtors.EventBus.ToggleFabWhenDrawerMove;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.R;

import java.util.List;

import de.greenrobot.event.EventBus;


public class FragmentPaymentsReceived extends Fragment implements InterfaceViewPager{

    private static final String TAG = FragmentPaymentsReceived.class.getSimpleName();

    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;

    private AdapterPaymentType adapterPaymentType = null;
    private View rootView = null;
    private RecyclerView recyclerView = null;

    private List<Payment> listOfPayments = null;
    private DatabasePayments dbPayment = null;

    public FragmentPaymentsReceived() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        listOfPayments = getListOfTransactionsByType(true);

        rootView = inflater.inflate(R.layout.fragment_payments_received, container, false);
        adapterPaymentType = new AdapterPaymentType(getContext(), listOfPayments);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_payments_received_recycler);
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterPaymentType);

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_payments_received_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "payments received ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                DialogPayment dialogPayment = new DialogPayment(fragmentActivity, true, new CallbackAddInDialog() {
                    @Override
                    public void reloadRecycler() {
                        adapterPaymentType.updateList(getListOfTransactionsByType(true));
//                        adapterPayment.notifyDataSetChanged();
//                        adapterPayment = new AdapterPayment(getContext(), true);
//                        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_payments_received_recycler);
////                        setupRecyclerView(recyclerView);
//                        recyclerView.setAdapter(adapterPayment);

//                        FragmentPaymentsReceived fragmentPaymentsReceived = new FragmentPaymentsReceived();
//
//                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                                android.R.anim.fade_out);
//                        fragmentTransaction.replace(R.id.fragment_payments_received_frame, fragmentPaymentsReceived);
//                        fragmentTransaction.commitAllowingStateLoss();

                    }
                });
                dialogPayment.show();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    public void onEvent(ToggleFabWhenDrawerMove toggleFabWhenDrawerMove){
        if(toggleFabWhenDrawerMove.isDirection())
            fab.show();
        else
            fab.hide();
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
        Log.i(TAG, "notifyWhenSwitched: received");
    }

    private List<Payment> getListOfTransactionsByType(boolean receivedOrGive) {
        DatabasePayments dbPayments = new DatabasePayments(fragmentActivity);

        List<Payment> list = dbPayments.getPaymentsByType(receivedOrGive);

        return list;
    }

    public void onEvent(DialogMenuPaymentsApplyReceivedOrGiven dialogMenuPaymentsApplyReceivedOrGiven) {
        dbPayment = new DatabasePayments(fragmentActivity);
        if (dialogMenuPaymentsApplyReceivedOrGiven.getType() == 1) {
            listOfPayments = dbPayment.getPaymentsByDateAndRange(dialogMenuPaymentsApplyReceivedOrGiven.getFromDate(), dialogMenuPaymentsApplyReceivedOrGiven.getToDate(), dialogMenuPaymentsApplyReceivedOrGiven.getMinRange(), dialogMenuPaymentsApplyReceivedOrGiven.getMaxRange(), dialogMenuPaymentsApplyReceivedOrGiven.getType());
            adapterPaymentType.updateList(listOfPayments);
        } else
            Log.e(TAG, "onEvent: czekam  bo oddaję RECEIVED :)");

    }
}
