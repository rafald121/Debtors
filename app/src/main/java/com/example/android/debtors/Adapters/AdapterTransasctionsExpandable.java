package com.example.android.debtors.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Transaction;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 13.03.2017.
 */

public class AdapterTransasctionsExpandable extends ExpandableRecyclerAdapter<AdapterTransactionParent, AdapterTransactonChild> {

    private static final String TAG = AdapterTransasctionsExpandable.class.getSimpleName();
    private DatabaseTransactions dbTransactions;
    private List<TransactionForClient> listOfTransactions;

    private Context context;
    private LayoutInflater mInflater;

    public AdapterTransasctionsExpandable(Context context, List transaction){
        super(context, transaction);
    }


    @Override
    public AdapterTransactionParent onCreateParentViewHolder(ViewGroup viewGroup) {
        View parent;
        parent = mInflater.inflate(R.layout.item_parent_transaction, viewGroup, false);
        return new AdapterTransactionParent(parent);
    }

    @Override
    public AdapterTransactonChild onCreateChildViewHolder(ViewGroup viewGroup) {
        View child;
        child = mInflater.inflate(R.layout.item_child_transactions, viewGroup, false);
        return new AdapterTransactonChild(child);
    }


    @Override
    public void onBindParentViewHolder(AdapterTransactionParent adapterTransactionParent, int i, Object o) {
        adapterTransactionParent.bind((TransactionForClient) o);
    }

    @Override
    public void onBindChildViewHolder(AdapterTransactonChild adapterTransactonChild, int i, Object o) {
        adapterTransactonChild.bind((TransactionForClient) o);

    }
////
//    private List<TransactionForClient> getListOfAllTransactions(){
//        DatabaseTransactions dbTransactions = new DatabaseTransactions(context);
//
//        List<TransactionForClient> list = dbTransactions.getAllTransactions();
//
//        return list;
//    }
//
//    private List<TransactionForClient> getListOfTransactionsByType(boolean purchaseOrSale){
//        DatabaseTransactions dbTransactions = new DatabaseTransactions(context);
//
//        List<TransactionForClient> list = dbTransactions.getTransactionsByType(purchaseOrSale);
//
//        for (Transaction t : list){
//            Log.i(TAG, "getListOfTransactionsSale: " + t.toString());
//        }
//
//        return list;
//    }
//
//    private Client getClientById(long ID){
//        DatabaseClients dbClients = new DatabaseClients(context);
//
//        Client client = dbClients.getClientByID(ID);
//
//        return client;
//    }
//
//    private List<Client> getListOfClients(){
//        DatabaseClients dbClients = new DatabaseClients(context);
//
//        List<Client> list = dbClients.getAllClient();
//
//        return list;
//    }
}
