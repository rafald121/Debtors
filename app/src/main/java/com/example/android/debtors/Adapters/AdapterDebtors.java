package com.example.android.debtors.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Fragments.FragmentDebtorsForMe;
import com.example.android.debtors.Fragments.FragmentSingleClientInfo;
import com.example.android.debtors.Fragments.FragmentTransactions;
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
    List<Client> clientListCopy = new ArrayList<>();

    private FragmentActivity fragmentActivity;

    public AdapterDebtors(FragmentActivity fragmentActivity, List<Client> clientList) {
        this.fragmentActivity = fragmentActivity;
        this.clientList = clientList;
        this.clientListCopy.addAll(clientList);
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

        public ImageView debtorsItemImageView;
        public TextView  debtorsItemName,debtorsItemAmount;
        public ImageButton debtorsItemImageButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            debtorsItemImageButton = (ImageButton) itemView.findViewById(R.id.debtors_item_arrow);
            debtorsItemImageView = (ImageView) itemView.findViewById(R.id.debtors_item_avatar);
            debtorsItemName= (TextView) itemView.findViewById(R.id.debtors_item_name);
            debtorsItemAmount = (TextView) itemView.findViewById(R.id.debtors_item_leftamount);

            itemView.setOnClickListener(this);
            debtorsItemImageButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Client client = clientList.get(getLayoutPosition());

            if(v.getId() == debtorsItemImageButton.getId()) {
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