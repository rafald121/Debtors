package com.example.android.debtors.Logic;

import android.content.Context;
import android.util.Log;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseOwner;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.Model.Transaction;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.Utils.Utils;

/**
 * Created by Rafaello on 2017-02-11.
 */

public class RealizeTransactionHelper {

    private static final String TAG = RealizeTransactionHelper.class.getSimpleName();


    Transaction transaction;
    DatabaseTransactions dbTransaction;
    DatabaseClients dbClients;
    DatabaseOwner dbOwners;
    Context mainContext;

    public RealizeTransactionHelper() {
    }

    public void realizeTransaction(Context applicationContext, TransactionForClient transaction){
        this.mainContext = applicationContext;
        dbTransaction = new DatabaseTransactions(mainContext);
        dbClients = new DatabaseClients(mainContext);
        dbOwners = new DatabaseOwner(mainContext);

        if(transaction!=null){

            int ownerId = transaction.getTransactionOwnerID();
            int clientId = transaction.getTransactionClientID();
            int amount = transaction.getTransactionQuantity();
            int value = transaction.getTransactionProductValue();
            int totalValue = amount * value;
            boolean gotOrGive = transaction.isTransactionBuyOrSell();

            if (transaction.getTransactionEntryPayment() > 0) {

                Payment payment = new Payment(Utils.getDateTime(), ownerId, clientId, transaction.getTransactionEntryPayment(), transaction.getTransactionDetails(),
                        gotOrGive);

                RealizePaymentHelper realizePaymentHelper = new RealizePaymentHelper();
                realizePaymentHelper.realizePayment(applicationContext, payment);

                changeOwnerValue(ownerId, totalValue, gotOrGive);

                changeClientValue(clientId, totalValue, gotOrGive); //jesli ostatnie true = Client wisi

                dbTransaction.createTransaction(transaction);

            } else {

                changeOwnerValue(ownerId, totalValue, gotOrGive);

                changeClientValue(clientId, totalValue, gotOrGive); //jesli ostatnie true = Client wisi

                dbTransaction.createTransaction(transaction);

            }

        } else {
            Log.e(TAG, "realizeTransaction: TRANSACTION IS NULL");
        }

    }

    private void changeOwnerValue(int ownerID, int totalValue, boolean gotOrGive){

        Owner owner = dbOwners.getOwner(ownerID);

        if (owner!=null)
            Log.i(TAG, "changeOwnerValue: owner is not null");
        else
            Log.i(TAG, "changeOwnerValue: owner is null");

        if (gotOrGive){
            owner.changeOwnerAmountWhenTransaction(totalValue);
        } else {
            owner.changeOwnerAmountWhenTransaction(totalValue*(-1));
        }

        dbOwners.updateOwner(owner);

    }

    private void changeClientValue(int clientId, int totalValue, boolean gotOrGive){

        Client client = dbClients.getClientByID(clientId);
        
        if(client!=null)
            Log.i(TAG, "changeClientValue: owner is not null");
        else
            Log.i(TAG, "changeClientValue: owner is null");
        
        if(gotOrGive)
            client.changeClientLeftAmount(totalValue);
        else
            client.changeClientLeftAmount(totalValue*(-1));

        dbClients.updateClient(client);

    }

}
