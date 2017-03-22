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
import com.example.android.debtors.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

/**
 * Created by admin on 19.03.2017.
 */

public class DialogMenuAllClients extends DialogFragment implements View.OnClickListener{

    private static final String TAG = DialogMenuAllClients.class.getSimpleName();

//    private EditText editTextFrom, editTextTo = null;
//    private TextView validateFrom, validateTo, textViewError = null;
    private TextView textViewError = null;

    private Button buttonApply,buttonCancel;

    private FragmentActivity fragmentActivity;

    private View view = null;

    private RangeSeekBar rangeSeekBarTextColorWithCode = null;

    private CallbackMenuAllclientDialog callbackMenuDialog;

    private DatabaseClients dbClients  = null;

    public static DialogMenuAllClients newInstance() {
        DialogMenuAllClients f = new DialogMenuAllClients();

        return f;
    }

    public DialogMenuAllClients() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackMenuDialog = (CallbackMenuAllclientDialog) getParentFragment();
//        callbackMenuDialog = fragmentActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_menu_allclients, container, false);

        textViewError = (TextView) view.findViewById(R.id.dialog_allclient_menu_error);

        rangeSeekBarTextColorWithCode = (RangeSeekBar) view.findViewById(R.id.dialog_allclients_menu_rangeseekbar);
        //TODO SHARED PREFERENCES TO MAX HIGH RANGE
        rangeSeekBarTextColorWithCode.setRangeValues(-5000,5000);

        rangeSeekBarTextColorWithCode.setTextAboveThumbsColorResource(android.R.color.holo_red_dark);

        buttonApply = (Button) view.findViewById(R.id.dialog_allclient_menu_apply);
        buttonCancel = (Button) view.findViewById(R.id.dialog_allclient_menu_cancel);

        buttonApply.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;

//        callbackMenuDialog = (CallbackMenuAllclientDialog) fragmentActivity;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonApply.getId()){

            int minAmount = (Integer) rangeSeekBarTextColorWithCode.getSelectedMinValue();
            Log.i(TAG, "onClick: minAmount: " + minAmount);

            int maxAmount = (Integer) rangeSeekBarTextColorWithCode.getSelectedMaxValue();
            Log.i(TAG, "onClick: maxAmount: " + maxAmount);

            if(maxAmount == (Integer) rangeSeekBarTextColorWithCode.getAbsoluteMaxValue()){
                dbClients = new DatabaseClients(fragmentActivity);
                maxAmount = dbClients.getClientWithHighestLeftAmount();
                Log.i(TAG, "onClick: a tukej: " + maxAmount);
            } else if(minAmount == (Integer) rangeSeekBarTextColorWithCode.getAbsoluteMinValue()){
                dbClients = new DatabaseClients(fragmentActivity);
                minAmount = dbClients.getClientWithLowestLeftAmount();
                Log.i(TAG, "onClick: najmniejsza: " + minAmount);
            }

            callbackMenuDialog.reloadRecycler(minAmount, maxAmount);
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
