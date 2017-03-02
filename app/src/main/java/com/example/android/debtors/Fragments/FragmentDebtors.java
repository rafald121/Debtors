package com.example.android.debtors.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Adapters.CategoryAdapterDebtors;
import com.example.android.debtors.R;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link FragmentDebtors.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link FragmentDebtors#newInstance} factory method to
// * create an instance of this fragment.
// */
public class FragmentDebtors extends Fragment  {

    private static final String TAG = FragmentDebtors.class.getSimpleName();
    static final String QUERY_DEBTORS = "QUERY_DEBTORS";

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private FragmentActivity fragmentActivity;

    interface SearchViewQuery{
        void searchViewQueryChanged(String query);
    }

    private SearchViewQuery searchViewQuery;

    public FragmentDebtors() {
        Log.i(TAG, "FragmentDebtors: START");
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: start");

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.i(TAG, "onCreate: end");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: start");

        Log.i(TAG, "onCreateView: end");



        return inflater.inflate(R.layout.fragment_debtors, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.debtors_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.debtors_tabs);

        CategoryAdapterDebtors categoryAdapterDebtors = new CategoryAdapterDebtors
                (getChildFragmentManager());
        viewPager.setAdapter(categoryAdapterDebtors);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_view, menu);
        inflater.inflate(R.menu.menu_debtors, menu);


        MenuItem searchItem = menu.findItem(R.id.search_view);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {

                    Log.i("onQueryTextChange", newText);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((fragmentActivity));
                    sharedPreferences.edit().putString(QUERY_DEBTORS, newText).apply();

                    searchViewQuery.searchViewQueryChanged(newText);

                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
//            searchView.setOnQueryTextListener(queryTextListener);

        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_view:
                Log.i(TAG, "onOptionsItemSelected case R.id.allclients_search:");
                // Not implemented here
                return false;
            case R.id.menu_debtors_max_amount:
                Log.i(TAG, "onOptionsItemSelected: menu_debtors_max_amount");
                return true;
            case R.id.menu_debtors_min_amount:
                Log.i(TAG, "onOptionsItemSelected: menu_debtors_min_amount");
                return true;
            case R.id.menu_debtors_max_date:
                Log.i(TAG, "onOptionsItemSelected: menu_debtors_max_date");
                return true;
            case R.id.menu_debtors_min_date:
                Log.i(TAG, "onOptionsItemSelected: menu_debtors_min_date");
                return true;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: START");
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;
//
//        try{
//            searchViewQuery = (SearchViewQuery) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement ");
//        }
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: START");
        super.onDetach();
    }

}
