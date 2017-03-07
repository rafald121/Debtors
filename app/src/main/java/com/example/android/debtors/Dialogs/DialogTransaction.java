package com.example.android.debtors.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.load.data.StreamAssetPathFetcher;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseOwner;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;
import com.example.android.debtors.Utils.Utils;

import org.w3c.dom.Text;

import java.util.List;

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
    private EditText newTransactionDetails;
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
        this.context = context;
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
        newTransactionDetails = (EditText) findViewById(R.id.dialog_transaction_details);

        newTransactionRadioGroup = (RadioGroup) findViewById(R.id.dialog_transaction_radiogroup);
        newTransactionRadioPurchase = (RadioButton) findViewById(R.id.dialog_transaction_purchase);
        newTransactionRadioSale = (RadioButton) findViewById(R.id.dialog_transaction_sale);

        newTransactionError = (TextView) findViewById(R.id.dialog_transaction_error);

        if(typeOfTransaction)
            newTransactionRadioSale.setChecked(true);
        else
            newTransactionRadioPurchase.setChecked(true);

        newTransactionButtonOk = (Button) findViewById(R.id.dialog_transaction_ok);
        newTransactionButtonCancel = (Button) findViewById(R.id.dialog_transaction_cancel);

        List<Client> listOfClients = getListOfClients();

        ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(context, android.R.layout.simple_spinner_dropdown_item, listOfClients);

        newTransactionSpinner.setAdapter(adapter);

        newTransactionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Client client = (Client) parent.getSelectedItem();

                Log.i(TAG, "onItemSelected: "+ client.toString(true));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        newTransactionButtonOk.setOnClickListener(this);
        newTransactionButtonCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == newTransactionButtonOk.getId()){

            DatabaseOwner dbOwner = new DatabaseOwner(context);
            DatabaseClients dbClients = new DatabaseClients(context);

            Owner owner = dbOwner.getOwner(1);

            Client selectedClient = (Client) newTransactionSpinner.getSelectedItem();

            int selectedClientId = selectedClient.getClientId();

            String transactionDetails;
            int transactionQuantity;
            int transactionProductValue;
            int transactionEntryPayment;

            boolean _type ;

            if(newTransactionQuantity.getText().toString().equals("")){
                newTransactionError.setText("Transaction quantity has to be over 0");
                return;
            } else if(Integer.parseInt(newTransactionQuantity.getText().toString()) == 0){
                newTransactionError.setText("Transaction quantity has to be over 0");
                return;
            } else
                transactionQuantity = Integer.parseInt(newTransactionQuantity.getText().toString());


            if(newTransactionProductValue.getText().toString().equals("")){
                newTransactionError.setText("Product value has to be over 0");
                return;
            } else if(Integer.parseInt(newTransactionProductValue.getText().toString()) == 0){
                newTransactionError.setText("Product value has to be over 0");
                return;
            } else
                transactionProductValue = Integer.parseInt(newTransactionProductValue.getText().toString());

            transactionEntryPayment = Integer.parseInt(newTransactionEntryPayment.getText().toString());

            transactionDetails = newTransactionDetails.getText().toString();

            if(newTransactionRadioSale.isChecked())
                typeOfTransaction = true;
            else if(newTransactionRadioPurchase.isChecked())
                typeOfTransaction = false;
            else {
                Log.e(TAG, "onClick: weird error");
                return;
            }

//            TransactionForClient transaction = new TransactionForClient(Utils.getDateTime(), )
            
        } else if(v.getId() == newTransactionButtonCancel.getId()){
            dismiss();
        }
    }

    private List<Client> getListOfClients(){
        DatabaseClients dbClients = new DatabaseClients(context);

        List<Client> listOfClients = dbClients.getAllClient();

        return listOfClients;
    }

    private List<String> getListOfClientsNames(){
        DatabaseClients dbClients = new DatabaseClients(context);

        List<String> list = dbClients.getListOfClientsNames();

        return list;
    }
}
