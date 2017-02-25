package com.example.android.debtors.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.debtors.Adapters.CategoryAdapterClientInfo;
import com.example.android.debtors.R;

//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link FragmentSingleClientInfo.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link FragmentSingleClientInfo#newInstance} factory method to
// * create an instance of this fragment.
// */
public class FragmentSingleClientInfo extends Fragment {

    private static final String TAG = FragmentSingleClientInfo.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

    private FragmentActivity fragmentActivity;
    private long clientID;

    public FragmentSingleClientInfo() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FragmentSingleClientInfo.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FragmentSingleClientInfo newInstance(String param1, String param2) {
//        FragmentSingleClientInfo fragment = new FragmentSingleClientInfo();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle readenArguments = getArguments();
        clientID = readenArguments.getLong("id");
        Log.i(TAG, "onCreateView: readen clientID : " + clientID);

        return inflater.inflate(R.layout.fragment_single_client_info, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.clientsinfo_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.clientsinfo_tabs);

        CategoryAdapterClientInfo categoryAdapterClientInfo = new CategoryAdapterClientInfo(getChildFragmentManager());
        viewPager.setAdapter(categoryAdapterClientInfo);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onAttach(Context context) {
        fragmentActivity = (FragmentActivity) context;
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
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
