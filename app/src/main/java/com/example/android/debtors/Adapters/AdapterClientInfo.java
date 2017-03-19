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
    private List<Payment> listOfPayments;
    private Context context;

    public AdapterClientInfo(Context context) {
        this.context = context;
    }

    public AdapterClientInfo(Context context, List<Payment> list) {
        this.context = context;
        this.listOfPayments = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clients_info_payments, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Payment payment = listOfPayments.get(position);

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
    }

    @Override
    public int getItemCount() {
        return listOfPayments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewClient, textViewPaymentAmount, textViewDate, textViewType;

        public MyViewHolder(View itemView) {
            super(itemView);
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
