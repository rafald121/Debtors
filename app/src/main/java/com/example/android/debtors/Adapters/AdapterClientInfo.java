package com.example.android.debtors.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 26.02.2017.
 */

public class AdapterClientInfo  extends RecyclerView.Adapter<AdapterClientInfo.MyViewHolder> {

    private static final String TAG = AdapterClientInfo.class.getSimpleName();
    List<Payment> listOfPayments;
    Context context;

    public AdapterClientInfo(Context context, List<Payment> list) {
        Log.i(TAG, "AdapterClientInfo: ");
        this.context = context;
        this.listOfPayments = list;
        Log.i(TAG, "AdapterClientInfo: size of list : " + list.size());
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clients_info_payments, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: START");
        Payment payment = listOfPayments.get(position);
        Log.i(TAG, "onBindViewHolder: payment " + payment.toString());

        String clientName = getClientByID(payment.getPaymentClientID()).getClientName();
        String[] dateArray = payment.getPaymentDate().split(" ");
        String dateString = dateArray[0];

        holder.textViewClient.setText(clientName);
        holder.textViewDate.setText(dateString);
        holder.textViewPaymentAmount.setText(String.valueOf(payment.getPaymentAmount()));
        if(payment.isPaymentGotOrGiven())//if tru
            holder.textViewType.setText("Received");
        else
            holder.textViewType.setText("Given");
        Log.i(TAG, "onBindViewHolder: END");
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: " + listOfPayments.size());
        return listOfPayments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewClient, textViewPaymentAmount, textViewDate, textViewType;


        public MyViewHolder(View itemView) {
            super(itemView);
            Log.i(TAG, "MyViewHolder: ");
            textViewClient = (TextView) itemView.findViewById(R.id.clientinfo_item_client);
            textViewPaymentAmount = (TextView) itemView.findViewById(R.id.clientinfo_item_totalamount);
            textViewDate = (TextView) itemView.findViewById(R.id.clientinfo_item_date);
            textViewType = (TextView) itemView.findViewById(R.id.clientinfo_item_type);

        }
    }



    private Client getClientByID(long ID){
        DatabaseClients dbClients = new DatabaseClients(context);

        Client client = dbClients.getClientByID(ID);

        return client;
    }
}
