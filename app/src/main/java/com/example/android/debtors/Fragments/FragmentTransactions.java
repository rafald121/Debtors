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

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Adapters.CategoryAdapterTransactions;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Interfaces.InterfaceSearchInRecyclerView;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
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
    private CategoryAdapterTransactions categoryAdapterTransactions;

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
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.transactions_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.transactions_tabs);

        categoryAdapterTransactions = new CategoryAdapterTransactions(getChildFragmentManager());
        viewPager.setAdapter(categoryAdapterTransactions);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int position) {
                InterfaceViewPager intefrace = (InterfaceViewPager) categoryAdapterTransactions.instantiateItem(viewPager, position);
                if (intefrace != null) {
                    Log.i(TAG, "onPageSelected: eyyy: " + position);
                    switch (position){
                        case 0:
                            MainActivity.CURRENT_SUB_TAG = "tagTransactionsAll";
                            break;
                        case 1:
                            MainActivity.CURRENT_SUB_TAG = "tagTransactionsSales";
                            break;
                        case 2:
                            MainActivity.CURRENT_SUB_TAG = "tagTransactionsPurchases";
                            break;

                    }
                    intefrace.notifyWhenSwitched();
                }
            }

            @Override
            public void onPageScrollStateChanged(final int position) {
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_transactions, menu);
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


    public void hideFAB() {
        if (categoryAdapterTransactions != null) {
            categoryAdapterTransactions.hideFAB();
        }
    }

    public void showFAB() {
        Log.i(TAG, "showFAB: 222");
        if(categoryAdapterTransactions != null){
            categoryAdapterTransactions.showFAB();
        }
    }
}
