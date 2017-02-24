package com.example.android.debtors.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bumptech.glide.load.data.StreamAssetPathFetcher;
import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseOwner;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.R;

import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.util.Log.i;

/**
 * Created by Rafaello on 2017-02-23.
 */

public class UtilsDatabaseMethods {

    static DatabaseClients dbClient;
    static DatabasePayments dbPayment;
    static DatabaseTransactions dbTransaction;
    static DatabaseOwner dbOwner;
    static Context context;

    public UtilsDatabaseMethods(Context applicationContext) {
        this.context = applicationContext;
        dbClient = new DatabaseClients(applicationContext);
        dbPayment = new DatabasePayments(applicationContext);
        dbTransaction = new DatabaseTransactions(applicationContext);

    }

    public static void createClients(String[] names){
        for(int i = 0 ; i< names.length -1 ; i++){
            Client client = new Client(names[i], 50*i*(-10));
            dbClient.createClient(client);
        }
    }

    public static void getListOfOwnerTransactions(Owner owner){
        i(TAG, "getListOfOwnerTransactions: lista tranzakcji " + owner.getListOfTransaction());
    }

    public static void getListOfClientTransactions(Client client){
        i(TAG, "getListOfClientTransactions: lista tranzakcji " + client.getListOfTransaction());
    }

    public static void getListOfOwnerPayments(Owner owner){
        i(TAG, "getListOfOwnerPayments: lista zapłat: " + owner.getListOfPayments());
    }
    public static void getListOfClientPayments(Client client){
        i(TAG, "getListOfClientPayments: lista zapłat: " + client.getListOfPayments());
    }
    public static void getInfoAboutTransaction(TransactionForClient transaction){
        i(TAG, "getInfoAboutTransaction: " + transaction.toString());
    }
    public static void getInfoAboutPayments(Payment payment){
        i(TAG, "getInfoAboutPayments: " + payment.toString());
    }
    public static void getInfoAboutOwner(Owner owner){
        i(TAG, "getInfoAboutOwner: " + owner.toString());
    }
    public static void getInfoAboutClient(Client client){
        i(TAG, "getInfoAboutClient: " + client.toString());
    }
    public static void printList(List<Client> list){
        if(list.size() == 0 )
            i(TAG, "printList: LISTA PUSTA");
        i(TAG, "printList: hao");
        for(Client c : list)
            i(TAG, "printList: drukuje klienta: " + c.toString());
    }

    public static List<Owner> getOwner(){
        i(TAG, "getOwner:  OWNER");

        List<Owner> listOfOwners = dbOwner.getAllOwners();
        Log.i(TAG, "getOwner: size of list of owners: " + listOfOwners.size());
        for(Owner o : listOfOwners)
            i(TAG, "getAllOwners: owner: " + o.toString());

        return  listOfOwners;
    }

    public static List<Payment> getPayments(){
        List<Payment> listOfPayments = dbPayment.getAllPayments();

        for(Payment p : listOfPayments)
            Log.i(TAG, "getPayments: " + p.toString(true));

        return listOfPayments;
    }
    public static List<TransactionForClient> getTransaction(){
        Log.i(TAG, "getTransaction: ");
        List<TransactionForClient> listOfTransaction = dbTransaction.getListOfTransaction();

        if (listOfTransaction.size()==0)
            Log.i(TAG, "getTransaction: LIST OF TRANSACTION = 0");

        for(TransactionForClient t: listOfTransaction)
            Log.i(TAG, "getTransaction: transaction: " + t.toString());

        return listOfTransaction;
    }
    public static List<TransactionForClient> getTransactionByClientId(long clientID) {
        List<TransactionForClient> transactionByClientID = dbTransaction.getTransactionFromClient(clientID);
        Log.w(TAG, "getTransactionByClientId: " );

        if (transactionByClientID.size() == 0 )
            Log.e(TAG, "getTransactionByClientId: LIST OF TRANSACTION BY CLIENT ID IS EMPTY");

        for(TransactionForClient t : transactionByClientID){
            Log.i(TAG, "getTransactionByClientId: transaction by client id: " + t.toString());
        }

        return transactionByClientID;
    }

    public static List<TransactionForClient> getTransactionByOwnerId(long ownerID) {
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
