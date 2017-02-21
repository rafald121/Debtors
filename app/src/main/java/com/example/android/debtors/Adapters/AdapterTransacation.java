package com.example.android.debtors.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-21.
 */

public class AdapterTransacation extends RecyclerView.Adapter<AdapterTransacation.MyViewHolder>{

    List<TransactionForClient> listOfTransactions = new ArrayList<>();
    List<Client> listOfClients = new ArrayList<>();

    public AdapterTransacation(List<TransactionForClient> listOfTransactions, List<Client> listOfClients) {
        this.listOfTransactions = listOfTransactions;
        this.listOfClients = listOfClients;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transacations, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TransactionForClient transaction = listOfTransactions.get(position);

        String clientName = listOfClients.get(transaction.getTransactionClientID()).getClientName();

        holder.textViewClient.setText(clientName);
        holder.textViewQuantity.setText(String.valueOf(transaction.getTransactionQuantity()));
        holder.textViewValue.setText(String.valueOf(transaction.getProductValue()));
        holder.textViewDate.setText(transaction.getTransactionDate());
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

        TextView textViewClient, textViewQuantity, textViewValue, textViewDate, textViewType;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewClient = (TextView) itemView.findViewById(R.id.transaction_item_client);
            textViewQuantity = (TextView) itemView.findViewById(R.id.transaction_item_quantity);
            textViewValue = (TextView) itemView.findViewById(R.id.transaction_item_value);
            textViewDate = (TextView) itemView.findViewById(R.id.transaction_item_date);
            textViewType = (TextView) itemView.findViewById(R.id.transaction_item_type);
        }
    }
}
