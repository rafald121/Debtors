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

import com.example.android.debtors.Adapters.AdapterPayment;
//import com.example.android.debtors.ItemListener.RecyclerItemClickListener;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Dialogs.DialogPayment;
import com.example.android.debtors.Dialogs.DialogTransaction;
import com.example.android.debtors.EventBus.ToggleFabWhenDrawerMove;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
import com.example.android.debtors.ItemListener.RecyclerOnScrollListener;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.R;

import java.util.List;

import de.greenrobot.event.EventBus;

public class FragmentPaymentsGiven extends Fragment implements InterfaceViewPager{

    private static final String TAG = FragmentPaymentsGiven.class.getSimpleName();

    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;

    private AdapterPayment adapterPayment = null;
    private View rootView = null;
    private RecyclerView recyclerView = null;

    private List<Payment> listOfPayments = null;


    public FragmentPaymentsGiven() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        listOfPayments = getListOfTransactionsByType(false);

        rootView = inflater.inflate(R.layout.fragment_payments_given, container, false);
        adapterPayment = new AdapterPayment(getContext(), listOfPayments);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_payments_given_recycler);
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

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_payments_given_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "payments given ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Log.i(TAG, "onClick: halo fab");
                DialogPayment dialogPayment = new DialogPayment(fragmentActivity, false, new CallbackAddInDialog() {
                    @Override
                    public void reloadRecycler() {
                    adapterPayment.updateList(getListOfTransactionsByType(false));
//                        Log.i(TAG, "reloadRecycler: ");
////                        adapterPayment.notifyDataSetChanged();
//                        adapterPayment = new AdapterPayment(getContext(), false);
//                        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_payments_given_recycler);
////                        setupRecyclerView(recyclerView);
//                        recyclerView.setAdapter(adapterPayment);
////                        FragmentPaymentsGiven fragmentPaymentsGiven = new FragmentPaymentsGiven();
////
////                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
////                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
////                                android.R.anim.fade_out);
////                        Log.i(TAG, "reloadRecycler: 1");
////                        fragmentTransaction.replace(R.id.fragment_payments_given_frame, fragmentPaymentsGiven);
////                        Log.i(TAG, "reloadRecycler: 2");
////                        fragmentTransaction.commitAllowingStateLoss();
////                        Log.i(TAG, "reloadRecycler: 3");

                    }
                });
                Log.i(TAG, "onClick: 4");
                dialogPayment.show();
            }
        });
    }

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
        Log.i(TAG, "notifyWhenSwitched: given");
    }

    private List<Payment> getListOfTransactionsByType(boolean receivedOrGive) {
        DatabasePayments dbPayments = new DatabasePayments(fragmentActivity);

        List<Payment> list = dbPayments.getPaymentsByType(receivedOrGive);

        return list;
    }
}
