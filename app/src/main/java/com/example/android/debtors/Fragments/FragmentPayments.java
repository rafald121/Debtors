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

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Adapters.CategoryAdapterPayments;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Enum.FragmentsIDs;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
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
    private CategoryAdapterPayments categoryAdapterPayments;
    private ViewPager viewPager;
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

        viewPager = (ViewPager) view.findViewById(R.id.payments_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.payments_tabs);
        categoryAdapterPayments = new CategoryAdapterPayments(getChildFragmentManager());
        viewPager.setAdapter(categoryAdapterPayments);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int position) {
                InterfaceViewPager intefrace = (InterfaceViewPager) categoryAdapterPayments.instantiateItem(viewPager, position);
                if (intefrace != null) {
                    Log.i(TAG, "onPageSelected: switched to: " + position);
                    switch (position){
                        case 0:
                            MainActivity.subFragmentID = FragmentsIDs.PAYMENTSALL;
                            intefrace.notifyWhenSwitched();
                            break;
                        case 1:
                            MainActivity.subFragmentID = FragmentsIDs.PAYMENTSRECEIVED;
                            intefrace.notifyWhenSwitched();
                            break;
                        case 2:
                            MainActivity.subFragmentID = FragmentsIDs.PAYMENTSGIVEN;
                            intefrace.notifyWhenSwitched();
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

    public void hideFAB() {
        if (categoryAdapterPayments != null) {
            categoryAdapterPayments.hideFAB();
        }
    }

    public void showFAB() {
        if(categoryAdapterPayments != null){
            categoryAdapterPayments.showFAB();
        }
    }

}
