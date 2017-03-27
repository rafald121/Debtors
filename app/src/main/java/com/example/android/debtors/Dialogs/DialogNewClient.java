package com.example.android.debtors.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.R;

/**
 * Created by admin on 02.03.2017.
 */

public class DialogNewClient extends Dialog implements View.OnClickListener {

    private static final String TAG = DialogNewClient.class.getSimpleName();

    private TextView newClientError;
    private EditText newClientName, newClientLeftAmount;
    private Button newClientButtonOK, newClientButtonCancel;
    private ToggleButton newClientToggle;

    private Context context;
    private DatabaseClients dbClients;

    private boolean type;

    private CallbackAddInDialog callbackAddInDialog = null;

    public DialogNewClient(Context context, CallbackAddInDialog callback){
        super(context);
        this.context = context;
        dbClients = new DatabaseClients(context);
        callbackAddInDialog = (CallbackAddInDialog) callback;

    }


    public DialogNewClient(Context context, boolean type, CallbackAddInDialog callback) {
        super(context);
        this.context = context;
        this.dbClients = new DatabaseClients(context);
        this.type = type;
        this.callbackAddInDialog =  callback;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_new_client);
        
        newClientName = (EditText) findViewById(R.id.dialog_newclient_name);

        newClientName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(""))
                    if(dbClients.isClientWithNameAlreadyExist(s.toString()))
                        newClientError.setText("User already exist");
                    else
                        newClientError.setText("Name free");
                else
                    newClientError.setText("User name must be at least one sign");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newClientLeftAmount = (EditText) findViewById(R.id.dialog_newclient_leftamount);

        newClientToggle = (ToggleButton) findViewById(R.id.dialog_newclient_type);

        if(type)
            newClientToggle.setChecked(true);
        else
            newClientToggle.setChecked(false);

        newClientError = (TextView) findViewById(R.id.dialog_newclient_error);
        newClientError.setText("");

        newClientButtonOK = (Button) findViewById(R.id.dialog_newclient_button_ok);
        newClientButtonCancel = (Button) findViewById(R.id.dialog_newclient_button_cancel);

        newClientButtonOK.setOnClickListener(this);
        newClientButtonCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == newClientButtonOK.getId()) {
            Log.i(TAG, "onClick: OK button clicked");

            if(!newClientName.getText().toString().equals("")) {
                String clientName = newClientName.getText().toString();
                int clientLeftAmount;

                Log.e(TAG, "onClick: auuu: " + newClientLeftAmount.getText().toString());
                if(!dbClients.isClientWithNameAlreadyExist(newClientName.getText().toString())) {
                    if (newClientLeftAmount.getText().toString().equals("")) {

                        clientLeftAmount = 0;

                        Client client = new Client(clientName, clientLeftAmount);
                        dbClients.createClient(client);

                        callbackAddInDialog.reloadRecycler();

                        dismiss();
                    } else {

                        if (newClientToggle.isChecked())
                            clientLeftAmount = Integer.parseInt(newClientLeftAmount.getText().toString());
                        else
                            clientLeftAmount = Integer.parseInt(newClientLeftAmount.getText().toString()) * (-1);

                        Client client = new Client(clientName, clientLeftAmount);
                        dbClients.createClient(client);

                        callbackAddInDialog.reloadRecycler();

                        dismiss();
                    }
                } else
                    newClientError.setText("User name is already exist");
            } else {
                newClientError.setText("Name of client must have at least one sign");
                Log.e(TAG, "onClick: trzeba wpisac imie usera");
            }
        }
        else if(v.getId() == newClientButtonCancel.getId()) {
            Log.i(TAG, "onClick: CANCEL button clicked");
            dismiss();
        }
        else
            Log.i(TAG, "onClick: ERROR");
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
