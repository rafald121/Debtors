package com.example.android.debtors.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Interfaces.CallbackMenuAllclientDialog;
import com.example.android.debtors.Interfaces.CallbackMenuDebtorsDialog;
import com.example.android.debtors.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

/**
 * Created by admin on 22.03.2017.
 */
public class DialogMenuDebtors extends DialogFragment implements View.OnClickListener{

    private static final String TAG = DialogMenuDebtors.class.getSimpleName();

    private TextView textViewError = null;

    private Button buttonApply,buttonCancel = null;

    private FragmentActivity fragmentActivity = null;

    private View view = null;

    private RangeSeekBar rangeSeekBar = null;

    private CallbackMenuDebtorsDialog callbackMenuDebtorsDialog = null;

    private DatabaseClients dbClients  = null;

    public static DialogMenuDebtors newInstance() {

        DialogMenuDebtors dialogMenuDebtors = new DialogMenuDebtors();
        return dialogMenuDebtors;
    }

    public DialogMenuDebtors(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackMenuDebtorsDialog = (CallbackMenuDebtorsDialog) getParentFragment().getParentFragment();
//        callbackMenuDialog = fragmentActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_menu_debtors, container, false);

        textViewError = (TextView) view.findViewById(R.id.dialog_menu_debtors_error);

        rangeSeekBar = (RangeSeekBar) view.findViewById(R.id.dialog_menu_debtors_rangeseekbar);
        
        //TODO SHARED PREFERENCES TO MAX HIGH RANGE
        rangeSeekBar.setRangeValues(-5000,5000);

        rangeSeekBar.setTextAboveThumbsColorResource(android.R.color.holo_red_dark);

        buttonApply = (Button) view.findViewById(R.id.dialog_menu_debtors_apply);
        buttonCancel = (Button) view.findViewById(R.id.dialog_menu_debtors_cancel);

        buttonApply.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonApply.getId()){

            int minAmount = (Integer) rangeSeekBar.getSelectedMinValue();
            Log.i(TAG, "onClick: minAmount: " + minAmount);

            int maxAmount = (Integer) rangeSeekBar.getSelectedMaxValue();
            Log.i(TAG, "onClick: maxAmount: " + maxAmount);

            if(maxAmount == (Integer) rangeSeekBar.getAbsoluteMaxValue()){
                dbClients = new DatabaseClients(fragmentActivity);
                maxAmount = dbClients.getClientWithHighestLeftAmount();
                Log.i(TAG, "onClick: a tukej: " + maxAmount);
            } else if(minAmount == (Integer) rangeSeekBar.getAbsoluteMinValue()){
                dbClients = new DatabaseClients(fragmentActivity);
                minAmount = dbClients.getClientWithLowestLeftAmount();
                Log.i(TAG, "onClick: najmniejsza: " + minAmount);
            }

            callbackMenuDebtorsDialog.reloadRecycler(minAmount, maxAmount);
            //query
            dismiss();
        } else if(v.getId() == buttonCancel.getId()){
            dismiss();
            Log.e(TAG, "onClick: eeee????????");
        } else {
            Log.e(TAG, "onClick: eeee");
        }
    }
  
}
