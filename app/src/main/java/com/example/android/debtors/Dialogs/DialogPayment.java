package com.example.android.debtors.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.load.data.StreamAssetPathFetcher;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by admin on 03.03.2017.
 */

public class DialogPayment extends Dialog implements View.OnClickListener{
    private static final String TAG = DialogPayment.class.getSimpleName();

    private Spinner newPaymentSpinner;
    private EditText newPaymentAmount;
    private TextView newPaymentError;
    private ToggleButton newPaymentToggle;

    private Button newPaymentOk, newPaymentCancel;

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

        newPaymentAmount = (EditText) findViewById(R.id.dialog_payment_amount);

        newPaymentError = (TextView) findViewById(R.id.dialog_payment_error);
        newPaymentError.setText("");

        newPaymentSpinner = (Spinner) findViewById(R.id.dialog_payment_spinner);

        newPaymentToggle = (ToggleButton) findViewById(R.id.dialog_payment_toggle);

        newPaymentOk = (Button) findViewById(R.id.dialog_payment_ok);
        newPaymentCancel = (Button) findViewById(R.id.dialog_payment_cancel);

        List<String> clientsNames= getListOfClientsNames();

        ArrayAdapter<String> adapterSpiner = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, clientsNames);
        newPaymentSpinner.setAdapter(adapterSpiner);

        newPaymentOk.setOnClickListener(this);
        newPaymentCancel.setOnClickListener(this);

/*        newPaymentSpinner.setOnClickListener(this);
        newPaymentToggle.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == newPaymentOk.getId()){
            Log.i(TAG, "onClick: ok");
        } else if(v.getId() == newPaymentCancel.getId()) {
            Log.i(TAG, "onClick: cancel");
        } else if(v.getId() == newPaymentSpinner.getId()) {
            Log.i(TAG, "onClick: spinner");
        }else if(v.getId() == newPaymentToggle.getId()) {
            Log.i(TAG, "onClick: toggle");
        }
    }

    private List<String> getListOfClientsNames(){
        DatabaseClients dbClients = new DatabaseClients(context);

        List<String> list = dbClients.getListOfClientsNames();

        return list;
    }
}
