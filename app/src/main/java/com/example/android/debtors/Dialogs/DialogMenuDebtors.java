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
import com.example.android.debtors.EventBus.DialogMenuDebtorsForMeApply;
import com.example.android.debtors.EventBus.DialogMenuDebtorsMeToOtherApply;
import com.example.android.debtors.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import de.greenrobot.event.EventBus;

/**
 * Created by admin on 22.03.2017.
 */
public class DialogMenuDebtors extends DialogFragment implements View.OnClickListener{

    private static final String TAG = DialogMenuDebtors.class.getSimpleName();

    private View view = null;
    private TextView textViewError = null;
    private Button buttonApply,buttonCancel = null;
    private RangeSeekBar rangeSeekBar = null;

    private FragmentActivity fragmentActivity = null;

    private DatabaseClients dbClients  = null;

    private int forMeOrMeToOther;

    public static DialogMenuDebtors newInstance(int i) {
        DialogMenuDebtors dialogMenuDebtors = new DialogMenuDebtors();

        dialogMenuDebtors.forMeOrMeToOther = i;

        return dialogMenuDebtors;
    }

    public DialogMenuDebtors(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus myEventBus = EventBus.getDefault();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: utworzono i formeormetoother = " + forMeOrMeToOther);
        view = inflater.inflate(R.layout.dialog_menu_debtors, container, false);

        textViewError = (TextView) view.findViewById(R.id.dialog_menu_debtors_error);

        rangeSeekBar = (RangeSeekBar) view.findViewById(R.id.dialog_menu_debtors_rangeseekbar);
        
        //TODO SHARED PREFERENCES TO MAX HIGH RANGE
        if(forMeOrMeToOther == 0)
            rangeSeekBar.setRangeValues(0,5000);
        else
            rangeSeekBar.setRangeValues(-5000,0);

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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonApply.getId()){

            int minAmount = (Integer) rangeSeekBar.getSelectedMinValue();
            Log.i(TAG, "onClick: minAmount: " + minAmount);

            int maxAmount = (Integer) rangeSeekBar.getSelectedMaxValue();
            Log.i(TAG, "onClick: maxAmount: " + maxAmount);

            if(forMeOrMeToOther == 0)//for me

                if(maxAmount == (Integer) rangeSeekBar.getAbsoluteMaxValue()){
                    dbClients = new DatabaseClients(fragmentActivity);
                    maxAmount = dbClients.getClientWithHighestLeftAmount();
                    Log.i(TAG, "onClick: a najwieksza: " + maxAmount);
                }

            else if(forMeOrMeToOther == 1){//me to other

                if(minAmount == (Integer) rangeSeekBar.getAbsoluteMinValue()){
                    dbClients = new DatabaseClients(fragmentActivity);
                    minAmount = dbClients.getClientWithLowestLeftAmount();
                    Log.i(TAG, "onClick: najmniejsza: " + minAmount);
                }
            }


//            if(maxAmount == (Integer) rangeSeekBar.getAbsoluteMaxValue()){
//                dbClients = new DatabaseClients(fragmentActivity);
//                maxAmount = dbClients.getClientWithHighestLeftAmount();
//                Log.i(TAG, "onClick: a tukej: " + maxAmount);
//            } else if(minAmount == (Integer) rangeSeekBar.getAbsoluteMinValue()){
//                dbClients = new DatabaseClients(fragmentActivity);
//                minAmount = dbClients.getClientWithLowestLeftAmount();
//                Log.i(TAG, "onClick: najmniejsza: " + minAmount);
//            }
            Log.d(TAG, "min: " + minAmount + " max: " + maxAmount);

            if(forMeOrMeToOther == 0)
                EventBus.getDefault().post(new DialogMenuDebtorsForMeApply(minAmount,maxAmount));
            else
                EventBus.getDefault().post(new DialogMenuDebtorsMeToOtherApply(minAmount,maxAmount));

//            callbackMenuDebtorsDialog.reloadRecycler(minAmount, maxAmount);
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
