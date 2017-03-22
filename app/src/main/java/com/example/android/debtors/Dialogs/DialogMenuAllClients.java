package com.example.android.debtors.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Interfaces.CallbackAllclientMenuDialog;
import com.example.android.debtors.R;
import com.example.android.debtors.Utils.Utils;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private CallbackAllclientMenuDialog callbackMenuDialog;

    private DatabaseClients dbClients  = null;

    public static DialogMenuAllClients newInstance(int num) {
        DialogMenuAllClients f = new DialogMenuAllClients();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    public DialogMenuAllClients() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackMenuDialog = (CallbackAllclientMenuDialog) getParentFragment();
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
//        rangeSeekBarTextColorWithCode.
//        rangeSeekBarTextColorWithCode.setSelectedMinValue(100);
//        rangeSeekBarTextColorWithCode.setSelectedMaxValue(900);
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

//        callbackMenuDialog = (CallbackAllclientMenuDialog) fragmentActivity;
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
