package com.example.android.debtors.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Adapters.AdapterPayment;
//import com.example.android.debtors.ItemListener.RecyclerItemClickListener;
import com.example.android.debtors.ItemListener.RecyclerOnScrollListener;
import com.example.android.debtors.R;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link FragmentPaymentsGiven.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link FragmentPaymentsGiven#newInstance} factory method to
// * create an instance of this fragment.
// */
public class FragmentPaymentsGiven extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = FragmentPaymentsGiven.class.getSimpleName();

    interface test{
        void hide();
        void show();
    }
    private test mtest;


    private FloatingActionButton fab;

    private OnFragmentInteractionListener mListener;

    public FragmentPaymentsGiven() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.recycler_view_with_viewpager, container, false);

        AdapterPayment adapterPayment = new AdapterPayment(getContext(), false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_with_viewpager);


//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));
//        recyclerView.addOnScrollListener(new RecyclerOnScrollListener(this));
        
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterPayment);

        // Inflate the layout for this fragment

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
//                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        return rootView;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        fab = (FloatingActionButton) view.findViewById(R.id.fab_payments_given);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "PaymentsGiven", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        super.onViewCreated(view, savedInstanceState);
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
        mListener = null;
    }

//    @Override
//    public void hideFab() {
//        Log.i(TAG, "hideFab: ");
//        mtest.hide();
//    }
//
//    @Override
//    public void showFab() {
//        Log.i(TAG, "showFab: ");
//        mtest.show();
//    }

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
}
