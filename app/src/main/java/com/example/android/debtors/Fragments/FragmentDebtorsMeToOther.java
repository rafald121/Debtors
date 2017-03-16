package com.example.android.debtors.Fragments;

import android.content.Context;
import android.content.IntentFilter;
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
import android.widget.Toast;

import com.example.android.debtors.Adapters.AdapterDebtors;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Dialogs.DialogNewClient;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.R;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Rafaello on 2017-02-18.
 */
public class FragmentDebtorsMeToOther extends Fragment implements InterfaceViewPager{

    private static final String TAG = FragmentDebtorsMeToOther.class.getSimpleName();

    private DatabaseClients dbClients;
    private List<Client> listOfClients;
    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;
    private AdapterDebtors adapterDebtors;



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
        
        View rootView = inflater.inflate(R.layout.fragment_debtors_metoother, container, false);
        listOfClients = getClientsLessThanZero();
        adapterDebtors  = new AdapterDebtors(fragmentActivity, listOfClients);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_debtors_metoother_recycler);
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterDebtors);

//        class Threadd implements Runnable{

//            View rootView;

//            public Threadd(View rootView){
//                this.rootView = rootView;
//            }

//            @Override
//            public void run() {

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


//        Runnable r = new Threadd(rootView);
//        new Thread(r).run();

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

    public void onEvent(FragmentDebtors.SearchQuery query){
        Log.i(TAG, "onEvent: " + query.getMessage());
        adapterDebtors.filter(query.getMessage());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated: START");

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_debtors_metoother_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Debtors me to other", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                DialogNewClient dialogNewClient = new DialogNewClient(fragmentActivity,false);
                dialogNewClient.show();
            }
        });

        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: END");
    }
    @Override
    public void onAttach(Context context) {
        fragmentActivity = (FragmentActivity) context;
        EventBus.getDefault().register(this); // this == your class instance

        Log.i(TAG, "onAttach: START");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: START");
        EventBus.getDefault().unregister(this);

        super.onDetach();
    }

    public List<Client> getClientsLessThanZero(){
        dbClients = new DatabaseClients(getContext());
        List<Client> clients = dbClients.getClientWithLeftAmountMoreOrLessZero(false);
        return clients;
    }

    public void showFAB() {
        if(!fab.isShown())
            fab.show();
        else
            Log.e(TAG, "showFAB: ");
    }


    public void hideFAB(){
        if(fab.isShown())
            fab.hide();
        else
            Log.e(TAG, "hideFAB: ");
    }

    @Override
    public void notifyWhenSwitched() {
        Log.i(TAG, "notifyWhenSwitched: ME TO OTHER");
    }
}
