package com.example.android.debtors.Logic;

import android.util.Log;

import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.Transaction;
import com.example.android.debtors.Model.TransactionForClient;

/**
 * Created by Rafaello on 2017-02-11.
 */

public class RealizeTransactionHelper {

    private static final String TAG = RealizeTransactionHelper.class.getSimpleName();

    Transaction transaction;

    public RealizeTransactionHelper() {
    }

    public void realizeTransaction(TransactionForClient transaction){
        if(transaction==null)
            Log.e(TAG, "realizeTransaction: TRANSACTION IS NULL");
        else {

            if(transaction.getTransactionEntryPayment()>0){
                Log.i(TAG, "realizeTransaction: PODANO ENTRY PAYMENT" + transaction.getTransactionEntryPayment());
            } else
                Log.i(TAG, "realizeTransaction: NIE PODANO ENTRY PAYMENT" + transaction.getTransactionEntryPayment());

            int amount = transaction.getTransactionQuantity();
            int value = transaction.getProductValue();
            int totalValue = amount * value;

            boolean gotOrGive = transaction.isTransactionSeller();

            Owner owner = transaction.getTransactionOwner();
            Client client = transaction.getTransactionClient();
            if(owner != null && client != null) {
                changeOwnerValue(owner, totalValue, gotOrGive);
                addTransactionToOwnerList(owner, transaction);

                changeClientValue(client, totalValue, gotOrGive); //jesli ostatnie true = Client wisi
                // więcej
                addTransactionToClientList(client, transaction);
            } else{
                Log.e(TAG, "realizeTransaction: CLIENT OR OWNER IS NULL");
            }
        }
    }


    private void changeOwnerValue(Owner owner, int totalValue, boolean gotOrGive) {
        if(gotOrGive) // jesli true = owner sprzedaje wiec ma dostać hajs
            owner.changeOwnerAmountWhenTransaction(totalValue);
        else
            owner.changeOwnerAmountWhenTransaction(totalValue*(-1));
    }

    private void addTransactionToOwnerList(Owner owner, TransactionForClient transaction) {
        owner.addTransactionToList(transaction);
    }

    private void changeClientValue(Client client, int totalValue, boolean gotOrGive) {
        if(gotOrGive)// jesli true = owner sprzedaje wiec buyer wisi więcej
            client.changeClientLeftAmount(totalValue);
        else // jesli false - owner kupuje więc płaci hajs clientowi więc clientowi zmniejsza sie
            // zaległa kwota
            client.changeClientLeftAmount(totalValue*(-1));
    }

    private void addTransactionToClientList(Client client, TransactionForClient transaction) {
        client.addTransactionToListOfTransaction(transaction);
    }
}
