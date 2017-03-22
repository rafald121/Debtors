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

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Adapters.AdapterTransacation;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Dialogs.DialogTransaction;
import com.example.android.debtors.Enum.FragmentsIDsAndTags;
import com.example.android.debtors.EventBus.ToggleFabWhenDrawerMove;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.List;

import de.greenrobot.event.EventBus;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link FragmentTransactionsPurchases.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link FragmentTransactionsPurchases#newInstance} factory method to
// * create an instance of this fragment.
// */
public class FragmentTransactionsPurchases extends Fragment implements InterfaceViewPager{

    private static final String TAG = FragmentTransactionsPurchases.class.getSimpleName();

    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;

    private View rootView = null;
    private AdapterTransacation adapterTransacation = null;
    private RecyclerView recyclerView = null;

    private List<TransactionForClient> listOfTransactions = null;

    public FragmentTransactionsPurchases() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listOfTransactions = getListOfTransactionsByType(false);

        rootView = inflater.inflate(R.layout.fragment_transactions_purchases, container, false);
        adapterTransacation = new AdapterTransacation(getContext(),listOfTransactions);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_transactions_purchases_recycler);
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

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_transactions_purchases_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "transaction purchases", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                DialogTransaction dialogTransaction = new DialogTransaction(fragmentActivity, false, new CallbackAddInDialog() {
                    @Override
                    public void reloadRecycler() {
                    adapterTransacation.updateList(getListOfTransactionsByType(false));
//                        adapterTransacation = new AdapterTransacation(getContext(), false);
//                        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_transactions_purchases_recycler);
//                        recyclerView.setAdapter(adapterTransacation);
                    }
                });
                dialogTransaction.show();
            }
        });

        Log.i(TAG, "onViewCreated: END");
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
        Log.i(TAG, "onAttach: START");
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;
        Log.i(TAG, "onAttach: END");
        EventBus.getDefault().register(this); // this == your class instance

    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: START");
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

        MainActivity.subFragmentID = FragmentsIDsAndTags.TRANSACTIONSPURCHASES;

        Log.i(TAG, "notifyWhenSwitched: purchases");
        Log.i(TAG, "notifyWhenSwitched: subfragment: " + MainActivity.subFragmentID);

        if (fab != null) {
            fab.show();
        }  else
            Log.e(TAG, "notifyWhenSwitched: fab is null!");

        Log.i(TAG, "notifyWhenSwitched: eee?");
    }

    private List<TransactionForClient> getListOfTransactionsByType(boolean purchaseOrSale){
        DatabaseTransactions dbTransactions = new DatabaseTransactions(fragmentActivity);

        List<TransactionForClient> list = dbTransactions.getTransactionsByType(purchaseOrSale);

        return list;
    }

}
