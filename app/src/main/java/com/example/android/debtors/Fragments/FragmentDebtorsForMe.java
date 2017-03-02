package com.example.android.debtors.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Adapters.AdapterDebtors;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Dialogs.DialogNewClient;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.R;

import java.util.List;

/**
 * Created by Rafaello on 2017-02-18.
 */
public class FragmentDebtorsForMe extends Fragment implements FragmentDebtors.SearchViewQuery{


    interface hideOrShowFab{
        void hideFab();
        void showFab();
    }

    private static final String TAG = FragmentDebtorsForMe.class.getSimpleName();

    private String query;
    private DatabaseClients dbClients;
    private List<Client> listOfClients;
    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;
    private AdapterDebtors adapterDebtors;
    private RecyclerView recyclerView;

    @Override
    public void searchViewQueryChanged(String query) {
        Log.i(TAG, "searchViewQueryChanged: halo");
        this.query = query;
        adapterDebtors.filter(query);
    }

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: START");
//        TODO make db is reading in another thread \/
//        TODO if listOfClients = null - zabezpieczyc, tak samo jak w innych fragmentach\/
        listOfClients = getClientsMoreThanZero();


        View rootView = inflater.inflate(R.layout.fragment_debtors_forme, container, false);
        adapterDebtors = new AdapterDebtors(fragmentActivity, listOfClients);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_debtors_forme_recyclerview);
        setupRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0){
                    Log.i(TAG, "onScrolled: w dol");

                    fab.hide();
                }else if(dy<0 ) {
                    Log.i(TAG, "onScrolled: w gore");

                    fab.show();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
//                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        Log.i(TAG, "onCreateView: END");

        recyclerView.setAdapter(adapterDebtors);

        return rootView;

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);//czy bedzie miala zmienny rozmiar podczas dzialania apki
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()
                .getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void scrolling(boolean direction){

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated: START");
        super.onViewCreated(view, savedInstanceState);

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_debtors_forme_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Debtors forme ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                DialogNewClient dialogNewClient = new DialogNewClient(fragmentActivity,true);
                dialogNewClient.show();

//                adapterDebtors.notifyDataSetChanged();


            }
        });

        Log.i(TAG, "onViewCreated: END");
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: START");
        fragmentActivity = (FragmentActivity) context;

//        try{
//            scrollingInterface = (Scrolling) context;
//        } catch (ClassCastException e ) {
//            throw new ClassCastException(context.toString()
//                    + " must implement OnHeadlineSelectedListener");
//        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: START");
        super.onDetach();
    }

    public List<Client> getClientsMoreThanZero() {
        dbClients = new DatabaseClients(getContext());
        List<Client> clients = dbClients.getClientWithLeftAmountMoreOrLessZero(true);
        return clients;
    }
}
