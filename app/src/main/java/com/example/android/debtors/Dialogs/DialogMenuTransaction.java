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
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.Calendar;
import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by admin on 24.03.2017.
 */
public class DialogMenuTransaction extends DialogFragment implements View.OnClickListener{

    private static final String TAG = DialogMenuTransaction.class.getSimpleName();

    private DatabasePayments dbPayments = null;

    private TextView textViewError = null;

    private Button buttonApply,buttonCancel;
    private DatePicker fromDatePickerDialog = null;
    private DatePicker toDatePickerDialog = null;
    private RangeSeekBar rangeSeekBarQuantity = null;

    private RangeSeekBar rangeSeekBarTotalAmount = null;

    private FragmentActivity fragmentActivity;

    private View view = null;

    private Calendar calendarFrom,calendarTo = null;
    private Date dateFrom, dateTo = null;

    private int fromDay,fromMonth,fromYear = 0;
    private int toDay,toMonth,toYear = 0;

    private int minQuantity, maxQuantity = 0;
    private int minTotalAmount, maxTotalAmount = 0;

    private int typeOfTransactions;

    public static DialogMenuTransaction newInstance(int typeOfTransactions) {
        DialogMenuTransaction f = new DialogMenuTransaction();

        f.typeOfTransactions = typeOfTransactions;

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

        dbPayments = new DatabasePayments(fragmentActivity);

        view = inflater.inflate(R.layout.dialog_menu_transactions, container, false);

        fromDatePickerDialog = (DatePicker) view.findViewById(R.id.dialog_menu_transactions_fromdate);
        toDatePickerDialog = (DatePicker) view.findViewById(R.id.dialog_menu_transactions_todate);


        textViewError = (TextView) view.findViewById(R.id.dialog_menu_transactions_menu_error);

        rangeSeekBarQuantity = (RangeSeekBar) view.findViewById(R.id.dialog_menu_transactions_range_seekbar_quantity);
        rangeSeekBarQuantity.setRangeValues(0, 100);
        rangeSeekBarQuantity.setTextAboveThumbsColorResource(android.R.color.holo_red_dark);

        rangeSeekBarTotalAmount = (RangeSeekBar) view.findViewById(R.id.dialog_menu_transactions_range_seekbar_totalamount);
        //todo najwieksza wartosc z bazy danych    \/
        rangeSeekBarTotalAmount.setRangeValues(0, 2000);
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


            fromDay = fromDatePickerDialog.getDayOfMonth();
            fromMonth = fromDatePickerDialog.getMonth();
            fromYear = fromDatePickerDialog.getYear();

            toDay = toDatePickerDialog.getDayOfMonth();
            toMonth = toDatePickerDialog.getMonth();
            toYear = toDatePickerDialog.getYear();

            calendarFrom.set(fromYear, fromMonth, fromDay);
            calendarTo.set(toYear, toMonth, toDay);

            dateFrom =  calendarFrom.getTime();
            dateTo =  calendarTo.getTime();


            if(dateFrom.before(dateTo)){

                minQuantity = (Integer) rangeSeekBarQuantity.getSelectedMinValue();
                maxQuantity = (Integer) rangeSeekBarQuantity.getSelectedMaxValue();

                minTotalAmount = (Integer) rangeSeekBarTotalAmount.getSelectedMinValue();
                maxTotalAmount = (Integer) rangeSeekBarTotalAmount.getSelectedMaxValue();



            } else
                textViewError.setText("From date has to be before To Date");

        } else if(v.getId() == buttonCancel.getId()){

            dismiss();

        } else {
            Log.e(TAG, "onClick: ERROR");
        }
    }
}
