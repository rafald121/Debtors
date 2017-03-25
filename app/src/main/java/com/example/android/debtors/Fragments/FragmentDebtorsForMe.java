package com.example.android.debtors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.example.android.debtors.EventBus.DialogMenuDebtorsForMeApply;
import com.example.android.debtors.EventBus.SearchQuery;
import com.example.android.debtors.EventBus.SearchQueryForMe;
import com.example.android.debtors.EventBus.ToggleFabWhenDrawerMove;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Interfaces.CallbackMenuDebtorsDialog;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.R;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Rafaello on 2017-02-18.
 */
public class FragmentDebtorsForMe extends Fragment implements InterfaceViewPager, CallbackMenuDebtorsDialog{

    public static boolean isMenuRangeActive = false;

    private static final String TAG = FragmentDebtorsForMe.class.getSimpleName();

    private DatabaseClients dbClients;

    private List<Client> listOfClients;
    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;
    private AdapterDebtors adapterDebtors;
    private RecyclerView recyclerView;
    public FragmentDebtorsForMe() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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
                    fab.hide();
                }else if(dy<0 ) {
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



    @Override
    public void reloadRecycler(int min, int max) {
        listOfClients = dbClients.getListOfClientWithLeftAmountInRange(min,max);
        adapterDebtors.updateList(listOfClients);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);//czy bedzie miala zmienny rozmiar podczas dzialania apki
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()
                .getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_debtors_forme_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogNewClient dialogNewClient = new DialogNewClient(fragmentActivity,true, new CallbackAddInDialog(){
                    @Override
                    public void reloadRecycler() {
                        adapterDebtors.updateList(getClientsMoreThanZero());
                    }
                });
                dialogNewClient.show();

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        fragmentActivity = (FragmentActivity) context;
        EventBus.getDefault().register(this);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    private List<Client> getClientsMoreThanZero() {
        dbClients = new DatabaseClients(getContext());
        List<Client> clients = dbClients.getClientWithLeftAmountMoreOrLessZero(true);
        return clients;
    }


    @Override
    public void notifyWhenSwitched() {
        Log.i(TAG, "notifyWhenSwitched: FOR ME");
    }

    //region EventBus
    public void onEvent(ToggleFabWhenDrawerMove toggleFabWhenDrawerMove){
        if(toggleFabWhenDrawerMove.isDirection())
            fab.show();
        else
            fab.hide();
    }
    public void onEvent(DialogMenuDebtorsForMeApply dialogMenuDebtorsForMeApply){
        Log.i(TAG, "onEvent: halo for me");
        isMenuRangeActive = true;
        listOfClients = dbClients.getListOfClientWithLeftAmountInRange(dialogMenuDebtorsForMeApply.getMin(), dialogMenuDebtorsForMeApply.getMax());
        adapterDebtors.updateList(listOfClients);
    }

    public void onEvent(SearchQueryForMe query){
        Log.i(TAG, "onEvent: " + query.getMessage());
        if(!isMenuRangeActive)
            adapterDebtors.filter(query.getMessage());
        else
            adapterDebtors.filter(query.getMessage(),listOfClients);
    }
    //endregion
}
