package com.example.android.debtors.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
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

import com.example.android.debtors.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import de.greenrobot.event.EventBus;

/**
 * Created by admin on 24.03.2017.
 */
public class DialogMenuTransaction extends DialogFragment implements View.OnClickListener{

    private static final String TAG = DialogMenuTransaction.class.getSimpleName();

    private TextView textViewError = null;

    private Button buttonApply,buttonCancel;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private RangeSeekBar rangeSeekBarQuantity = null;
    private RangeSeekBar rangeSeekBarTotalAmount = null;

    private FragmentActivity fragmentActivity;

    private View view = null;

    public static DialogMenuTransaction newInstance() {
        DialogMenuTransaction f = new DialogMenuTransaction();

        return f;
    }

    public DialogMenuTransaction() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus myEventBus = EventBus.getDefault();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_menu_transactions, container, false);

        textViewError = (TextView) view.findViewById(R.id.dialog_menu_transactions_menu_error);

        rangeSeekBarQuantity = (RangeSeekBar) view.findViewById(R.id.dialog_menu_transactions_range_seekbar_quantity);
        rangeSeekBarQuantity.setRangeValues(0, 100);
        rangeSeekBarQuantity.setTextAboveThumbsColorResource(android.R.color.holo_red_dark);

        rangeSeekBarTotalAmount = (RangeSeekBar) view.findViewById(R.id.dialog_menu_transactions_range_seekbar_totalamount);
        //todo najwieksza wartosc z bazy danych    \/
        rangeSeekBarTotalAmount.setRangeValues(0, 200);
        rangeSeekBarTotalAmount.setTextAboveThumbsColorResource(android.R.color.holo_red_dark);

        buttonApply = (Button) view.findViewById(R.id.dialog_menu_transactions_apply);
        buttonCancel = (Button) view.findViewById(R.id.dialog_menu_transactions_cancel);

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
            Log.i(TAG, "onClick: apply in transaction menu");

        } else if(v.getId() == buttonCancel.getId()){

            dismiss();

        } else {
            Log.e(TAG, "onClick: ERROR");
        }
    }
}
