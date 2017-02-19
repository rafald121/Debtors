package com.example.android.debtors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.debtors.Adapters.AdapterDebtorsForMe;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.R;

import java.util.List;

/**
 * Created by Rafaello on 2017-02-18.
 */
public class FragmentDebtorsForMe extends Fragment{
    
    private static final String TAG = FragmentDebtorsForMe.class.getSimpleName();

    DatabaseClients dbClients;
    List<Client> listOfClients;

    public FragmentDebtorsForMe() {
        Log.i(TAG, "FragmentDebtorsForMe: START");
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: start");
        super.onCreate(savedInstanceState);



        Log.i(TAG, "onCreate: end");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated: START");
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: END");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: START");
        listOfClients = getAllClientsFromDatabase();

        View rootView = inflater.inflate(R.layout.recycler, container, false);
        AdapterDebtorsForMe adapterDebtorsForMe = new AdapterDebtorsForMe(listOfClients);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterDebtorsForMe);


        Log.i(TAG, "onCreateView: END");
        return rootView;

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);//czy bedzie miala zmienny rozmiar podczas dzialania apki
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()
                .getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onAttach(Context context) {
        Toast.makeText(context, "halo", Toast.LENGTH_SHORT).show();

        Log.i(TAG, "onAttach: START");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: START");
        super.onDetach();
    }

    public List<Client> getAllClientsFromDatabase() {
        dbClients = new DatabaseClients(getContext());
        List<Client> clients = dbClients.getAllClient();

        for(Client c: clients)
            Log.i(TAG, "getAllClientsFromDatabase: " + c.toString());

        return clients;
    }
}
