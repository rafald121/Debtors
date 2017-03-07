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
//ZMIENIC NA PRZYPISYWANIE ID
//            Owner owner = transaction.getTransactionOwner();
//            Client client = transaction.getTransactionClient();
            int ownerId = transaction.getTransactionOwnerID();
            int clientId = transaction.getTransactionClientID();
            int amount = transaction.getTransactionQuantity();
            int value = transaction.getTransactionProductValue();
            int totalValue = amount * value;
            boolean gotOrGive = transaction.isTransactionBuyOrSell();

//            Owner owner = dbOwners.getOwner(ownerId);
//            Client client = dbClients.getClientByID(clientId);

//            if (owner != null && client != null) {

            if (transaction.getTransactionEntryPayment() > 0) {
                Log.i(TAG, "realizeTransaction: PODANO ENTRY PAYMENT: " + transaction
                        .getTransactionEntryPayment());

                Payment payment = new Payment(Utils.getDateTime(), ownerId, clientId, transaction.getTransactionEntryPayment(), transaction.getTransactionDetails(),
                        gotOrGive);

                RealizePaymentHelper realizePaymentHelper = new RealizePaymentHelper();
                realizePaymentHelper.realizePayment(applicationContext, payment);

                changeOwnerValue(ownerId, totalValue, gotOrGive);
//                    addTransactionToOwnerList(owner, transaction);

                changeClientValue(clientId, totalValue, gotOrGive); //jesli ostatnie true = Client wisi
                // więcej
//                    addTransactionToClientList(client, transaction);

                dbTransaction.createTransaction(transaction);

            } else {

                Log.i(TAG, "realizeTransaction: NIE PODANO ENTRY PAYMENT: " + transaction.getTransactionEntryPayment());
//                    TYLKO ZMIANA ZA TRANZAKCJE
                changeOwnerValue(ownerId, totalValue, gotOrGive);
//                    addTransactionToOwnerList(owner, transaction);

                changeClientValue(clientId, totalValue, gotOrGive); //jesli ostatnie true = Client wisi
                // więcej
//                    addTransactionToClientList(client, transaction);

                dbTransaction.createTransaction(transaction);

            }

//            } else {
//                Log.e(TAG, "realizeTransaction: CLIENT OR OWNER IS NULL");
//            }
        } else {
            Log.e(TAG, "realizeTransaction: TRANSACTION IS NULL");
        }

    }

    private void changeOwnerValue(int ownerID, int totalValue, boolean gotOrGive){

        Log.i(TAG, "changeOwnerValue: ownerID: " + ownerID);

        Owner owner = dbOwners.getOwner(ownerID);

        if (owner!=null)
            Log.i(TAG, "changeOwnerValue: git");
        else
            Log.i(TAG, "changeOwnerValue: slabo");

        if (gotOrGive){
            owner.changeOwnerAmountWhenTransaction(totalValue);
        } else {
            owner.changeOwnerAmountWhenTransaction(totalValue*(-1));
        }

        dbOwners.updateOwner(owner);

    }

    private void changeClientValue(int clientId, int totalValue, boolean gotOrGive){

        Log.i(TAG, "changeClientValue: clientID: " + clientId);
        
        Client client = dbClients.getClientByID(clientId);
        
        if(client!=null)
            Log.i(TAG, "changeClientValue: git");
        else
            Log.i(TAG, "changeClientValue: slabo");
        
        if(gotOrGive)
            client.changeClientLeftAmount(totalValue);
        else
            client.changeClientLeftAmount(totalValue*(-1));

        dbClients.updateClient(client);

    }
//    private void changeOwnerValue(Owner owner, int totalValue, boolean gotOrGive) {
//
//        Log.i(TAG, "changeOwnerValue: OWNER BEFORE FROM DATABASE: " + dbOwners.getOwner(owner.getOwnerID()));
//
//        if(gotOrGive) // jesli true = owner sprzedaje wiec ma dostać hajs
//            owner.changeOwnerAmountWhenTransaction(totalValue);
//        else
//            owner.changeOwnerAmountWhenTransaction(totalValue*(-1));
//
//        dbOwners.updateOwner(owner);
//
//        Log.i(TAG, "changeOwnerValue: OWNER AFTER FROM DATABASE: " + dbOwners.getOwner(owner.getOwnerID()));
//    }

//    private void addTransactionToOwnerList(Owner owner, TransactionForClient transaction) {
////        Log.w(TAG, "addTransactionToOwnerList: dodaje tranzakcje u ownera: " + transaction.toString
////                () );
//        owner.addTransactionToList(transaction);
//    }

//    private void changeClientValue(Client client, int totalValue, boolean gotOrGive) {
//
//        Log.i(TAG, "changeClientValue: CLIENT BEFORE FROM DATABASE: " + dbClients.getClientByID
//                (client.getClientId()));
//
//        if(gotOrGive)// jesli true = owner sprzedaje wiec buyer wisi więcej
//            client.changeClientLeftAmount(totalValue);
//        else // jesli false - owner kupuje więc płaci hajs clientowi więc clientowi zmniejsza sie
//            // zaległa kwota
//            client.changeClientLeftAmount(totalValue*(-1));
//
//        dbClients.updateClient(client);
//
//        Log.i(TAG, "changeClientValue: CLIENT AFTER FROM DATABASE: " + dbClients.getClientByID
//                (client.getClientId()));
//    }

//    private void addTransactionToClientList(Client client, TransactionForClient transaction) {
//        TransactionForClient editedTransaction = new TransactionForClient(Utils.getDateTime(),
//                transaction.getTransactionOwner(),transaction.getTransactionClient(), transaction
//                .getTransactionQuantity(),transaction.getTransactionProductValue(),transaction
//                .getTransactionEntryPayment(),!transaction.isTransactionBuyOrSell());
////        Log.w(TAG, "addTransactionToClientList: dodaje tranzakcje  u klienta " + editedTransaction
////                .toString() );
//
//        //edycja, aby u klienta wyswietlalo sie ze kupił, a nie sprzedal
//        client.addTransactionToListOfTransaction(editedTransaction);
//    }
}
