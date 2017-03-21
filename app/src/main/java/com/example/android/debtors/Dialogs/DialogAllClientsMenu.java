package com.example.android.debtors.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Output;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.os.CancellationSignal;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.android.debtors.R;
import com.example.android.debtors.Utils.Utils;

import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 19.03.2017.
 */

public class DialogAllClientsMenu extends DialogFragment implements View.OnClickListener{

    private static final String TAG = DialogAllClientsMenu.class.getSimpleName();

    private EditText editTextFrom, editTextTo = null;
    private TextView validateFrom, validateTo, textViewError = null;

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

        validateFrom = (TextView) view.findViewById(R.id.dialog_allclient_menu_from_validate);
        validateTo = (TextView) view.findViewById(R.id.dialog_allclient_menu_to_validate);
        textViewError = (TextView) view.findViewById(R.id.dialog_allclient_menu_error);

        editTextFrom.setText(Utils.getDate());
        editTextTo.setText(Utils.getDate());

        validate(editTextFrom.getText(),true);
        validate(editTextTo.getText(),false);

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

        buttonApply = (Button) view.findViewById(R.id.dialog_allclient_menu_apply);
        buttonCancel = (Button) view.findViewById(R.id.dialog_allclient_menu_cancel);

        buttonApply.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

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
                if(validate(s,true)) {
                    editTextFrom.setBackgroundResource(R.color.green);
                    validateFrom.setText("Good format!");
                }
                else {
                    editTextFrom.setBackgroundResource(R.color.red);
                }
//                Log.d(TAG, "afterTextChanged() called with: s = [" + s.toString() + "]");
            }
        });

        editTextTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validate(s,false)) {
                    editTextTo.setBackgroundResource(R.color.green);
                    validateTo.setText("Good format!");
                }
                else {
                    editTextTo.setBackgroundResource(R.color.red);
                }
            }
        });


        return view;
    }


    private boolean validate(Editable s, boolean bool) {
        String string =  s.toString();

        if(string.length()==10) {

            String[] dateSplit = string.split("-");

            if (dateSplit.length == 3) {

                if(dateSplit[0].length()==2 && dateSplit[1].length()==2 && dateSplit[2].length()==4) {

                    if (Integer.parseInt(dateSplit[2]) > 1970 && Integer.parseInt(dateSplit[2]) < 2018) {

                        if (Integer.parseInt(dateSplit[1]) >= 1 && Integer.parseInt(dateSplit[1]) <= 12){

//                            KWIE CZER WRZE LIS
                            if (Integer.parseInt(dateSplit[1]) == 4 || Integer.parseInt(dateSplit[1]) == 6 || Integer.parseInt(dateSplit[1]) == 9 || Integer.parseInt(dateSplit[1]) == 11) {

                                if (Integer.parseInt(dateSplit[0]) >= 1 && Integer.parseInt(dateSplit[0]) <= 30) {
                                    return true;
                                } else {
                                    Log.e(TAG, "validate: kwie czer wrze lis musi miec 30 dni");
                                    validateFrom("These month has to has 30 days", bool);
                                    return false;
                                }
//                            LUTY
                            } else if(Integer.parseInt(dateSplit[1])==2){
                                if (Integer.parseInt(dateSplit[0]) >= 1 && Integer.parseInt(dateSplit[0]) <= 28) {
                                    return true;
                                } else {
                                    Log.e(TAG, "validate: luty!!");
                                    validateFrom("February has to has 28 days", bool);
                                    return false;
                                }
//                              ZWYKLY INNY MIESIAC
                            } else {
                                if (Integer.parseInt(dateSplit[0]) >= 1 && Integer.parseInt(dateSplit[0]) <= 31) {
                                    return true;
                                } else {
                                    Log.e(TAG, "validate: ZWYKLY MIESIAC MA 31 dni !!");
                                    validateFrom("Usual month has 31 days", bool);
                                    return false;
                                }
                            }

                        } else {
                            Log.e(TAG, "validate: blad w miesiacach");
                            validateFrom("Bad month format", bool);
                            return false;
                        }

                    }else {
                        Log.e(TAG, "validate: blad w roku");
                        validateFrom("Type year between 1970 and 2017", bool);
                        return false;
                    }

                } else {
                    Log.e(TAG, "validate: zly format między myslnikami");
                    validateFrom("Bad colon format", bool);
                    return false;
                }
            } else {
                Log.e(TAG, "validate: zly format, nie ma dwóch myslinikow");
                validateFrom("Lack of two semicolon", bool);
                return false;
            }
        } else {
            Log.e(TAG, "validate: dlugosc nei równa się 10, a równa sie: " +  string.length());
            validateFrom("Date has to has 10 sign length",bool);
            return false;
        }

    }

    private void validateFrom(String text, boolean bool){
        if(bool)// from
            validateFrom.setText(text);
        else // to
            validateTo.setText(text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonApply.getId()){
            Log.i(TAG, "onClick: kurwa klikaj sie");
            String dateFromString = editTextFrom.getText().toString();
            String dateToString = editTextTo.getText().toString();

            Date dateFrom = getDateFromString(dateFromString);
            Date dateTo = getDateFromString(dateToString);

            int minAmount = (Integer) rangeSeekBarTextColorWithCode.getSelectedMinValue();
            Log.i(TAG, "onClick: minAmount: " + minAmount);

            int maxAmount = (Integer) rangeSeekBarTextColorWithCode.getSelectedMaxValue();
            Log.i(TAG, "onClick: maxAmount: " + maxAmount);

            if(dateFrom.before(dateTo)){
                Log.i(TAG, "onClick: wyszukuje: dateFrom: " + dateFrom.toString() + " dateTo: " + dateTo.toString() + minAmount + maxAmount);
            } else
                textViewError.setText("From date must be before To Date");

        } else if(v.getId() == buttonCancel.getId()){
            dismiss();
            Log.e(TAG, "onClick: eeee????????");
        } else {
            Log.e(TAG, "onClick: eeee");
        }
    }

    public Date getDateFromString(String stringDate){
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = dateformat.parse(stringDate);
        } catch (ParseException e) {
            Log.e(TAG, "getDateFromString: ERROR WHILE PARSING  ");
            e.printStackTrace();
        }
        return date;
    }
}
