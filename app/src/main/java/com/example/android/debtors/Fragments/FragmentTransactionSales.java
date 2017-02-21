package com.example.android.debtors.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Adapters.AdapterTransacation;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.List;

public class FragmentTransactionSales extends Fragment {


    private static final String TAG = FragmentTransactionSales.class.getSimpleName();


    private DatabaseTransactions dbTransaction;
    private List<TransactionForClient> listOfTransactions;
    private List<Client> listOfClients;

    public FragmentTransactionSales() {
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTransactionSales.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTransactionSales newInstance(String param1, String param2) {
        FragmentTransactionSales fragment = new FragmentTransactionSales();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listOfTransactions = getListOfTransactions();
        listOfClients = getListOfClients();

        View rootView = inflater.inflate(R.layout.recycler_view_with_viewpager, container, false);
        if (listOfClients != null || listOfClients != null){
            AdapterTransacation adapterTransacation = new AdapterTransacation(listOfTransactions, listOfClients);
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_with_viewpager);
            setupRecyclerView(recyclerView);
            recyclerView.setAdapter(adapterTransacation);

        }else
            Log.e(TAG, "onCreateView: listOfClients or listOfTransacation is null");
        // Inflate the layout for this fragment
        return rootView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);//czy bedzie miala zmienny rozmiar podczas dzialania apki
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()
                .getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private List<TransactionForClient> getListOfTransactions(){
        DatabaseTransactions dbTransactions = new DatabaseTransactions(getContext());

        List<TransactionForClient> list = dbTransactions.getListOfTransaction();

        return list;
    }

    private List<Client> getListOfClients(){
        DatabaseClients dbClients = new DatabaseClients(getContext());

        List<Client> list = dbClients.getAllClient();

        return list;
    }
}
