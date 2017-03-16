package com.example.android.debtors.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Transaction;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-21.
 */

public class AdapterTransacation extends RecyclerView.Adapter<AdapterTransacation.MyViewHolder>{

    private static final String TAG = AdapterTransacation.class.getSimpleName();

    private DatabaseTransactions dbTransactions;
    private DatabaseClients dbClients;
    private List<TransactionForClient> listOfTransactions = new ArrayList<>();

    private Context context;

    public AdapterTransacation(Context context){
        this.context = context;
        listOfTransactions = getListOfAllTransactions();
    }

    public AdapterTransacation(List<TransactionForClient> listOfTransactions) {
//        Log.i(TAG, "AdapterTransacation: constructor");
        this.listOfTransactions = listOfTransactions;
    }

    public AdapterTransacation(Context context, boolean purchaseOrSale) {
//        Log.i(TAG, "AdapterTransacation: START, constructor created ");
        this.context = context;
        dbClients = new DatabaseClients(context);
        listOfTransactions = getListOfTransactionsByType(purchaseOrSale);
    }

    public AdapterTransacation(Context context, List<TransactionForClient> listOfTransactions){
        this.context = context;
        this.listOfTransactions = listOfTransactions;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.i(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_transaction, parent,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TransactionForClient transaction = listOfTransactions.get(position);
//        Log.i(TAG, "onBindViewHolder: halo: " + transaction.toString());
//        Log.i(TAG, "onBindViewHolder: client ID" + transaction.getTransactionClientID());

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


    }

    @Override
    public int getItemCount() {
        return listOfTransactions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewClient, textViewTotalAmount, textViewDate, textViewType;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewClient = (TextView) itemView.findViewById(R.id.transaction_item_client);
            textViewTotalAmount = (TextView) itemView.findViewById(R.id.transaction_item_totalamount);
            textViewDate = (TextView) itemView.findViewById(R.id.transaction_item_date);
            textViewType = (TextView) itemView.findViewById(R.id.transaction_item_type);
        }
    }

    private List<TransactionForClient> getListOfAllTransactions(){
        DatabaseTransactions dbTransactions = new DatabaseTransactions(context);

        List<TransactionForClient> list = dbTransactions.getAllTransactions();

        return list;
    }

    private List<TransactionForClient> getListOfTransactionsByType(boolean purchaseOrSale){
        DatabaseTransactions dbTransactions = new DatabaseTransactions(context);

        List<TransactionForClient> list = dbTransactions.getTransactionsByType(purchaseOrSale);

//        for (Transaction t : list){
//            Log.i(TAG, "getListOfTransactionsSale: " + t.toString());
//        }

        return list;
    }

    private Client getClientById(long ID){
        DatabaseClients dbClients = new DatabaseClients(context);

        Client client = dbClients.getClientByID(ID);

        return client;
    }

    private List<Client> getListOfClients(){
        DatabaseClients dbClients = new DatabaseClients(context);

        List<Client> list = dbClients.getAllClient();

        return list;
    }
}
