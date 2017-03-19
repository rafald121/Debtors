package com.example.android.debtors.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Output;
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
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.debtors.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by admin on 19.03.2017.
 */

public class DialogAllClientsMenu extends DialogFragment {

    private static final String TAG = DialogAllClientsMenu.class.getSimpleName();

    private TextView textViewFrom, textViewTo = null;
    private Button buttonApply,buttonCancel;
    private int year;
    private int month;
    private int day;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat simpleDateFormat;

    private FragmentActivity fragmentActivity;

    private View view = null;
    private RangeSeekBar<Integer> rangeSeekBar = null;

    public static DialogAllClientsMenu newInstance(int num) {
        DialogAllClientsMenu f = new DialogAllClientsMenu();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    public DialogAllClientsMenu() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        view = inflater.inflate(R.layout.dialog_allclients_menu, container, false);
        textViewFrom = (EditText) view.findViewById(R.id.dialog_allclient_menu_edittext_from);
        textViewTo = (EditText) view.findViewById(R.id.dialog_allclient_menu_edittext_to);

        rangeSeekBar = new RangeSeekBar<Integer>(fragmentActivity);

        


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;

    }
}
