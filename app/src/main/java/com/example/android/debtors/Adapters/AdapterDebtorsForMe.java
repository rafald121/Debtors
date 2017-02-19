package com.example.android.debtors.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.debtors.Model.Client;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-19.
 */

public class AdapterDebtorsForMe extends RecyclerView.Adapter<AdapterDebtorsForMe.MyViewHolder> {

    private static final String TAG = "AdapterDebtorsForMe";

    List<Client> clientList = new ArrayList<>();

    public AdapterDebtorsForMe(List<Client> clientList) {
        this.clientList = clientList;
        Log.i(TAG, "AdapterDebtorsForMe: " + clientList.toString());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debtors_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Client client = clientList.get(position);
        Log.i(TAG, "onBindViewHolder: client: " + client.toString());
        holder.name.setText(client.getClientName());
        holder.amount.setText(String.valueOf(client.getClientLeftAmount()));

    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView  name,amount;
        public ImageButton imageButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageButton = (ImageButton) itemView.findViewById(R.id.details);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            name= (TextView) itemView.findViewById(R.id.name);
            amount = (TextView) itemView.findViewById(R.id.amount);
        }



    }
}