package com.example.android.debtors.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import org.w3c.dom.Text;

import static com.example.android.debtors.R.id.textView;

/**
 * Created by admin on 13.03.2017.
 */

public class AdapterTransactonChild extends ChildViewHolder {

    private TextView textViewQuantity, textViewProductValue, textViewEntryPayment, textViewDetails, textViewDetailsHeader;
    private LinearLayout linearLayout;

    private Context context;

    public AdapterTransactonChild(View itemView) {
        super(itemView);

        textViewQuantity = (TextView) itemView.findViewById(R.id.transactions_item_child_quantity);
        textViewProductValue = (TextView) itemView.findViewById(R.id.transactions_item_child_productvalue);
        textViewEntryPayment = (TextView) itemView.findViewById(R.id.transactions_item_child_details);
        textViewDetails = (TextView) itemView.findViewById(R.id.transactions_item_child_details);
        textViewDetailsHeader = (TextView) itemView.findViewById(R.id.transactions_item_child_details_header);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.transactions_item_child_linear_details);

    }

    public AdapterTransactonChild(View itemView, Context context) {
        super(itemView);

        this.context = context;

        textViewQuantity = (TextView) itemView.findViewById(R.id.transactions_item_child_quantity);
        textViewProductValue = (TextView) itemView.findViewById(R.id.transactions_item_child_productvalue);
        textViewEntryPayment = (TextView) itemView.findViewById(R.id.transactions_item_child_details);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.transactions_item_child_linear_details);

    }


    public void bind(@NonNull TransactionForClient transaction){
//        Client client =  getClientById(transaction.getTransactionClientID());

        textViewQuantity.setText(String.valueOf(transaction.getTransactionQuantity()));
        textViewProductValue.setText(String.valueOf(transaction.getTransactionProductValue()));
        if(transaction.getTransactionEntryPayment()!=0)
            textViewEntryPayment.setText(String.valueOf(transaction.getTransactionEntryPayment()));
        else
            textViewEntryPayment.setText("-");

        if(transaction.getTransactionDetails().equals("")){
            linearLayout.getLayoutParams().height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
            linearLayout.setVisibility(View.VISIBLE);
            textViewDetailsHeader.setVisibility(View.VISIBLE);
            textViewDetails.setVisibility(View.VISIBLE);
            textViewDetails.setText(transaction.getTransactionDetails());
        } else {
            linearLayout.setVisibility(View.INVISIBLE);
        }

    }

    private Client getClientById(long ID){
        DatabaseClients dbClients = new DatabaseClients(context);

        Client client = dbClients.getClientByID(ID);

        return client;
    }

}
