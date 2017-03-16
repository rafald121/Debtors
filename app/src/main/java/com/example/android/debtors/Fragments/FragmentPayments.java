package com.example.android.debtors.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.example.android.debtors.Adapters.CategoryAdapterPayments;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.ItemListener.RecyclerOnScrollListener;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.R;

import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link FragmentPayments.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link FragmentPayments#newInstance} factory method to
// * create an instance of this fragment.
// */
public class FragmentPayments extends Fragment {

    private static final String TAG = FragmentPayments.class.getSimpleName();

    public FragmentPayments() {
        Log.i(TAG, "FragmentPayments: START");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: START");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: START");
        return inflater.inflate(R.layout.fragment_payments, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.payments_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.payments_tabs);
        CategoryAdapterPayments categoryAdapterPayments = new CategoryAdapterPayments(getChildFragmentManager());
        viewPager.setAdapter(categoryAdapterPayments);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_payments,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_payment_max_amount:
                Log.i(TAG, "onOptionsItemSelected: menu_payment_max_amount");
                return true;
            case R.id.menu_payment_min_amount:
                Log.i(TAG, "onOptionsItemSelected: menu_payment_min_amount");
                return true;
            case R.id.menu_payment_max_date:
                Log.i(TAG, "onOptionsItemSelected: menu_payment_max_date");
                return true;
            case R.id.menu_payment_min_date:
                Log.i(TAG, "onOptionsItemSelected: menu_payment_min_date");
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

    public void showFAB() {
    }
public void hideFAB() {
    }

}
