package com.example.android.debtors.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by Rafaello on 2017-02-21.
 */

public class AdapterTransacation extends RecyclerView.Adapter<AdapterTransacation.MyViewHolder>{

    private static final String TAG = AdapterTransacation.class.getSimpleName();

    private DatabaseTransactions dbTransactions = null;
    private DatabaseClients dbClients = null;
    private List<TransactionForClient> listOfTransactions = new ArrayList<>();

    private Context context;

    public AdapterTransacation(Context context){
        this.context = context;
        this.listOfTransactions = getListOfAllTransactions();
    }


    public AdapterTransacation(Context context, boolean purchaseOrSale) {
        this.context = context;
        this.dbClients = new DatabaseClients(context);
        this.listOfTransactions = getListOfTransactionsByType(purchaseOrSale);
    }

    public AdapterTransacation(Context context, List<TransactionForClient> listOfTransactions){
        this.context = context;
        this.listOfTransactions = listOfTransactions;
    }

    public AdapterTransacation(Context context, long clientID) {
        this.context = context;
        this.dbTransactions = new DatabaseTransactions(context);
        this.listOfTransactions = dbTransactions.getTransactionFromClient(clientID);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TransactionForClient transaction = listOfTransactions.get(position);

        Client client =  getClientById(transaction.getTransactionClientID());

        String clientName = client.getClientName();

        holder.textViewClient.setText(clientName);
        holder.textViewTotalAmount.setText(String.valueOf(transaction.getTransactionProductValue()
                *transaction.getTransactionQuantity()));
        String[] datearray = transaction.getTransactionDate().split(" ");
        String date = datearray[0];
        holder.textViewDate.setText(date);
        if(transaction.isTransactionBuyOrSell())
            holder.textViewType.setText("Sale");
        else
            holder.textViewType.setText("Purchase");

        holder.textViewQuantity.setText(String.valueOf(transaction.getTransactionQuantity()));
        holder.textViewProductvalue.setText(String.valueOf(transaction.getTransactionProductValue()));
        holder.textViewEntryPayment.setText(String.valueOf(transaction.getTransactionEntryPayment()));



    }

    @Override
    public int getItemCount() {
        return listOfTransactions.size();
    }

    public void updateList(List<TransactionForClient> list){
        listOfTransactions.clear();
        listOfTransactions.addAll(list);
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewClient, textViewTotalAmount, textViewDate, textViewType,  textViewQuantity, textViewProductvalue, textViewEntryPayment;
        private ImageButton transactionItemEdit, transactionItemDelete;
        public MyViewHolder(View itemView) {
            super(itemView);

            textViewClient = (TextView) itemView.findViewById(R.id.transaction_item_client);
            textViewTotalAmount = (TextView) itemView.findViewById(R.id.transaction_item_totalamount);
            textViewDate = (TextView) itemView.findViewById(R.id.transaction_item_date);
            textViewType = (TextView) itemView.findViewById(R.id.transaction_item_type);
            textViewQuantity = (TextView) itemView.findViewById(R.id.transaction_item_quantity);
            textViewProductvalue = (TextView) itemView.findViewById(R.id.transaction_item_productvalue);
            textViewEntryPayment = (TextView) itemView.findViewById(R.id.transaction_item_entrypayment);
            transactionItemEdit = (ImageButton) itemView.findViewById(R.id.item_transaction_edit);
            transactionItemDelete = (ImageButton) itemView.findViewById(R.id.item_transaction_delete);


            itemView.setOnClickListener(this);
            transactionItemEdit.setOnClickListener(this);
            transactionItemDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            TransactionForClient clickedItem = listOfTransactions.get(getLayoutPosition());

            if(v.getId() == transactionItemEdit.getId()){
                //edit
            } else if (v.getId() == transactionItemDelete.getId()){
                Log.i(TAG, "onClick: delete");
                Log.i(TAG, "onClick: transaciton to delete: " + clickedItem.toString());
                deleteTransaction(clickedItem.getTransactionID());
                dbTransactions.deleteTransaction(clickedItem.getTransactionID());
//                notifyItemChanged(getLayoutPosition());
                listOfTransactions.remove(clickedItem);
                notifyDataSetChanged();

            } else {
                Log.i(TAG, "onClick: item view content");
            }
        }

        private void deleteTransaction(int id) {
            dbTransactions = new DatabaseTransactions(context);
            dbTransactions.deleteTransaction(id);

        }
    }

    private Client getClientById(long ID){
        DatabaseClients dbClients = new DatabaseClients(context);

        Client client = dbClients.getClientByID(ID);

        return client;
    }

    private List<TransactionForClient> getListOfAllTransactions(){
        DatabaseTransactions dbTransactions = new DatabaseTransactions(context);

        List<TransactionForClient> list = dbTransactions.getAllTransactions();

        return list;
    }

    private List<TransactionForClient> getListOfTransactionsByType(boolean purchaseOrSale){
        DatabaseTransactions dbTransactions = new DatabaseTransactions(context);

        List<TransactionForClient> list = dbTransactions.getTransactionsByType(purchaseOrSale);

        return list;
    }

}
