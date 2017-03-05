package com.example.android.debtors.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.R;

import org.w3c.dom.Text;

/**
 * Created by admin on 05.03.2017.
 */

public class DialogTransaction extends Dialog implements View.OnClickListener {

    private static final String TAG = DialogTransaction.class.getSimpleName();

    private boolean typeOfTransaction;

    private Spinner newTransactionSpinner;
    private EditText newTransactionQuantity;
    private EditText newTransactionProductValue;
    private EditText newTransactionEntryPayment;
    private RadioGroup newTransactionRadioGroup;
    private RadioButton newTransactionRadioSale;
    private RadioButton newTransactionRadioPurchase;

    private TextView newTransactionError;

    private Button newTransactionButtonOk;
    private Button newTransactionButtonCancel;

    private Context context;
    private DatabaseClients dbClients;

    public DialogTransaction(Context context) {
        super(context);
    }

    public DialogTransaction(Context context, boolean type) {
        super(context);
        this.typeOfTransaction = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_transaction);

        newTransactionSpinner = (Spinner) findViewById(R.id.dialog_transaction_spinner);

        newTransactionQuantity = (EditText) findViewById(R.id.dialog_transaction_quantity);
        newTransactionProductValue = (EditText) findViewById(R.id.dialog_transaction_productvalue);
        newTransactionEntryPayment = (EditText) findViewById(R.id.dialog_transaction_entrypayment);

        newTransactionRadioGroup = (RadioGroup) findViewById(R.id.dialog_transaction_radiogroup);
        newTransactionRadioPurchase = (RadioButton) findViewById(R.id.dialog_transaction_purchase);
        newTransactionRadioSale = (RadioButton) findViewById(R.id.dialog_transaction_sale);

        newTransactionError = (TextView) findViewById(R.id.dialog_transaction_error);

        if(typeOfTransaction)
            newTransactionRadioSale.setChecked(true);
        else
            newTransactionRadioPurchase.setChecked(true);




    }

    @Override
    public void onClick(View v) {

    }
}
