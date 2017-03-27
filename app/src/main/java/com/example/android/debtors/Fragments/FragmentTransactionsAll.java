package com.example.android.debtors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
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

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Adapters.AdapterTransacation;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Dialogs.DialogTransaction;
import com.example.android.debtors.Dialogs.DialogTransactionTMP;
import com.example.android.debtors.Enum.FragmentsIDsAndTags;
import com.example.android.debtors.EventBus.DialogMenuTransactionsApplyAll;
import com.example.android.debtors.EventBus.DialogMenuTransactionsApplySalesOrPurchases;
import com.example.android.debtors.EventBus.ToggleFabWhenDrawerMove;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by admin on 10.03.2017.
 */

public class FragmentTransactionsAll extends Fragment implements InterfaceViewPager{

    private static final String TAG = FragmentTransactionsAll.class.getSimpleName();

    private DatabaseTransactions dbTransactions = null;

    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;

    private View rootView = null;
    private AdapterTransacation adapterTransaction = null;
    private RecyclerView recyclerView = null;

    private List<TransactionForClient> listOfTransactions = null;

    public FragmentTransactionsAll(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        listOfTransactions = getListOfAllTransactions();

        rootView = inflater.inflate(R.layout.fragment_transactions_all, container, false);
        adapterTransaction = new AdapterTransacation(getContext(), listOfTransactions);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_transactions_all_recycler);
        setupRecyclerView(recyclerView);

        recyclerView.setAdapter(adapterTransaction);

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
        fab = (FloatingActionButton) view.findViewById(R.id.fragment_transactions_all_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogTransaction dialogTransaction = new DialogTransaction(fragmentActivity, true, new CallbackAddInDialog() {
                    @Override
                    public void reloadRecycler() {
                    adapterTransaction.updateList(getListOfAllTransactions());
                    }
                });
                dialogTransaction.show();
//                showDialogTransactions();

            }
        });
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

    private void showDialogTransactions(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getChildFragmentManager().findFragmentByTag(FragmentsIDsAndTags.TAG_TRANSACTIONSALL);

        if(prev!=null)
            ft.remove(prev);
        else
            Log.i(TAG, "showDialogTransactions: prev is not null");

        ft.addToBackStack(null);

        DialogFragment d = DialogTransactionTMP.newInstance();
        d.show(getChildFragmentManager(), FragmentsIDsAndTags.TAG_DIALOG_CREATE_TRASACTIONS);
    }

    @Override
    public void notifyWhenSwitched() {
        MainActivity.fragmentID = FragmentsIDsAndTags.TRANSACTIONSALL;
        Log.i(TAG, "notifyWhenSwitched: subfragment: " + MainActivity.fragmentID);
        fab.show();
    }

    public void onEvent(DialogMenuTransactionsApplyAll dialogMenuTransactionsApplyAll){

        dbTransactions = new DatabaseTransactions(fragmentActivity);
        listOfTransactions = dbTransactions.getTransactionsByQueryInMenuDialog(dialogMenuTransactionsApplyAll.getFromDate(), dialogMenuTransactionsApplyAll.getToDate(), dialogMenuTransactionsApplyAll.getMinQuantity(), dialogMenuTransactionsApplyAll.getMaxQuantity(), dialogMenuTransactionsApplyAll.getMinTotalAmount(), dialogMenuTransactionsApplyAll.getMaxTotalAmount());
        adapterTransaction.updateList(listOfTransactions);

    }

    private List<TransactionForClient> getListOfAllTransactions(){
        DatabaseTransactions dbTransactions = new DatabaseTransactions(fragmentActivity);

        List<TransactionForClient> list = dbTransactions.getAllTransactions();

        return list;
    }
}
