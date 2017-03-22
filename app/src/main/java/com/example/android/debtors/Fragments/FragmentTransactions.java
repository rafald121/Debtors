package com.example.android.debtors.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Adapters.CategoryAdapterTransactions;
import com.example.android.debtors.Enum.FragmentsIDsAndTags;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
import com.example.android.debtors.R;

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
    private ViewPager viewPager;
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

        Log.i(TAG, "onViewCreated: podczas tworzenia FragmentTransactions: " + MainActivity.subFragmentID);
//        MainActivity.subFragmentID = FragmentsIDsAndTags.TRANSACTIONSALL;

        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.transactions_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.transactions_tabs);
        Log.i(TAG, "onViewCreated: 1");

        if (categoryAdapterTransactions == null)
            categoryAdapterTransactions = new CategoryAdapterTransactions(getChildFragmentManager());
        else
            Log.e(TAG, "onViewCreated: 11 ");

        Log.i(TAG, "onViewCreated: 2");

        if (viewPager != null) {
            viewPager.setAdapter(categoryAdapterTransactions);
        } else
            Log.e(TAG, "onViewCreated: 22");
        Log.i(TAG, "onViewCreated: 3");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int position) {
                InterfaceViewPager intefrace = (InterfaceViewPager) categoryAdapterTransactions.instantiateItem(viewPager, position);
                if (intefrace != null) {
                    Log.i(TAG, "onPageSelected: switched to: " + position);
                    switch (position){
                        case 0:
                            MainActivity.subFragmentID = FragmentsIDsAndTags.TRANSACTIONSALL;
                            intefrace.notifyWhenSwitched();
                            break;
                        case 1:
                            MainActivity.subFragmentID = FragmentsIDsAndTags.TRANSACTIONSSALES;
                            intefrace.notifyWhenSwitched();
                            break;
                        case 2:
                            MainActivity.subFragmentID = FragmentsIDsAndTags.TRANSACTIONSPURCHASES;
                            intefrace.notifyWhenSwitched();
                            Log.i(TAG, "onPageSelected: after notify when switched");
                            break;
                        default:
                            Log.d(TAG, "onPageSelected() called with: position = [" + position + "]");
                            break;
                    }


                } else
                    Log.i(TAG, "onPageSelected: pomocy");
            }

            @Override
            public void onPageScrollStateChanged(final int position) {
            }
        });

        Log.i(TAG, "onViewCreated: 4");

        tabLayout.setupWithViewPager(viewPager);
        Log.i(TAG, "onViewCreated: 5");
        MainActivity.subFragmentID = FragmentsIDsAndTags.TRANSACTIONSALL;
        Log.i(TAG, "onViewCreated: 6, assigned, now subfragment is: " + MainActivity.subFragmentID);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(TAG, "onCreateOptionsMenu: START");
        inflater.inflate(R.menu.menu_transactions, menu);
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG, "onCreateOptionsMenu: END");
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
        Log.i(TAG, "onAttach: END");
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: START");
        super.onDetach();
    }


//    public void hideFAB() {
//        if (categoryAdapterTransactions != null) {
//            categoryAdapterTransactions.hideFAB();
//        }
//    }
//
//    public void showFAB() {
//        if(categoryAdapterTransactions != null){
//            categoryAdapterTransactions.showFAB();
//        }
//    }
}
