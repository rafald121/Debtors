package com.example.android.debtors.Dialogs;

import android.app.DatePickerDialog;
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
 * Created by admin on 21.03.2017.
 */


public class DialogMenuPayment extends DialogFragment implements View.OnClickListener{

    private static final String TAG = DialogMenuPayment.class.getSimpleName();

    private TextView textViewError = null;

    private Button buttonApply,buttonCancel;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private RangeSeekBar rangeSeekBar = null;

    private FragmentActivity fragmentActivity;

    private View view = null;

    public static DialogMenuPayment newInstance() {
        DialogMenuPayment f = new DialogMenuPayment();

        return f;
    }

    public DialogMenuPayment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus myEventBus = EventBus.getDefault();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_menu_payments, container, false);

        textViewError = (TextView) view.findViewById(R.id.dialog_menu_payments_menu_error);
        rangeSeekBar = (RangeSeekBar) view.findViewById(R.id.dialog_menu_payments_amount_range_seekbar);
        rangeSeekBar.setRangeValues(0, 5000);
        rangeSeekBar.setTextAboveThumbsColorResource(android.R.color.holo_red_dark);

        buttonApply = (Button) view.findViewById(R.id.dialog_menu_payments_apply);
        buttonCancel = (Button) view.findViewById(R.id.dialog_menu_payments_cancel);

        buttonApply.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        
        return view;
    }


//        private boolean validate(Editable s, boolean bool) {
//            String string =  s.toString();
//
//            if(string.length()==10) {
//
//                String[] dateSplit = string.split("-");
//
//                if (dateSplit.length == 3) {
//
//                    if(dateSplit[0].length()==2 && dateSplit[1].length()==2 && dateSplit[2].length()==4) {
//
//                        if (Integer.parseInt(dateSplit[2]) > 1970 && Integer.parseInt(dateSplit[2]) < 2018) {
//
//                            if (Integer.parseInt(dateSplit[1]) >= 1 && Integer.parseInt(dateSplit[1]) <= 12){
//
////                            KWIE CZER WRZE LIS
//                                if (Integer.parseInt(dateSplit[1]) == 4 || Integer.parseInt(dateSplit[1]) == 6 || Integer.parseInt(dateSplit[1]) == 9 || Integer.parseInt(dateSplit[1]) == 11) {
//
//                                    if (Integer.parseInt(dateSplit[0]) >= 1 && Integer.parseInt(dateSplit[0]) <= 30) {
//                                        return true;
//                                    } else {
//                                        Log.e(TAG, "validate: kwie czer wrze lis musi miec 30 dni");
//                                        validateFrom("These month has to has 30 days", bool);
//                                        return false;
//                                    }
////                            LUTY
//                                } else if(Integer.parseInt(dateSplit[1])==2){
//                                    if (Integer.parseInt(dateSplit[0]) >= 1 && Integer.parseInt(dateSplit[0]) <= 28) {
//                                        return true;
//                                    } else {
//                                        Log.e(TAG, "validate: luty!!");
//                                        validateFrom("February has to has 28 days", bool);
//                                        return false;
//                                    }
////                              ZWYKLY INNY MIESIAC
//                                } else {
//                                    if (Integer.parseInt(dateSplit[0]) >= 1 && Integer.parseInt(dateSplit[0]) <= 31) {
//                                        return true;
//                                    } else {
//                                        Log.e(TAG, "validate: ZWYKLY MIESIAC MA 31 dni !!");
//                                        validateFrom("Usual month has 31 days", bool);
//                                        return false;
//                                    }
//                                }
//
//                            } else {
//                                Log.e(TAG, "validate: blad w miesiacach");
//                                validateFrom("Bad month format", bool);
//                                return false;
//                            }
//
//                        }else {
//                            Log.e(TAG, "validate: blad w roku");
//                            validateFrom("Type year between 1970 and 2017", bool);
//                            return false;
//                        }
//
//                    } else {
//                        Log.e(TAG, "validate: zly format między myslnikami");
//                        validateFrom("Bad colon format", bool);
//                        return false;
//                    }
//                } else {
//                    Log.e(TAG, "validate: zly format, nie ma dwóch myslinikow");
//                    validateFrom("Lack of two semicolon", bool);
//                    return false;
//                }
//            } else {
//                Log.e(TAG, "validate: dlugosc nei równa się 10, a równa sie: " +  string.length());
//                validateFrom("Date has to has 10 sign length",bool);
//                return false;
//            }
//
//        }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonApply.getId()){
            Log.i(TAG, "onClick: apply in payment menu");

        } else if(v.getId() == buttonCancel.getId()){

            dismiss();

        } else {
            Log.e(TAG, "onClick: ERROR");
        }
    }

}


