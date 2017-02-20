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
 * Created by Rafaello on 2017-02-20.
 */

public class AdapterAllClients extends RecyclerView.Adapter<AdapterAllClients.MyViewHolder> {

    List<Client> clientList = new ArrayList<>();

    public AdapterAllClients(List<Client> clientList) {
        this.clientList = clientList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allclients, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Client client = clientList.get(position);

        holder.allClientsItemName.setText(client.getClientName());
        holder.allClientsItemLeftAmount.setText(String.valueOf(client.getClientLeftAmount()));

    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
//        TODO check if private
        public ImageView allClientsItemAvatar;
        public TextView allClientsItemName, allClientsItemLeftAmount;
        public ImageButton allClientsItemButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            allClientsItemAvatar = (ImageView) itemView.findViewById(R.id.allclients_item_avatar);
            allClientsItemName = (TextView) itemView.findViewById(R.id.allclients_item_name);
            allClientsItemLeftAmount = (TextView) itemView.findViewById(R.id.allclients_item_leftamount);
            allClientsItemButton = (ImageButton) itemView.findViewById(R.id.allclients_item_arrow);
        }

    }
}
