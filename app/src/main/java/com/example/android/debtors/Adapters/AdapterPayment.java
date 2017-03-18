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

public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.MyViewHolder>{

    private static final String TAG = AdapterPayment.class.getSimpleName();

    private List<Payment> listOfPayments = new ArrayList<>();
    private DatabasePayments dbPayments = null;
    private DatabaseClients dbClients = null;
    private long clientID = -1;
    private Context context = null;

    public AdapterPayment(Context context){
        this.context = context;
        this.listOfPayments = getListOfPayments();
    }

    public AdapterPayment(Context context, List<Payment> listOfPayments) {
        this.context = context;
        this.dbClients = new DatabaseClients(context);
        this.listOfPayments = listOfPayments;
    }

    public AdapterPayment(Context context, boolean receivedOrGive) {
        this.context = context;
        this.dbClients = new DatabaseClients(context);
        this.listOfPayments = getListOfTransactionsByType(receivedOrGive);
    }

    public AdapterPayment(Context context, long clientID){
        this.context = context;
        this.dbPayments = new DatabasePayments(context);
        this.listOfPayments = dbPayments.getPaymentsFromClient(clientID);
        Log.i(TAG, "AdapterPayment: listOfPayments: " + listOfPayments.toString());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: start");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_payments,
                parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: start");
        Payment payment = listOfPayments.get(position);
        Log.i(TAG, "onBindViewHolder: payment " + payment.toString());
        String clientName = getClientByID(payment.getPaymentClientID()).getClientName();
        String[] dateArray = payment.getPaymentDate().split(" ");
        String dateString = dateArray[0];
        String title = payment.getPaymentDetails();

        holder.textViewClient.setText(clientName);
        holder.textViewDate.setText(dateString);
        holder.textViewPaymentAmount.setText(String.valueOf(payment.getPaymentAmount()));

        if(!title.equals("")) {
            holder.linearLayout.getLayoutParams().height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.textViewDetails.setVisibility(View.VISIBLE);
            holder.textViewDetailsHeader.setVisibility(View.VISIBLE);
            holder.textViewDetails.setText(payment.getPaymentDetails());
        } else{
            holder.linearLayout.setVisibility(View.INVISIBLE);
//            holder.textViewDetails.setVisibility(View.INVISIBLE);
//            holder.textViewDetailsHeader.setVisibility(View.INVISIBLE);
        }

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

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        private  TextView  textViewClient, textViewPaymentAmount, textViewDate, textViewType, textViewDetails, textViewDetailsHeader;
        private LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            Log.i(TAG, "MyViewHolder: start");
            textViewClient = (TextView) itemView.findViewById(R.id.payment_item_client);
            textViewPaymentAmount = (TextView) itemView.findViewById(R.id.payments_item_totalamount);
            textViewDate = (TextView) itemView.findViewById(R.id.payments_item_date);
            textViewType = (TextView) itemView.findViewById(R.id.payments_item_type);
            textViewDetails = (TextView) itemView.findViewById(R.id.payments_item_details);
            textViewDetailsHeader = (TextView) itemView.findViewById(R.id.payments_item_details_header);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.payments_item_linear_details);

            Log.i(TAG, "MyViewHolder: end");
        }
    }
    private List<Payment> getListOfPaymentsByClient(long clientID){
        DatabasePayments dbPayments = new DatabasePayments(context);

        List<Payment> paymentList = dbPayments.getPaymentsFromClient(clientID);
        Log.i(TAG, "getListOfPaymentsByClient: list of payments: " + paymentList.toString());
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

    private Client getClientByID(long ID){
        DatabaseClients dbClients = new DatabaseClients(context);

        Client client = dbClients.getClientByID(ID);

        return client;
    }
}
