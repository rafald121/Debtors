package com.example.android.debtors.Utils;

import android.util.Log;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseOwner;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.Model.TransactionForClient;

import java.util.List;

import static android.content.ContentValues.TAG;
import static android.util.Log.i;

/**
 * Created by Rafaello on 2017-02-23.
 */

public class UtilsDatabaseMethods {

    DatabaseClients dbClient;
    DatabasePayments dbPayment;
    DatabaseTransactions dbTransaction;
    DatabaseOwner dbOwner;

    private void createClients(String[] names){
        for(int i = 0 ; i< names.length -1 ; i++){
            Client client = new Client(names[i], 50*i*(-10));
            dbClient.createClient(client);
        }
    }

    private void getListOfOwnerTransactions(Owner owner){
        i(TAG, "getListOfOwnerTransactions: lista tranzakcji " + owner.getListOfTransaction());
    }

    private void getListOfClientTransactions(Client client){
        i(TAG, "getListOfClientTransactions: lista tranzakcji " + client.getListOfTransaction());
    }

    private void getListOfOwnerPayments(Owner owner){
        i(TAG, "getListOfOwnerPayments: lista zapłat: " + owner.getListOfPayments());
    }
    private void getListOfClientPayments(Client client){
        i(TAG, "getListOfClientPayments: lista zapłat: " + client.getListOfPayments());
    }
    private void getInfoAboutTransaction(TransactionForClient transaction){
        i(TAG, "getInfoAboutTransaction: " + transaction.toString());
    }
    private void getInfoAboutPayments(Payment payment){
        i(TAG, "getInfoAboutPayments: " + payment.toString());
    }
    private void getInfoAboutOwner(Owner owner){
        i(TAG, "getInfoAboutOwner: " + owner.toString());
    }
    private void getInfoAboutClient(Client client){
        i(TAG, "getInfoAboutClient: " + client.toString());
    }
    private void printList(List<Client> list){
        if(list.size() == 0 )
            i(TAG, "printList: LISTA PUSTA");
        i(TAG, "printList: hao");
        for(Client c : list)
            i(TAG, "printList: drukuje klienta: " + c.toString());
    }

    private List<Owner> getOwner(){
        i(TAG, "getOwner:  OWNER");

        List<Owner> listOfOwners = dbOwner.getAllOwners();
        Log.i(TAG, "getOwner: size of list of owners: " + listOfOwners.size());
        for(Owner o : listOfOwners)
            i(TAG, "getAllOwners: owner: " + o.toString());

        return  listOfOwners;
    }

    private List<Payment> getPayments(){
        List<Payment> listOfPayments = dbPayment.getAllPayments();

        for(Payment p : listOfPayments)
            Log.i(TAG, "getPayments: " + p.toString(true));

        return listOfPayments;
    }
    private List<TransactionForClient> getTransaction(){
        Log.i(TAG, "getTransaction: ");
        List<TransactionForClient> listOfTransaction = dbTransaction.getListOfTransaction();

        if (listOfTransaction.size()==0)
            Log.i(TAG, "getTransaction: LIST OF TRANSACTION = 0");

        for(TransactionForClient t: listOfTransaction)
            Log.i(TAG, "getTransaction: transaction: " + t.toString());

        return listOfTransaction;
    }
    private List<TransactionForClient> getTransactionByClientId(long clientID) {
        List<TransactionForClient> transactionByClientID = dbTransaction.getTransactionFromClient(clientID);
        Log.w(TAG, "getTransactionByClientId: " );

        if (transactionByClientID.size() == 0 )
            Log.e(TAG, "getTransactionByClientId: LIST OF TRANSACTION BY CLIENT ID IS EMPTY");

        for(TransactionForClient t : transactionByClientID){
            Log.i(TAG, "getTransactionByClientId: transaction by client id: " + t.toString());
        }

        return transactionByClientID;
    }

    private List<TransactionForClient> getTransactionByOwnerId(long ownerID) {
        List<TransactionForClient> transactionByOwnerID = dbTransaction.getTransactionFromOwned(ownerID);
        Log.w(TAG, "getTransactionByOwnerId: " );

        if (transactionByOwnerID.size() == 0 )
            Log.e(TAG, "getTransactionByOwnerId: LIST OF TRANSACTION BY OWNER ID IS EMPTY");

        for(TransactionForClient t : transactionByOwnerID){
            Log.i(TAG, "getTransactionByOwnerId: transaction by owner id: " + t.toString());
        }

        return transactionByOwnerID;
    }

}
