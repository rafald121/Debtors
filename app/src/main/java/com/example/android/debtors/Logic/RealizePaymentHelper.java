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


            Owner owner = payment.getPaymentOwner();
            Client client = payment.getPaymentClient();

            int paidAmount = payment.getPaymentAmount();

            boolean revenueOrExpense = payment.isPaymentGotOrGiven();

            changeOwnerAmount(owner, paidAmount, revenueOrExpense);
            addPaymentToOwnerList(owner, payment);


            changeClientAmount(client, paidAmount, revenueOrExpense);
            addPaymentToClientList(client, payment);

            dbPayment.createPayment(payment);

        }
    }




    private void changeOwnerAmount(Owner owner, int paidAmount, boolean revenueOrExpense) {

        Log.i(TAG, "changeOwnerAmount: OWNER BEFORE FROM ARGUMENT: " + owner.toString());
        Log.i(TAG, "changeOwnerAmount: OWNER BEFORE FROM DATABASE: " + dbOwner.getOwner(owner.getOwnerID()));

        if(revenueOrExpense)//jesli true = revenue
            owner.changeOwnerAmountWhenPayments(paidAmount);
        else
            owner.changeOwnerAmountWhenPayments(paidAmount*(-1));

        dbOwner.updateOwner(owner);
        Log.i(TAG, "changeOwnerAmount: OWNER AFTER FROM DATABASE: " + dbOwner.getOwner(owner
                .getOwnerID()));


    }

    private void addPaymentToOwnerList(Owner owner, Payment payment) {
        owner.addPaymentToList(payment);
    }

    private void changeClientAmount(Client client, int paidAmount, boolean revenueOrExpense) {

        Log.i(TAG, "changeClientAmount: CLIENT BEFORE FROM ARGUMENT: " + client.toString());
        Log.i(TAG, "changeClientAmount: CLIENT BEFORE FROM DATABASE: " + dbClient.getClientByID
                (client.getClientId()));

        if(revenueOrExpense) // owner dostaje od clienta, wiec client ma mniejszy dlug
            client.changeClientLeftAmount(paidAmount*(-1));
        else
            client.changeClientLeftAmount(paidAmount);

        dbClient.updateClient(client);
        Log.i(TAG, "changeClientAmount: CLIENT AFTER FROM DATABASE: " + dbClient.getClientByID(client.getClientId
                ()));

    }

    private void addPaymentToClientList(Client client, Payment payment) {
        Payment payment2 = new Payment(Utils.getDateTime(), payment.getPaymentOwner(), payment.getPaymentClient(), payment.getPaymentAmount(), !payment.isPaymentGotOrGiven());

        client.addPaymentToListOfPayments(payment2);
    }
}
