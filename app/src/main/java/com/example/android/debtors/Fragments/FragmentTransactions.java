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

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link FragmentTransactions.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link FragmentTransactions#newInstance} factory method to
// * create an instance of this fragment.
// */
public class FragmentTransactions extends Fragment {

    private static final String TAG = FragmentTransactions.class.getSimpleName();
    private DatabaseTransactions dbTransaction;
    private List<TransactionForClient> listOfTransactions;
    private List<Client> listOfClients;

//    TODO hmmmm ? \/
    private OnFragmentInteractionListener mListener;

    public FragmentTransactions() {
        Log.i(TAG, "FragmentTransactions: START");
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FragmentTransactions.
//     */
    // TODO: Rename and change types and number of parameters
//    public static FragmentTransactions newInstance(String param1, String param2) {
//        Log.i(TAG, "newInstance: START");
//        FragmentTransactions fragment = new FragmentTransactions();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        Log.i(TAG, "newInstance: END");
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: START");
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: END");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: START");
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
        Log.i(TAG, "onButtonPressed: START");
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: START");
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: START");
        super.onDetach();
        mListener = null;
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
//TODO sprobwac utilsa do baz
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
