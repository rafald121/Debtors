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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Interfaces.CallbackAddInDialog;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.R;

/**
 * Created by admin on 25.03.2017.
 */

public class DialogNewClientTMP extends DialogFragment implements View.OnClickListener {


    private static final String TAG = DialogNewClient.class.getSimpleName();

    private View view = null;
    private TextView newClientError;
    private EditText newClientName, newClientLeftAmount;
    private Button newClientButtonOK, newClientButtonCancel;
    private ToggleButton newClientToggle;

    private Context context;
    private DatabaseClients dbClients;

    private boolean type;

    private CallbackAddInDialog callbackAddInDialog = null;

    private FragmentActivity fragmentActivity = null;


    public static DialogNewClientTMP newInstance() {
        DialogNewClientTMP fragment = new DialogNewClientTMP();
        return fragment;
    }

    public DialogNewClientTMP() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackAddInDialog = (CallbackAddInDialog) getParentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_newclient, container, false);

        newClientName = (EditText) view.findViewById(R.id.dialog_newclient_name);

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

        newClientLeftAmount = (EditText) view.findViewById(R.id.dialog_newclient_leftamount);

        newClientToggle = (ToggleButton) view.findViewById(R.id.dialog_newclient_type);

        if(type)
            newClientToggle.setChecked(true);
        else
            newClientToggle.setChecked(false);

        newClientError = (TextView) view.findViewById(R.id.dialog_newclient_error);
        newClientError.setText("");

        newClientButtonOK = (Button) view.findViewById(R.id.dialog_newclient_button_ok);
        newClientButtonCancel = (Button) view.findViewById(R.id.dialog_newclient_button_cancel);

        newClientButtonOK.setOnClickListener(this);
        newClientButtonCancel.setOnClickListener(this);

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
            Log.i(TAG, "onClick: wtf?");
    }
}
