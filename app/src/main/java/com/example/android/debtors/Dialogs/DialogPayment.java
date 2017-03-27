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

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseOwner;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Logic.RealizePaymentHelper;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.R;
import com.example.android.debtors.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 03.03.2017.
 */

public class DialogPayment extends Dialog implements View.OnClickListener{
    private static final String TAG = DialogPayment.class.getSimpleName();

    private boolean type;
    private long clientID = -1;
    private Client firstClientInSpinner = null;

    private Spinner newPaymentSpinner;
    private EditText newPaymentAmount;
    private EditText newPaymentDetails;
    private TextView newPaymentError;
    private RadioGroup newPaymentRadioGroup;
    private RadioButton newPaymentRadioReceived;
    private RadioButton newPaymentRadioGiven;

    private Button newPaymentOk, newPaymentCancel;

    private ArrayAdapter<Client> adapter;

    private Context context;
    private DatabaseClients dbClients;
    private DatabasePayments dbPayments;

    private CallbackAddInDialog callbackAddInDialog = null;

    List<Client> listOfClientsInOrder = new ArrayList<>();

    public DialogPayment(Context context) {
        super(context);
        this.context = context;
    }

    public DialogPayment(Context context, boolean type) {
        super(context);
        this.context = context;
        this.type=type;
    }

    public DialogPayment(Context context, boolean type, CallbackAddInDialog callback){
        super(context);
        this.context = context;
        this.type = type;
        this.callbackAddInDialog = callback;
    }

    public DialogPayment(Context context, long clientID, CallbackAddInDialog callbackAddInDialog){
        super(context);
        this.context = context;
        this.clientID = clientID;
        this.callbackAddInDialog = callbackAddInDialog;
        this.firstClientInSpinner = getClientById(clientID);
        Log.i(TAG, "DialogPayment: firstClientInSpinner: " + firstClientInSpinner.toString());
        //TODO
        // type depends what type of payments client from clientID taken

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_new_payment);

        newPaymentAmount = (EditText) findViewById(R.id.dialog_payment_amount);
        newPaymentAmount.setText("");

        newPaymentDetails = (EditText) findViewById(R.id.dialog_payment_details);

        newPaymentError = (TextView) findViewById(R.id.dialog_payment_error);
        newPaymentError.setText("");

        newPaymentSpinner = (Spinner) findViewById(R.id.dialog_payment_spinner);

        newPaymentRadioGroup = (RadioGroup) findViewById(R.id.dialog_payment_radio);
        newPaymentRadioReceived = (RadioButton) findViewById(R.id.dialog_payment_received);
        newPaymentRadioGiven= (RadioButton) findViewById(R.id.dialog_payment_given);

        if(type)
            newPaymentRadioReceived.setChecked(true);
        else
            newPaymentRadioGiven.setChecked(true);

        newPaymentOk = (Button) findViewById(R.id.dialog_payment_ok);
        newPaymentCancel = (Button) findViewById(R.id.dialog_payment_cancel);

        listOfClientsInOrder = getListOfClientsInMostCommonOrder(10);

        if(firstClientInSpinner != null){

            if(!listOfClientsInOrder.get(0).equals(firstClientInSpinner)) {
                List<Client> list = swapIndex(listOfClientsInOrder, firstClientInSpinner);
                adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);

            } else
                Log.i(TAG, "onCreate: client is on first index, good!");
        } else
            adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listOfClientsInOrder);

        newPaymentSpinner.setAdapter(adapter);


        newPaymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Client client = (Client) parent.getSelectedItem();
                Log.i(TAG, "onItemSelected: "+ client.toString(true));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        newPaymentOk.setOnClickListener(this);
        newPaymentCancel.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if(v.getId() == newPaymentOk.getId()) {
            Log.i(TAG, "onClick: ok");

            DatabaseOwner dbOwner = new DatabaseOwner(context);

//            TODO ZMIENIC RAW OWNER NA ZALOGOWANEGO
            Owner owner = dbOwner.getOwner(1);

            Client selectedClient = (Client) newPaymentSpinner.getSelectedItem();
            int selectedClientId = selectedClient.getClientId();

            String paymentDetails = newPaymentDetails.getText().toString();
            int paymentAmount = 0;

            if ((newPaymentAmount.getText().toString().equals(""))) {
                newPaymentError.setText("Payment amount has to be over 0");
                return;
            } else if (Integer.parseInt(newPaymentAmount.getText().toString()) == 0){
                newPaymentError.setText("Payment amount has to be over 0");
                return;
            } else
                paymentAmount = Integer.parseInt(newPaymentAmount.getText().toString());

            boolean typeOfPayment = false;


            if(newPaymentRadioReceived.isChecked())
                typeOfPayment=true;
            else if(newPaymentRadioGiven.isChecked())
                typeOfPayment=false;
            else {
                Log.e(TAG, "onClick: weird error");
                return;
            }

            Payment payment = new Payment(Utils.getDateTime(), owner.getOwnerID(), selectedClientId, paymentAmount, paymentDetails, typeOfPayment );

            Log.i(TAG, "onClick: payment: " + payment.toString());

            RealizePaymentHelper realizePaymentHelper = new RealizePaymentHelper();
            realizePaymentHelper.realizePayment(context, payment);

            Log.i(TAG, "onClick: przed");
            callbackAddInDialog.reloadRecycler();
            Log.i(TAG, "onClick: po");

//            TODO add snackbar when client is added
            dismiss();

        } else if(v.getId() == newPaymentCancel.getId()) {
            dismiss();
            Log.i(TAG, "onClick: cancel");
        } else if(v.getId() == newPaymentSpinner.getId()) {
            Log.i(TAG, "onClick: spinner");
        }else if(v.getId() == newPaymentRadioGroup.getId()) {
            Log.i(TAG, "onClick: radio");
        }
    }

    private List<Client> swapIndex(List<Client> listOfClientsInOrder, Client i) {
        List<Client> swappedList = new ArrayList<>();
        swappedList.add(i);
        for (Client c: listOfClientsInOrder){
            if(!c.equals(i))
                swappedList.add(c);
            else
                Log.e(TAG, "swapIndex: natrafilem na to samo: " + c.toString() );
        }

        return swappedList;
    }

    private Client getClientById(long clientID) {
        DatabaseClients dbClients = new DatabaseClients(context);

        return dbClients.getClientByID(clientID);
    }

    private List<Client> getListOfClientsInMostCommonOrder(int fromLastDays){
        DatabaseClients dbClients = new DatabaseClients(context);
        DatabasePayments dbPayments = new DatabasePayments(context);

        int[][] array = dbPayments.getArrayMapWithMostCommonClients(fromLastDays);

        List<Client> list = new ArrayList<>();

        for (int i = 0 ; i < array.length ; i ++){

            Client client = dbClients.getClientByID(array[i][0]);

            list.add(client);

        }

        list.toString();

        return list;
    }


}
