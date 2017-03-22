package com.example.android.debtors.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Adapters.AdapterAllClients;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Dialogs.DialogMenuAllClients;
import com.example.android.debtors.Dialogs.DialogNewClient;
import com.example.android.debtors.EventBus.ToggleFabWhenDrawerMove;
import com.example.android.debtors.Helper.SwipeableRecyclerViewTouchListener;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Interfaces.CallbackMenuAllclientDialog;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.R;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link FragmentAllClients.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link FragmentAllClients#newInstance} factory method to
// * create an instance of this fragment.
// */

public class FragmentAllClients extends Fragment implements CallbackMenuAllclientDialog {
    //IF USER SELECTED USERS IN RANGE IT  \/ CHANGE TO TRUE
    public static boolean isMenuRangeActive = false;
// AND THEN SEARCHING IN SEARCHVIEW GIVE RESULT ONLY FROM CLIENT FROM RANGE SELECTED IN MENU

    private static final String TAG = FragmentAllClients.class.getSimpleName();

    private List<Client> listOfAllClients = new ArrayList<>();

    private DatabaseClients dbClients;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private FragmentActivity fragmentActivity;

    private FloatingActionButton fab;

    private View rootView = null;
    private RecyclerView recyclerView = null;
    private AdapterAllClients adapterAllClients = null;

    private CallbackAddInDialog addInDialog = null;

    public FragmentAllClients() {
        Log.i(TAG, "FragmentAllClients: START");
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: START");

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Log.i(TAG, "onCreate: END");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: START");

        listOfAllClients = getAllClientsFromDatabase();

        rootView = inflater.inflate(R.layout.fragment_all_clients, container, false);
        adapterAllClients = new AdapterAllClients(fragmentActivity, listOfAllClients);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_without_viewpager);
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterAllClients);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0  && fab.isShown())
                    fab.hide();
                else if(dy<0 && !fab.isShown())
                    fab.show();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {


                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    Log.i(TAG, "onScrollStateChanged: ");
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        SwipeableRecyclerViewTouchListener swipeTouchListener =
            new SwipeableRecyclerViewTouchListener(recyclerView,
                new SwipeableRecyclerViewTouchListener.SwipeListener() {
//                            @Override
//                            public boolean canSwipe(int position) {
//                                return true;
//                            }

                    @Override
                    public boolean canSwipeLeft(int position) {
                        return true;
                    }

                    @Override
                    public boolean canSwipeRight(int position) {
                        return true;
                    }

                    @Override
                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        Log.i(TAG, "onDismissedBySwipeLeft: left");
                        for (int position : reverseSortedPositions) {
                            listOfAllClients.remove(position);
                            adapterAllClients.notifyItemRemoved(position);
                        }
                        adapterAllClients.notifyDataSetChanged();
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        Log.i(TAG, "onDismissedBySwipeRight: right");
                        for (int position : reverseSortedPositions) {
                            listOfAllClients.remove(position);
                            adapterAllClients.notifyItemRemoved(position);
                        }
                        adapterAllClients.notifyDataSetChanged();
                    }
                });

        recyclerView.addOnItemTouchListener(swipeTouchListener);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_view, menu);
        inflater.inflate(R.menu.menu_allclients, menu);

        MenuItem searchItem = menu.findItem(R.id.search_view);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    if(!isMenuRangeActive)
                        adapterAllClients.filter(newText);
                    else
                        adapterAllClients.filter(newText,listOfAllClients);
//                   searchView.clearFocus();
//                    finish();
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    adapterAllClients.filter(query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);

        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search_view:
                Log.i(TAG, "onOptionsItemSelected case R.id.allclients_search:");
                // Not implemented here
                return false;
            case R.id.dialog_menu_allclient_show_dialog:{
                Log.i(TAG, "onOptionsItemSelected: show dialog ?");

                showDialog();



                return true;

            }
//            case R.id.menu_allclients_min_amount:
//                Log.i(TAG, "onOptionsItemSelected: menu_allclients_min_amount");
//                return true;
//            case R.id.menu_allclients_max_date:
//                Log.i(TAG, "onOptionsItemSelected: menu_allclients_max_date");
//                return true;
//            case R.id.menu_allclients_min_date:
//                Log.i(TAG, "onOptionsItemSelected: menu_allclients_min_date");
//                return true;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        Fragment prev = getFragmentManager().findFragmentByTag(MainActivity.TAG_ALL_CLIENTS);
        Fragment prev = getChildFragmentManager().findFragmentByTag(MainActivity.TAG_ALL_CLIENTS);
        if(prev!=null)
            ft.remove(prev);
        else
            Log.e(TAG, "showDialog: prev isnot null");

        ft.addToBackStack(null);

        DialogFragment d = DialogMenuAllClients.newInstance();

        d.show(getChildFragmentManager(), "dialogMenuAllClient");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = (FloatingActionButton) view.findViewById(R.id.fab_allclients);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Allclients", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                DialogNewClient dialogNewClient = new DialogNewClient(fragmentActivity, new CallbackAddInDialog() {
                    @Override
                    public void reloadRecycler() {
                        adapterAllClients.updateList(getAllClientsFromDatabase());
                    }
                });
                dialogNewClient.show();

            }
        });

    }

    public void onButtonPressed(Uri uri) {
        Log.i(TAG, "onButtonPressed: START");
    }

    public void onEvent(ToggleFabWhenDrawerMove toggleFabWhenDrawerMove){
        if(toggleFabWhenDrawerMove.isDirection())
            fab.show();
        else
            fab.hide();
    }


    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: START");

        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;


        EventBus.getDefault().register(this); // this == your class instance

    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: START");
        super.onDetach();

        addInDialog = null;
        EventBus.getDefault().unregister(this);

    }

    public List<Client> getAllClientsFromDatabase() {
        dbClients = new DatabaseClients(getContext());
        List<Client> list = dbClients.getAllClient();
        return list;
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
    public void reloadRecycler(int min, int max) {
        Log.i(TAG, "reloadRecycler: halo korwa: " + min + max);
        listOfAllClients = dbClients.getListOfClientWithLeftAmountInRange(min,max);
//        adapterAllClients.notifyDataSetChanged();
        adapterAllClients.updateList(listOfAllClients);
        isMenuRangeActive = true;
    }
}
