package com.example.android.debtors.Logic;

import android.content.Context;
import android.util.Log;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseOwner;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.Utils.Utils;

/**
 * Created by Rafaello on 2017-02-11.
 */

public class RealizePaymentHelper {

    private static final String TAG = RealizePaymentHelper.class.getSimpleName();

    Context mainContext;
    DatabasePayments dbPayment;
    DatabaseOwner dbOwner;
    DatabaseClients dbClient;
    public RealizePaymentHelper() {
    }

    public void realizePayment(Context context, Payment payment){
        mainContext=context;
        dbPayment = new DatabasePayments(mainContext);
        dbClient = new DatabaseClients(mainContext);
        dbOwner = new DatabaseOwner(mainContext);

        if(payment==null){
            Log.e(TAG, "realizePayment: PAYMENT IS NULL");
        } else {

            int ownerID = payment.getPaymentOwnerID();
            int clientID = payment.getPaymentClientID();

            Owner owner = dbOwner.getOwner(ownerID);
            Client client = dbClient.getClientByID(clientID);

            int paidAmount = payment.getPaymentAmount();

            boolean revenueOrExpense = payment.isPaymentGotOrGiven();

            changeOwnerAmount(owner, paidAmount, revenueOrExpense);

            changeClientAmount(client, paidAmount, revenueOrExpense);

            dbPayment.createPayment(payment);

        }
    }


    private void changeOwnerAmount(Owner owner, int paidAmount, boolean revenueOrExpense) {

        if(revenueOrExpense)//jesli true = revenue
            owner.changeOwnerAmountWhenPayments(paidAmount);
        else
            owner.changeOwnerAmountWhenPayments(paidAmount*(-1));

        dbOwner.updateOwner(owner);

    }

    private void changeClientAmount(Client client, int paidAmount, boolean revenueOrExpense) {

        if(revenueOrExpense) // owner dostaje od clienta, wiec client ma mniejszy dlug
            client.changeClientLeftAmount(paidAmount*(-1));
        else
            client.changeClientLeftAmount(paidAmount);

        dbClient.updateClient(client);

    }

}
