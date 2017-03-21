package com.example.android.debtors.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Output;
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
import android.widget.FrameLayout;

import com.example.android.debtors.R;
import com.example.android.debtors.Utils.Utils;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 19.03.2017.
 */

public class DialogAllClientsMenu extends DialogFragment {

    private static final String TAG = DialogAllClientsMenu.class.getSimpleName();

    private EditText editTextFrom, editTextTo = null;
    private Button buttonApply,buttonCancel;

    private int year;
    private int month;
    private int day;

    private Date date = null;
//
//    private DatePickerDialog fromDatePickerDialog;
//    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat simpleDateFormat;

    private FragmentActivity fragmentActivity;

    private View view = null;

    RangeSeekBar rangeSeekBarTextColorWithCode = null;
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

        view = inflater.inflate(R.layout.dialog_allclients_menu, container, false);

        editTextFrom = (EditText) view.findViewById(R.id.dialog_allclient_menu_edittext_from);
        editTextTo = (EditText) view.findViewById(R.id.dialog_allclient_menu_edittext_to);

        editTextFrom.setText(Utils.getDate());
        editTextTo.setText(Utils.getDate());

//        rangeSeekBar = new RangeSeekBar<Integer>(fragmentActivity);
//
//        rangeSeekBar.setRangeValues(0, 1000);
//        rangeSeekBar.setSelectedMinValue(0);
//        rangeSeekBar.setSelectedMaxValue(10000);
//        rangeSeekBar.setColor(R.color.colorPrimary);

        rangeSeekBarTextColorWithCode = (RangeSeekBar) view.findViewById(R.id.dialog_allclients_menu_rangeseekbar);
        //TODO SHARED PREFERENCES TO MAX HIGH RANGE
        rangeSeekBarTextColorWithCode.setRangeValues(0,1000);
        rangeSeekBarTextColorWithCode.setSelectedMinValue(100);
        rangeSeekBarTextColorWithCode.setSelectedMaxValue(900);
        rangeSeekBarTextColorWithCode.setTextAboveThumbsColorResource(android.R.color.holo_red_dark);


        editTextFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.i(TAG, "beforeTextChanged: char sequence: " + s + " start: " + start + );
//                Log.d(TAG, "beforeTextChanged() called with: s = [" + s + "], start = [" + start + "], count = [" + count + "], after = [" + after + "]");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d(TAG, "onTextChanged() called with: s = [" + s + "], start = [" + start + "], before = [" + before + "], count = [" + count + "]");

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validate(s))
                    editTextFrom.setBackgroundResource(R.color.green);
                else
                    editTextFrom.setBackgroundResource(R.color.red);
//                Log.d(TAG, "afterTextChanged() called with: s = [" + s.toString() + "]");
            }
        });


        return view;
    }

    private boolean validate(Editable s) {
        String string =  s.toString();
        if(string.length()==10) {

            String[] dateSplit = string.split("-");

            if (dateSplit.length == 3) {

                if(dateSplit[0].length()==2 && dateSplit[1].length()==2 && dateSplit[2].length()==4){

                    if(Integer.parseInt(dateSplit[0]) >= 1   && Integer.parseInt(dateSplit[0]) <= 30 )

                        if(Integer.parseInt(dateSplit[1]) >= 1 && Integer.parseInt(dateSplit[0])  <= 12 )

                            if (Integer.parseInt(dateSplit[2]) > 1970 && Integer.parseInt(dateSplit[0]) < 2018 )
                                return true;

                            else {
                                Log.e(TAG, "validate: blad w roku");
                                return false;
                            }

                        else{
                            Log.e(TAG, "validate: blad w miesiacach");
                            return false;
                        }

                    else {
                        Log.e(TAG, "validate: blad w dniach ");
                        return false;
                    }

                } else {
                    Log.e(TAG, "validate: zly format między myslnikami");
                    return false;
                }

            }
            else {
                Log.e(TAG, "validate: zly format, nie ma dwóch myslinikow");
                return false;
            }
        } else {
            Log.e(TAG, "validate: dlugosc nei równa się 10, a równa sie: " +  string.length());
            return false;
        }
//        if(s.length()>10)
//            return false;
//        else if(s.length()<)
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;

    }
}
