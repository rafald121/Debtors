package com.example.android.debtors.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Transaction;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.List;

/**
 * Created by admin on 13.03.2017.
 */

public class AdapterTransactionParent extends ParentViewHolder {

    private static final String TAG = AdapterTransactionParent.class.getSimpleName();

    private TextView textViewClient, textViewTotalAmount, textViewDate, textViewType;
    private Context context;

    public AdapterTransactionParent(View itemView) {
        super(itemView);

        textViewClient = (TextView) itemView.findViewById(R.id.transaction_item_client);
        textViewTotalAmount = (TextView) itemView.findViewById(R.id.transaction_item_totalamount);
        textViewDate = (TextView) itemView.findViewById(R.id.transaction_item_date);
        textViewType = (TextView) itemView.findViewById(R.id.transaction_item_type);
    }

    public AdapterTransactionParent(View itemView, Context context) {
        super(itemView);

        this.context = context;

        textViewClient = (TextView) itemView.findViewById(R.id.transaction_item_client);
        textViewTotalAmount = (TextView) itemView.findViewById(R.id.transaction_item_totalamount);
        textViewDate = (TextView) itemView.findViewById(R.id.transaction_item_date);
        textViewType = (TextView) itemView.findViewById(R.id.transaction_item_type);
    }

    public void bind(@NonNull TransactionForClient transaction){
        Client client =  getClientById(transaction.getTransactionClientID());
        String clientName = client.getClientName();

        textViewClient.setText(clientName);
        textViewTotalAmount.setText(String.valueOf(transaction.getTransactionProductValue()
                *transaction.getTransactionQuantity()));


        String[] datearray = transaction.getTransactionDate().split(" ");
        String date = datearray[0];

        textViewDate.setText(date);
        if(transaction.isTransactionBuyOrSell())
            textViewType.setText("Sale");
        else
            textViewType.setText("Purchase");


    }



    private Client getClientById(long ID){
        DatabaseClients dbClients = new DatabaseClients(context);

        Client client = dbClients.getClientByID(ID);

        return client;
    }

}
