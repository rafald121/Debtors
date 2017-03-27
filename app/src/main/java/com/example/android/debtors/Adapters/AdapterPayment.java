package com.example.android.debtors.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-22.
 */

public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.MyViewHolder>{

    private static final String TAG = AdapterPayment.class.getSimpleName();

    private List<Payment> listOfPayments = new ArrayList<>();
    private DatabasePayments dbPayments = null;
    private DatabaseClients dbClients = null;
    private Context context = null;

    private View itemView = null;


    public AdapterPayment(Context context){
        this.context = context;
        this.listOfPayments = getListOfPayments();
    }

    public AdapterPayment(Context context, List<Payment> listOfPayments) {
        this.context = context;
        this.listOfPayments = listOfPayments;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payments,
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

        if(!title.equals("")) {
            holder.linearLayout.getLayoutParams().height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.textViewDetails.setVisibility(View.VISIBLE);
            holder.textViewDetailsHeader.setVisibility(View.VISIBLE);
            holder.textViewDetails.setText(payment.getPaymentDetails());
        } else{
            holder.linearLayout.setVisibility(View.INVISIBLE);
        }
        if (payment.isPaymentGotOrGiven())//if tru
            holder.textViewType.setText("Received");
        else
            holder.textViewType.setText("Given");
    }

    @Override
    public int getItemCount() {
        return listOfPayments.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        private  TextView  textViewClient, textViewPaymentAmount, textViewDate, textViewType, textViewDetails, textViewDetailsHeader;
        private LinearLayout linearLayout;
        private ImageButton itemPaymentDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewClient = (TextView) itemView.findViewById(R.id.payment_item_client);
            textViewPaymentAmount = (TextView) itemView.findViewById(R.id.payments_item_totalamount);
            textViewDate = (TextView) itemView.findViewById(R.id.payments_item_date);
            textViewType = (TextView) itemView.findViewById(R.id.payments_item_type);
            textViewDetails = (TextView) itemView.findViewById(R.id.payments_item_details);
            textViewDetailsHeader = (TextView) itemView.findViewById(R.id.payments_item_details_header);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.payments_item_linear_details);
            itemPaymentDelete = (ImageButton) itemView.findViewById(R.id.item_payments_delete);

            itemView.setOnClickListener(this);
            itemPaymentDelete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Payment payment = listOfPayments.get(getLayoutPosition());

            if(v.getId() == itemPaymentDelete.getId()){
                deletePayment(payment.getPaymentID());
                listOfPayments.remove(payment);
                notifyDataSetChanged();
            } else
                Log.i(TAG, "onClick: content");
        }

        private void deletePayment(int paymentID) {
            dbPayments = new DatabasePayments(context);
            dbPayments.deletePayment(paymentID);
        }
    }

    private Client getClientByID(long ID){
        DatabaseClients dbClients = new DatabaseClients(context);

        Client client = dbClients.getClientByID(ID);

        return client;
    }

    private List<Payment> getListOfPayments(){
        DatabasePayments dbPayments = new DatabasePayments(context);

        List<Payment> list = dbPayments.getAllPayments();

        return list;
    }

}
