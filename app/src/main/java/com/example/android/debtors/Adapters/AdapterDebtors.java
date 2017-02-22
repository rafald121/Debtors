package com.example.android.debtors.Adapters;

import android.support.v7.widget.RecyclerView;
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

public class AdapterDebtors extends RecyclerView.Adapter<AdapterDebtors.MyViewHolder> {

    private static final String TAG = AdapterDebtors.class.getSimpleName();

    List<Client> clientList = new ArrayList<>();

    public AdapterDebtors(List<Client> clientList) {
        this.clientList = clientList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_debtors, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Client client = clientList.get(position);
        holder.debtorsItemName.setText(client.getClientName());
        holder.debtorsItemAmount.setText(String.valueOf(client.getClientLeftAmount()));

    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView debtorsItemImageView;
        public TextView  debtorsItemName,debtorsItemAmount;
        public ImageButton debtorsItemImageButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            debtorsItemImageButton = (ImageButton) itemView.findViewById(R.id.debtors_item_arrow);
            debtorsItemImageView = (ImageView) itemView.findViewById(R.id.debtors_item_avatar);
            debtorsItemName= (TextView) itemView.findViewById(R.id.debtors_item_name);
            debtorsItemAmount = (TextView) itemView.findViewById(R.id.debtors_item_leftamount);
        }



    }
}