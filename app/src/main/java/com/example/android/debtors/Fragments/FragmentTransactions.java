package com.example.android.debtors.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.example.android.debtors.Dialogs.DialogMenuTransaction;
import com.example.android.debtors.Dialogs.DialogTransactionTMP;
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

    private int typeOfTransactions = 0; //0 - all, 1- sales, 2- purchases

    private MenuItem menuItemArrow = null;
    private boolean sortUpOrDown = false;

    public FragmentTransactions() {
        Log.i(TAG, "FragmentTransactions: START");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transactions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.transactions_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.transactions_tabs);

        if (categoryAdapterTransactions == null)
            categoryAdapterTransactions = new CategoryAdapterTransactions(getChildFragmentManager());
        else
            Log.e(TAG, "onViewCreated: null ");

        if (viewPager != null) {
            viewPager.setAdapter(categoryAdapterTransactions);
        } else
            Log.e(TAG, "onViewCreated: null");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int position) {
                InterfaceViewPager intefrace = (InterfaceViewPager) categoryAdapterTransactions.instantiateItem(viewPager, position);
                if (intefrace != null) {

                    switch (position){
                        case 0:
                            MainActivity.fragmentID = FragmentsIDsAndTags.TRANSACTIONSALL;
                            intefrace.notifyWhenSwitched();
                            typeOfTransactions = 0;
                            break;
                        case 1:
                            MainActivity.fragmentID = FragmentsIDsAndTags.TRANSACTIONSSALES;
                            intefrace.notifyWhenSwitched();
                            typeOfTransactions = 1;
                            break;
                        case 2:
                            MainActivity.fragmentID = FragmentsIDsAndTags.TRANSACTIONSPURCHASES;
                            intefrace.notifyWhenSwitched();
                            typeOfTransactions = 2;
                            break;
                        default:
                            break;
                    }

                } else
                    Log.i(TAG, "onPageSelected: pomocy");
            }

            @Override
            public void onPageScrollStateChanged(final int position) {
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        MainActivity.fragmentID = FragmentsIDsAndTags.TRANSACTIONSALL;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_transactions, menu);

        menuItemArrow = menu.findItem(R.id.arrowtosort_transactions);
        menuItemArrow.setVisible(false);

        menuItemArrow.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                rotateSortArrow();
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_transactions_filter:

                menuItemArrow.setVisible(false);
                showDialog();
                return true;

            case R.id.menu_transactions_sortbyname:

                menuItemArrow.setVisible(true);
                sortUpOrDown = false;

                if (sortUpOrDown) {
                    menuItemArrow.setIcon(R.drawable.arrow_up);
                    sortUpOrDown = false;
                }
                else {
                    menuItemArrow.setIcon(R.drawable.arrow_down);
                    sortUpOrDown = true;
                }
                break;

            case R.id.menu_transactions_sortbyamount:

                menuItemArrow.setVisible(true);
                sortUpOrDown = false;

                if (sortUpOrDown) {
                    menuItemArrow.setIcon(R.drawable.arrow_up);
                    sortUpOrDown = false;
                }
                else {
                    menuItemArrow.setIcon(R.drawable.arrow_down);
                    sortUpOrDown = true;
                }
                break;

            case R.id.menu_transactions_sortbyquantity:

                menuItemArrow.setVisible(true);
                sortUpOrDown = false;

                if (sortUpOrDown) {
                    menuItemArrow.setIcon(R.drawable.arrow_up);
                    sortUpOrDown = false;
                }
                else {
                    menuItemArrow.setIcon(R.drawable.arrow_down);
                    sortUpOrDown = true;
                }
                break;

            case R.id.menu_transactions_sortbydate:

                menuItemArrow.setVisible(true);
                sortUpOrDown = false;

                if (sortUpOrDown) {
                    menuItemArrow.setIcon(R.drawable.arrow_up);
                    sortUpOrDown = false;
                }
                else {
                    menuItemArrow.setIcon(R.drawable.arrow_down);
                    sortUpOrDown = true;
                }
                break;

            default:
                Log.e(TAG, "onOptionsItemSelected: ERROR");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void rotateSortArrow() {
        if(sortUpOrDown) {
            menuItemArrow.setIcon(R.drawable.arrow_up);
            sortUpOrDown = false;
        }
        else {
            menuItemArrow.setIcon(R.drawable.arrow_down);
            sortUpOrDown = true;
        }
    }

    private void showDialog() {
        FragmentTransaction ft =  getFragmentManager().beginTransaction();
        Fragment prev = getChildFragmentManager().findFragmentByTag(FragmentsIDsAndTags.TAG_TRANSACTIONS);

        if(prev!=null)
            ft.remove(prev);
        else
            Log.e(TAG, "showDialog: prev is null");

        ft.addToBackStack(null);

        DialogFragment d = DialogMenuTransaction.newInstance(typeOfTransactions);
        d.show(getChildFragmentManager(), FragmentsIDsAndTags.TAG_DIALOG_MENU_TRANSACTIONS);
    }

    public void onButtonPressed(Uri uri) {
        Log.i(TAG, "onButtonPressed: START");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
