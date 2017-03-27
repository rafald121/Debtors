package com.example.android.debtors.Fragments;

import android.content.Context;
import android.net.Uri;
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
import com.example.android.debtors.Adapters.AdapterTransactionType;
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

public class FragmentTransactionsSales extends Fragment implements InterfaceViewPager{

    private static final String TAG = FragmentTransactionsSales.class.getSimpleName();

    private DatabaseTransactions dbTransactions = null;

    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;

    private View rootView = null;
    private AdapterTransactionType adapterTransactionType = null;

    private RecyclerView recyclerView = null;

    private List<TransactionForClient> listOfTransactions = null;

    public FragmentTransactionsSales() {
    }

    public static FragmentTransactionsSales newInstance() {
        FragmentTransactionsSales fragment = new FragmentTransactionsSales();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        listOfTransactions = getListOfTransactionsByType(true);

        rootView = inflater.inflate(R.layout.fragment_transactions_sales, container, false);
        adapterTransactionType = new AdapterTransactionType(getContext(), listOfTransactions);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_transactions_sales_recycler);
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterTransactionType);

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

                DialogTransaction dialogTransaction = new DialogTransaction(fragmentActivity, true, new CallbackAddInDialog() {
                    @Override
                    public void reloadRecycler() {
                        adapterTransactionType.updateList(getListOfTransactionsByType(true));
                    }
                });
                dialogTransaction.show();

//          showDialogTransactions();
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


    private void showDialogTransactions(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getChildFragmentManager().findFragmentByTag(FragmentsIDsAndTags.TAG_TRANSACTIONSSALES);

        if(prev!=null)
            ft.remove(prev);
        else
            Log.i(TAG, "showDialogTransactions: prev is not null");

        ft.addToBackStack(null);

        DialogFragment d = DialogTransactionTMP.newInstance(true);
        d.show(getChildFragmentManager(), FragmentsIDsAndTags.TAG_DIALOG_CREATE_TRASACTIONS);
    }


    @Override
    public void notifyWhenSwitched() {
        MainActivity.fragmentID = FragmentsIDsAndTags.TRANSACTIONSSALES;
        Log.i(TAG, "notifyWhenSwitched: sales");
        Log.i(TAG, "notifyWhenSwitched: subfragment: " + MainActivity.fragmentID);
        if (fab != null) {
            fab.show();
        }else
            Log.e(TAG, "notifyWhenSwitched: fab is null");
    }

    private List<TransactionForClient> getListOfTransactionsByType(boolean purchaseOrSale){
        dbTransactions = new DatabaseTransactions(fragmentActivity);

        List<TransactionForClient> list = dbTransactions.getTransactionsByType(purchaseOrSale);

        return list;
    }

    public void onEvent(DialogMenuTransactionsApplySalesOrPurchases dialogMenuTransactionsApplySalesOrPurchases){
        Log.i(TAG, "onEvent: hlao tukej SALES !!!!!??");
        dbTransactions = new DatabaseTransactions(fragmentActivity);
        if(dialogMenuTransactionsApplySalesOrPurchases.getTypeOfTransactions() == 1) {
            listOfTransactions = dbTransactions.getTransactionsByQueryInMenuDialog(dialogMenuTransactionsApplySalesOrPurchases.getFromDate(), dialogMenuTransactionsApplySalesOrPurchases.getToDate(), dialogMenuTransactionsApplySalesOrPurchases.getMinQuantity(), dialogMenuTransactionsApplySalesOrPurchases.getMaxQuantity(), dialogMenuTransactionsApplySalesOrPurchases.getMinTotalAmount(), dialogMenuTransactionsApplySalesOrPurchases.getMaxTotalAmount(), dialogMenuTransactionsApplySalesOrPurchases.getTypeOfTransactions());
            adapterTransactionType.updateList(listOfTransactions);
        } else
            Log.e(TAG, "onEvent: error typeOfTransactions in purchase on Event: " + dialogMenuTransactionsApplySalesOrPurchases.getTypeOfTransactions());
    }

}
