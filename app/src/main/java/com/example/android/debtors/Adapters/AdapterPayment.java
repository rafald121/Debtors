package com.example.android.debtors.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rafaello on 2017-02-22.
 */

public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.MyViewHolder>{

    private static final String TAG = AdapterPayment.class.getSimpleName();

    List<Payment> listOfPayments = new ArrayList<>();
    DatabasePayments dbPayments;
    DatabaseClients dbClients;

    Context context;

    public AdapterPayment(Context context) {
        this.context = context;
        dbClients = new DatabaseClients(context);
        listOfPayments = getListOfPayments();
    }


    public AdapterPayment(Context context, boolean receivedOrGive) {
        this.context = context;
        dbClients = new DatabaseClients(context);
        listOfPayments = getListOfTransactionsByType(receivedOrGive);
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payments,
                parent, false);

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

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        TextView  textViewClient, textViewPaymentAmount, textViewDate, textViewType;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewClient = (TextView) itemView.findViewById(R.id.payment_item_client);
            textViewPaymentAmount = (TextView) itemView.findViewById(R.id.payments_item_totalamount);
            textViewDate = (TextView) itemView.findViewById(R.id.payments_item_date);
            textViewType = (TextView) itemView.findViewById(R.id.payments_item_type);


        }
    }

    private List<Payment> getListOfPayments(){
        DatabasePayments dbPayments = new DatabasePayments(context);

        List<Payment> list = dbPayments.getAllPayments();

        return list;
    }

    private List<Payment> getListOfTransactionsByType(boolean receivedOrGive) {
        DatabasePayments dbPayments = new DatabasePayments(context);

        List<Payment> list = dbPayments.getPaymentsByType(receivedOrGive);

        return list;
    }

    private Client getClientByID(long ID){
        DatabaseClients dbClients = new DatabaseClients(context);

        Client client = dbClients.getClientByID(ID);

        return client;
    }
}
