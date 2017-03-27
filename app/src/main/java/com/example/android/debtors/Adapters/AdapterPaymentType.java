package com.example.android.debtors.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rafaello on 2017-02-22.
 */

public class AdapterPaymentType extends RecyclerView.Adapter<AdapterPaymentType.MyViewHolder> {

    private static final String TAG = AdapterPayment.class.getSimpleName();

    private List<Payment> listOfPayments = new ArrayList<>();
    private DatabasePayments dbPayments = null;
    private DatabaseClients dbClients = null;
    private Context context = null;

    private View itemView = null;


    public AdapterPaymentType(Context context){
        this.context = context;
        this.listOfPayments = getListOfPayments();
    }

    public AdapterPaymentType(Context context, List<Payment> listOfPayments) {
        this.context = context;
        this.listOfPayments = listOfPayments;
    }

    public AdapterPaymentType(Context context, boolean receivedOrGive) {
        this.context = context;
        this.dbClients = new DatabaseClients(context);
        this.listOfPayments = getListOfTransactionsByType(receivedOrGive);
    }

    public AdapterPaymentType(Context context, long clientID){
        this.context = context;
        this.dbPayments = new DatabasePayments(context);
        this.listOfPayments = dbPayments.getPaymentsFromClient(clientID);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_payments_type,
                    parent, false);

        return new MyViewHolder(itemView);
    }

    public void updateList(List<Payment> list){
        listOfPayments.clear();
        listOfPayments.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Payment payment = listOfPayments.get(position);

        String clientName = getClientByID(payment.getPaymentClientID()).getClientName();
        String[] dateArray = payment.getPaymentDate().split(" ");
        String dateString = dateArray[0];
        String title = payment.getPaymentDetails();

        holder.textViewClient.setText(clientName);
        holder.textViewDate.setText(dateString);
        holder.textViewPaymentAmount.setText(String.valueOf(payment.getPaymentAmount()));

        if( !title.equals("")  && !(title.length() == 0)) {
            Log.i(TAG, "onBindViewHolder: for: " + payment.toString());
            holder.linearLayout.getLayoutParams().height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.textViewDetails.setVisibility(View.VISIBLE);
            holder.textViewDetailsHeader.setVisibility(View.VISIBLE);
            holder.textViewDetails.setText(payment.getPaymentDetails());
        } else{
            Log.i(TAG, "onBindViewHolder: not for: " + payment.toString());
            holder.linearLayout.getLayoutParams().height = 0;
            holder.linearLayout.setVisibility(View.INVISIBLE);
            holder.textViewDetails.setVisibility(View.INVISIBLE);
            holder.textViewDetailsHeader.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listOfPayments.size();
    }

    
    public class MyViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{ 

        private  TextView  textViewClient, textViewPaymentAmount, textViewDate, textViewDetails, textViewDetailsHeader;
        private LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewClient = (TextView) itemView.findViewById(R.id.payment_item_client);
            textViewPaymentAmount = (TextView) itemView.findViewById(R.id.payments_item_totalamount);
            textViewDate = (TextView) itemView.findViewById(R.id.payments_item_date);
            textViewDetails = (TextView) itemView.findViewById(R.id.payments_item_details);
            textViewDetailsHeader = (TextView) itemView.findViewById(R.id.payments_item_details_header);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.payments_item_linear_details);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: halo");
            Payment payment = listOfPayments.get(getLayoutPosition());
            Log.i(TAG, "onClick: payment to string: " + payment.toString());
        }

    }
    private Client getClientByID(long ID){
        DatabaseClients dbClients = new DatabaseClients(context);

        Client client = dbClients.getClientByID(ID);

        return client;
    }

    private List<Payment> getListOfPaymentsByClient(long clientID){
        DatabasePayments dbPayments = new DatabasePayments(context);

        List<Payment> paymentList = dbPayments.getPaymentsFromClient(clientID);

        return paymentList;
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
}