package com.example.android.debtors.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Activities.MainActivity;
import com.example.android.debtors.Adapters.CategoryAdapterDebtors;
import com.example.android.debtors.Dialogs.DialogMenuDebtors;
import com.example.android.debtors.Enum.FragmentsIDsAndTags;
import com.example.android.debtors.EventBus.SearchQuery;
import com.example.android.debtors.Interfaces.InterfaceViewPager;
import com.example.android.debtors.R;

import de.greenrobot.event.EventBus;

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
    private CategoryAdapterDebtors categoryAdapterDebtors;

    public FragmentDebtors() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_debtors, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.debtors_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.debtors_tabs);

        categoryAdapterDebtors = new CategoryAdapterDebtors(getChildFragmentManager());
        viewPager.setAdapter(categoryAdapterDebtors);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                InterfaceViewPager intefrace = (InterfaceViewPager) categoryAdapterDebtors.instantiateItem(viewPager, position);
                if (intefrace != null) {
                    Log.i(TAG, "onPageSelected: switched to: " + position);
                    switch (position){
                        case 0:
                            MainActivity.subFragmentID = FragmentsIDsAndTags.DEBTORSFORME;
                            intefrace.notifyWhenSwitched();
                            break;
                        case 1:
                            MainActivity.subFragmentID = FragmentsIDsAndTags.DEBTORSMETOOTHER;
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
            public void onPageScrollStateChanged(int state) {

            }
        });

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
                    EventBus.getDefault().post(new SearchQuery(newText));
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);

        } else Log.e(TAG, "onCreateOptionsMenu: searchView is null");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_view:
                Log.i(TAG, "onOptionsItemSelected case R.id.allclients_search:");
                // Not implemented here
                return false;
            case R.id.dialog_menu_debtors_show_dialog:
                Log.i(TAG, "onOptionsItemSelected: show dialog in debtors: " );
                showDialog();
                break;
            default:
                Log.e(TAG, "onOptionsItemSelected: ERROR ");

//            case R.id.menu_debtors_max_amount:
//                Log.i(TAG, "onOptionsItemSelected: menu_debtors_max_amount");
//                return true;
//            case R.id.menu_debtors_min_amount:
//                Log.i(TAG, "onOptionsItemSelected: menu_debtors_min_amount");
//                return true;
//            case R.id.menu_debtors_max_date:
//                Log.i(TAG, "onOptionsItemSelected: menu_debtors_max_date");
//                return true;
//            case R.id.menu_debtors_min_date:
//                Log.i(TAG, "onOptionsItemSelected: menu_debtors_min_date");
//                return true;
//            default:
//                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getChildFragmentManager().findFragmentByTag(MainActivity.TAG_DEBTORS);

        if(prev!=null)
            ft.remove(prev);
        else
            Log.e(TAG, "showDialog: prev is not null");

        ft.addToBackStack(null);

        DialogFragment d = DialogMenuDebtors.newInstance();

        d.show(getChildFragmentManager(), "dialogMenuDebtors");

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: START");
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;

    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: START");
        super.onDetach();
    }

}
