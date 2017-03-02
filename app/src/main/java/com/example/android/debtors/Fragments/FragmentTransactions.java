package com.example.android.debtors.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Adapters.CategoryAdapterTransactions;
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

    public FragmentTransactions() {
        Log.i(TAG, "FragmentTransactions: START");
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
        return inflater.inflate(R.layout.fragment_transactions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.transactions_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.transactions_tabs);

        CategoryAdapterTransactions categoryAdapterTransactions = new CategoryAdapterTransactions
                (getChildFragmentManager());
        viewPager.setAdapter(categoryAdapterTransactions);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_transactions,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_transaction_max_total_amount:
                Log.i(TAG, "onOptionsItemSelected: menu_transaction_max_total_amount");
                return true;
            case R.id.menu_transaction_min_total_amount:
                Log.i(TAG, "onOptionsItemSelected: menu_transaction_min_total_amount");
                return true;
            case R.id.menu_transaction_max_date:
                Log.i(TAG, "onOptionsItemSelected: menu_transaction_max_date");
                return true;
            case R.id.menu_transaction_min_date:
                Log.i(TAG, "onOptionsItemSelected: menu_transaction_min_date");
                return true;
            case R.id.menu_transaction_max_quantity:
                Log.i(TAG, "onOptionsItemSelected: menu_transaction_max_quantity");
                return true;
            case R.id.menu_transaction_min_quantity:
                Log.i(TAG, "onOptionsItemSelected: menu_transaction_min_quantity");
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonPressed(Uri uri) {
        Log.i(TAG, "onButtonPressed: START");
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: START");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: START");
        super.onDetach();
    }


}
