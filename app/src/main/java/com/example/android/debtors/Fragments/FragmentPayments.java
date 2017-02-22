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

import com.example.android.debtors.Adapters.CategoryAdapterPayments;
import com.example.android.debtors.Databases.DatabasePayments;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = FragmentPayments.class.getSimpleName();

    private DatabasePayments dbPayments;
    private List<Payment> listOfPayments;
//    private List<>



    private OnFragmentInteractionListener mListener;

    public FragmentPayments() {
        Log.i(TAG, "FragmentPayments: START");
        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FragmentPayments.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FragmentPayments newInstance(String param1, String param2) {
//        Log.i(TAG, "newInstance: START");
//        FragmentPayments fragment = new FragmentPayments();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        Log.i(TAG, "newInstance: END");
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: START");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: START");
        // Inflate the layout for this fragment
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        Log.i(TAG, "onButtonPressed: START");

    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: START");
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: START");
        super.onDetach();
        mListener = null;
    }

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
