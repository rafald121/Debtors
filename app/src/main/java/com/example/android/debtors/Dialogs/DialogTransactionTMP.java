package com.example.android.debtors.Dialogs;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseOwner;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Logic.RealizeTransactionHelper;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;
import com.example.android.debtors.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 26.03.2017.
 */

public class DialogTransactionTMP extends DialogFragment implements View.OnClickListener {


    private static final String TAG = DialogTransactionTMP.class.getSimpleName();

    private boolean typeOfTransaction;
    private long clientID = -1;
    private Client firstClientInSpinner = null;

    private View view = null;
    
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

    private ArrayAdapter<Client> adapter = null;

    private Context context;
    private DatabaseClients dbClients;

    private CallbackAddInDialog callbackAddInDialog = null;

    private List<Client> listOfClientsInOrder = new ArrayList<>();

    private FragmentActivity fragmentActivity = null;

    public static DialogTransactionTMP newInstance() {
        DialogTransactionTMP fragment = new DialogTransactionTMP();
        return fragment;
    }

    public static DialogTransactionTMP newInstance(boolean typeOfTransaction) {
        DialogTransactionTMP fragment = new DialogTransactionTMP();
        fragment.typeOfTransaction = typeOfTransaction;
        return fragment;
    }

    public DialogTransactionTMP() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_transaction, container, false);
        
        newTransactionSpinner = (Spinner) view.findViewById(R.id.dialog_transaction_spinner);

        newTransactionQuantity = (EditText) view.findViewById(R.id.dialog_transaction_quantity);
        newTransactionProductValue = (EditText) view.findViewById(R.id.dialog_transaction_productvalue);
        newTransactionEntryPayment = (EditText) view.findViewById(R.id.dialog_transaction_entrypayment);
        newTransactionDetails = (EditText) view.findViewById(R.id.dialog_transaction_details);

        newTransactionRadioGroup = (RadioGroup) view.findViewById(R.id.dialog_transaction_radiogroup);
        newTransactionRadioPurchase = (RadioButton) view.findViewById(R.id.dialog_transaction_purchase);
        newTransactionRadioSale = (RadioButton) view.findViewById(R.id.dialog_transaction_sale);

        newTransactionError = (TextView) view.findViewById(R.id.dialog_transaction_error);

        if(typeOfTransaction)
            newTransactionRadioSale.setChecked(true);
        else
            newTransactionRadioPurchase.setChecked(true);

        newTransactionButtonOk = (Button) view.findViewById(R.id.dialog_transaction_ok);
        newTransactionButtonCancel = (Button) view.findViewById(R.id.dialog_transaction_cancel);

//        listOfClientsInOrder = getListOfClientsInOrder(10);



        if(firstClientInSpinner != null)
            if(!listOfClientsInOrder.get(0).equals(firstClientInSpinner)){
                List<Client> list = swapIndex(listOfClientsInOrder, firstClientInSpinner);
                adapter = new ArrayAdapter<>(fragmentActivity, android.R.layout.simple_spinner_dropdown_item, list);

            } else
                Log.i(TAG, "onCreate: client is on first index, good!");
        else
            adapter = new ArrayAdapter<>(fragmentActivity, android.R.layout.simple_spinner_dropdown_item, listOfClientsInOrder);


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

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == newTransactionButtonOk.getId()){

            DatabaseOwner dbOwner = new DatabaseOwner(fragmentActivity);
            DatabaseClients dbClients = new DatabaseClients(fragmentActivity);

            Owner owner = dbOwner.getOwner(1);

            Client selectedClient = (Client) newTransactionSpinner.getSelectedItem();

            Log.i(TAG, "onClick: selectedClient from spinner: " + selectedClient.toString());

            int selectedClientId = selectedClient.getClientId();

            String transactionDetails = "";
            int transactionQuantity;
            int transactionProductValue;
            int transactionEntryPayment = 0;

            boolean _typeOfTransaction ;

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

            if(!newTransactionEntryPayment.getText().toString().equals(""))
                transactionEntryPayment = Integer.parseInt(newTransactionEntryPayment.getText().toString());

            if(!newTransactionDetails.getText().toString().equals(""))
                transactionDetails = newTransactionDetails.getText().toString();

            if(newTransactionRadioSale.isChecked())
                _typeOfTransaction = true;
            else if(newTransactionRadioPurchase.isChecked())
                _typeOfTransaction = false;
            else {
                Log.e(TAG, "onClick: weird error");
                return;
            }

            TransactionForClient transaction = new TransactionForClient(Utils.getDateTime(), owner.getOwnerID(), selectedClient.getClientId(),  transactionQuantity, transactionProductValue, transactionEntryPayment, transactionDetails, _typeOfTransaction);

            Log.i(TAG, "onClick: transaction: " + transaction.toString());

            Client client = dbClients.getClientByID(selectedClientId);

            Log.i(TAG, "onClick: before tranzaction: " + client.toString(true));

            RealizeTransactionHelper realizeTransactionHelper = new RealizeTransactionHelper();
            realizeTransactionHelper.realizeTransaction(fragmentActivity, transaction);

//            dbClients.updateClient(client);

            Client client1 = dbClients.getClientByID(selectedClientId);

            Log.i(TAG, "onClick: after tranzaction: " + client1.toString(true));

            callbackAddInDialog.reloadRecycler();

            dismiss();

        } else if(v.getId() == newTransactionButtonCancel.getId()){
            dismiss();
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
        DatabaseClients dbClients = new DatabaseClients(fragmentActivity);

        return dbClients.getClientByID(clientID);
    }

    private List<Client> getListOfClientsInOrder(int fromLastDays){
        DatabaseClients dbClients = new DatabaseClients(fragmentActivity);
        DatabaseTransactions dbTransactions = new DatabaseTransactions(fragmentActivity);

        int[][] array = dbTransactions.getArrayMapWithMostCommonClients(fromLastDays);

        List<Client> list = new ArrayList<>();

        for (int i = 0 ; i < array.length ; i ++){

            Client client = dbClients.getClientByID(array[i][0]);

            list.add(client);
        }

        list.toString();

        return list;
    }

    private List<Client> getListOfClients(){
        DatabaseClients dbClients = new DatabaseClients(fragmentActivity);

        List<Client> listOfClients = dbClients.getAllClient();

        return listOfClients;
    }

    private List<String> getListOfClientsNames(){
        DatabaseClients dbClients = new DatabaseClients(fragmentActivity);

        List<String> list = dbClients.getListOfClientsNames();

        return list;
    }

}
