package com.example.android.debtors.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.R;

/**
 * Created by admin on 03.03.2017.
 */

public class DialogPayment extends Dialog{
    private static final String TAG = DialogPayment.class.getSimpleName();

    private Spinner newPaymentSpinner;
    private EditText newPaymentAmount;
    private TextView newPaymentError;
    private ToggleButton newPaymentToggle;

    private Context context;
    private DatabaseClients dbClients;

    public DialogPayment(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO co robi \/?
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_payment);

        newPaymentAmount = (EditText) findViewById(R.id.dialog_payme)


    }
}
