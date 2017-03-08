package com.example.android.debtors.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Adapters.AdapterPayment;
//import com.example.android.debtors.ItemListener.RecyclerItemClickListener;
import com.example.android.debtors.Dialogs.DialogPayment;
import com.example.android.debtors.Dialogs.DialogTransaction;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.ItemListener.RecyclerOnScrollListener;
import com.example.android.debtors.R;

public class FragmentPaymentsGiven extends Fragment {

    private static final String TAG = FragmentPaymentsGiven.class.getSimpleName();

    private FloatingActionButton fab;
    private FragmentActivity fragmentActivity;

//    interface test{
//        void hide();
//        void show();
//    }
//    private test mtest;

    public FragmentPaymentsGiven() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payments_given, container, false);

        AdapterPayment adapterPayment = new AdapterPayment(getContext(), false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_payments_given_recycler);
//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));
//        recyclerView.addOnScrollListener(new RecyclerOnScrollListener(this));
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(adapterPayment);

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_payment_given_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "payments given ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                DialogPayment dialogPayment = new DialogPayment(fragmentActivity, true, new CallbackAddInDialog() {
                    @Override
                    public void reloadRecycler() {

                        FragmentPaymentsGiven fragmentPaymentsGiven = new FragmentPaymentsGiven();

                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                        fragmentTransaction.replace(R.id.frame, fragmentPaymentsGiven);
                        fragmentTransaction.commitAllowingStateLoss();

                    }
                });
                dialogPayment.show();
            }
        });
    }

    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
