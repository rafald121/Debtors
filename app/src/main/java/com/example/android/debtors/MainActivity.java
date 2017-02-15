package com.example.android.debtors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.debtors.Databases.DatabaseClients;
import com.example.android.debtors.Databases.DatabaseOwner;
import com.example.android.debtors.Databases.DatabasePayments;
import com.example.android.debtors.Databases.DatabaseTransactions;
import com.example.android.debtors.Logic.RealizePaymentHelper;
import com.example.android.debtors.Logic.RealizeTransactionHelper;
import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.Payment;
import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.util.Log.i;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    DatabaseClients dbClient;
    DatabaseOwner dbOwner;
    DatabasePayments dbPayment;
    DatabaseTransactions dbTransaction;

    String[] names = {"rafal", "marek", "karol", "adrian" , "tomek" , "jan", "andrzejek",
            "maniek", "maniok", "chamiok"};
    HashMap<Long,Client> clientsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbClient = new DatabaseClients(getApplicationContext());
        dbOwner = new DatabaseOwner(getApplicationContext());
        dbPayment = new DatabasePayments(getApplicationContext());
        dbTransaction = new DatabaseTransactions(getApplicationContext());

       
//        dbPayment.deletePaymentInRange(15,20);
//        simulatePayments();

//        List<Client> listOfClient = new ArrayList<>();
//        List<Client> listOfAllClientFromDatabase = new ArrayList<>();
//        List<Client> listOfClientWithLeftAmountFromTo = new ArrayList<>();
//        List<Client> listOfUserByName = new ArrayList<>();
//        listOfAllClientFromDatabase = getAllClients();
//        listOfClientWithLeftAmountFromTo = getClientInLeftAmountRange();
//        List<Payment> listOfAllPayments = getPayments();
//        List<Payment> listOfPaymentsByClientId = getPaymentByClientId(2);
//        List<Payment>
//        List<TransactionForClient> listOfTransactionByClientID = getTransactionByClientId(7);
//        List<TransactionForClient> listOfTransactionByOwnerID = getTransactionByOwnerId(2);
//        List<Owner> listOfAllOwners = getOwner();


//        simulateTransaction();
//        simulateTransactionWithPayment();


//        List<Payment> listOfAllPayments = getPayments();
//        List<Payment> listOfPaymentsByClientId = getPaymentByClientId(2);
//        List<Payment> listOfPaymentsByOwnerId = getPaymentByOwnerId(1);

//        List<TransactionForClient> listOfAllTransaction = getTransaction();

//        Log.i(TAG, "onCreate: listOfPaymentsByClientId" + listOfPaymentsByClientId.toString();
//        simulatePayments(owner,clientJurand);
//        simulateTransaction();
//        simulateTransactionWithPayment();

    }




    private void simulateTransactionWithPayment(){
        Log.w(TAG, "simulateTransactionWithPayment: ");
        //        WLASCICIEL
        Owner owner = dbOwner.getOwner(1);

//        KLIECI
        Client clientJurand = dbClient.getClientByID(4); //kupujacy 2

        TransactionForClient transactionWithPayment = new TransactionForClient(Utils.getDateTime(), owner, clientJurand, 6, 30, 100, true);

        getInfoAboutTransaction(transactionWithPayment);

        Log.w(TAG, "onCreate: BEFORE TRANSACTION WITH PAYMENT" );
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerTransactions(owner);
//        getListOfClientTransactions(clientJurand);

        RealizeTransactionHelper realizeTransactionHelper = new RealizeTransactionHelper();
        realizeTransactionHelper.realizeTransaction(getApplicationContext(), transactionWithPayment);

        Log.w(TAG, "onCreate: AFTER TRANSACTION WITH PAYMENT ");

        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerTransactions(owner);
//        getListOfClientTransactions(clientJurand);

        dbClient.updateClient(clientJurand);

    }
    private void simulatePayments(){
        Log.w(TAG, "simulatePayments: " );

        Owner owner = dbOwner.getOwner(1);

        Client clientJurand = dbClient.getClientByID(8); //kupujacy 2

        Payment payment = new Payment(Utils.getDateTime(), owner, clientJurand, 20, true);

        Log.i(TAG, "simulatePayments: CREATING PAYMENT  " + payment.toString());
//        dbPayment.createPayment(payment);

        Log.w(TAG, "onCreate: BEFORE PAYMENT" );
        getInfoAboutPayments(payment);
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);

        RealizePaymentHelper realizePaymentHelper = new RealizePaymentHelper();
        realizePaymentHelper.realizePayment(getApplicationContext(), payment);

        Log.w(TAG, "onCreate: AFTER PAYMENT ");
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerPayments(owner);
//        getListOfClientPayments(clientJurand);

        dbClient.updateClient(clientJurand);


    }
    private void simulatePayments(Owner owner, Client clientJurand){
        Log.w(TAG, "simulatePayments: " );

//        PLATNOSC, clientJurand - klient, 50 - kwota, true - dostaję, false - płacę
        Payment payment = new Payment(Utils.getDateTime(), owner, clientJurand, 50, true);
        //tworzony obiekt
        Log.w(TAG, "onCreate: BEFORE PAYMENT" );
        getInfoAboutPayments(payment);
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);


        RealizePaymentHelper realizePaymentHelper = new RealizePaymentHelper();
        realizePaymentHelper.realizePayment(getApplicationContext(), payment);

        Log.w(TAG, "onCreate: AFTER PAYMENT ");
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerPayments(owner);
//        getListOfClientPayments(clientJurand);


        dbClient.updateClient(clientJurand);


    }
    private void simulateTransaction(){
        Log.w(TAG, "simulateTransaction: ");

//        WLASCICIEL
        Owner owner = dbOwner.getOwner(1);
//        KLIECI
        Client clientJurand = dbClient.getClientByID(4); //kupujacy 2

//        TRANZAKCJA
        //o godziinie X owner robi tranzakcje z jurandem za 5 po 10,
        // true - owner - sprzedający,
        // false - owner - kupujący
        TransactionForClient transactionForClient = new TransactionForClient(Utils.getDateTime(),
                owner, clientJurand, 2, 100, true);

//        dbTransaction.createTransaction(transactionForClient);

        getInfoAboutTransaction(transactionForClient);

        Log.w(TAG, "onCreate: BEFORE TRANSACTION" );
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerTransactions(owner);
//        getListOfClientTransactions(clientJurand);

        RealizeTransactionHelper realizeTransactionHelper = new RealizeTransactionHelper();
        realizeTransactionHelper.realizeTransaction(getApplicationContext(),transactionForClient);
        Log.w(TAG, "onCreate: AFTER TRANSACTION ");

        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
//        getListOfOwnerTransactions(owner);
//        getListOfClientTransactions(clientJurand);


        dbClient.updateClient(clientJurand);
    }

    private void createClients(String[] names){
        for(int i = 0 ; i< names.length -1 ; i++){
            Client client = new Client(names[i], 50*i);
            dbClient.createClient(client);
        }
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
    private void deleteAllPayments(){
        List<Payment> listOfPayments = dbPayment.getAllPayments();
        int pajmeny = listOfPayments.size();

        for(int i = 0 ; i<pajmeny;i++){
            dbPayment.deletePayment(listOfPayments.get(i).getPaymentID());
        }
    }

    private void deleteAllClients() {
        
        List<Client> listOfClients = dbClient.getAllClient();
        int liczbaklientuw = listOfClients.size();

        Log.i(TAG, "deleteAllClients: size of list of clients : " + liczbaklientuw);

//        while(liczbaklientuw>0){
//            Log.i(TAG, "deleteAllClients: deleting client id: " + liczbaklientuw);
//            db.deleteClient(liczbaklientuw);
//            liczbaklientuw--;
//        }

        for(int i = 0 ; i<liczbaklientuw;i++){
            Log.i(TAG, "deleteAllClients: deleted client: " + i);
            dbClient.deleteClient(listOfClients.get(i).getClientId());
        }

    }

    private List<Payment> getPaymentByClientId(long clientID){
        Log.i(TAG, "getPaymentByClientId: payment dla danego id clienta");
        List<Payment> listOfPayments = dbPayment.getPaymentsFromClient(clientID);

        if(listOfPayments.size()==0)
            Log.e(TAG, "getPaymentByClientId: THERE IS NOT ANY PAYMENTS FOR THAT CLIENT" );

        for(Payment p : listOfPayments){
            Log.i(TAG, "getPaymentByClientId: payment: " + p.toString(true));
        }

        return listOfPayments;
    }

    private List<Payment> getPaymentByOwnerId(long ownerID){
        Log.i(TAG, "getPaymentByOwnerId: payment dla danego id ownera");
        List<Payment> listOfPayments = dbPayment.getPaymentsFromOwner(ownerID);

        if(listOfPayments.size()==0)
            Log.e(TAG, "getPaymentByOwnerId: THERE IS NOT ANY PAYMENTS FOR THAT OWNER" );

        for(Payment p : listOfPayments){
            Log.i(TAG, "getPaymentByOwnerId: payment: " + p.toString(true));
        }

        return listOfPayments;
    }
    private List<Client> getAllClients() {
        i(TAG, "getAllClients: WSZYSCY KLIENCI");
        List<Client> listOfClients = dbClient.getAllClient();
        i(TAG, "getAllClients: liczba klientów: " + listOfClients.size());
        for(Client c : listOfClients)
            i(TAG, "getAllClients: client: " + c.toString());

        return  listOfClients;
    }
    
    private List<Client> getClientInLeftAmountRange(){
        i(TAG, "getClientInLeftAmountRange: Z ZAKRESU OD DO HAJSU");
        
        List<Client> listOfClientWithLeftAmountFromTo = new ArrayList<>();

        listOfClientWithLeftAmountFromTo = dbClient.getClientWithLeftAmountSorted(50,150);


        for(Client client : listOfClientWithLeftAmountFromTo){
            i(TAG, "onCreate: uzytkownik: " + client.toString());
        }
        
        return listOfClientWithLeftAmountFromTo;
    }

    private List<Client> getClientsByName(String name){

        List<Client> listOfUserByName = new ArrayList<>();

        listOfUserByName =  dbClient.getClientByName(name);
        return listOfUserByName;
    }



}

