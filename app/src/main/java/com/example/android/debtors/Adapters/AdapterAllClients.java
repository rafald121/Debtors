package com.example.android.debtors.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Fragments.FragmentSingleClientInfo;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-20.
 */

public class AdapterAllClients extends RecyclerView.Adapter<AdapterAllClients.MyViewHolder> {

    private static final String TAG = AdapterAllClients.class.getSimpleName();

    private List<Client> clientList = new ArrayList<>();
    private List<Client> clientListCopy = new ArrayList<>();

    private FragmentActivity fragmentActivity;


    public AdapterAllClients(FragmentActivity fragmentActivity, List<Client> clientList) {
        this.fragmentActivity = fragmentActivity;
        this.clientList = clientList;
        this.clientListCopy.addAll(clientList);
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

    public void filter(String text) {
        clientList.clear();
        if(text.isEmpty()){
            clientList.addAll(clientListCopy);
        } else{
            text = text.toLowerCase();

            for(Client client: clientListCopy){
                if(client.getClientName().toLowerCase().contains(text))
                    clientList.add(client);
            }

        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        TODO check if private
        private ImageView allClientsItemAvatar;
        private TextView allClientsItemName, allClientsItemLeftAmount;
        private ImageButton allClientsItemButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            allClientsItemAvatar = (ImageView) itemView.findViewById(R.id.allclients_item_avatar);
            allClientsItemName = (TextView) itemView.findViewById(R.id.allclients_item_name);
            allClientsItemLeftAmount = (TextView) itemView.findViewById(R.id.allclients_item_leftamount);
            allClientsItemButton = (ImageButton) itemView.findViewById(R.id.allclients_item_arrow);

            itemView.setOnClickListener(this);
            allClientsItemButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: clicked position " + getLayoutPosition());
            Client client = clientList.get(getLayoutPosition());
            Log.i(TAG, "onClick: clicked client : " + client.toString());
            Log.i(TAG, "onClick: client ID: " + client.getClientId());


            if(v.getId() == allClientsItemButton.getId()) {
                Log.i(TAG, "onClick: expand item");
                MainActivity.PREVIOUS_TAG = MainActivity.CURRENT_TAG;
                MainActivity.CURRENT_TAG = "singleClient";
                Fragment fragment = new FragmentSingleClientInfo();

                Bundle bundleArgument = setArgument(client.getClientId());
                fragment.setArguments( bundleArgument );

                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                FragmentTransaction fragmenttransaction = fragmentManager.beginTransaction();
                fragmenttransaction.replace(R.id.frame, fragment, MainActivity.CURRENT_TAG);
                fragmenttransaction.addToBackStack(null);
                fragmenttransaction.commit();
            } else {
                Log.i(TAG, "onClick: item content clicked");
            }
        }

        private Bundle setArgument(long id){
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            return bundle;
        }
    }
}
