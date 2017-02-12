package com.example.android.debtors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.debtors.Databases.DatabaseClients;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    DatabaseClients db;
    String[] names = {"rafal", "marek", "karol", "adrian" , "tomek" , "jan", "andrzejek",
            "maniek", "maniok", "chamiok"};
    HashMap<Long,Client> clientsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseClients(getApplicationContext());

        List<Client> listOfClient = new ArrayList<>();
        List<Client> listOfAllClientFromDatabase = new ArrayList<>();
        List<Client> listOfClientWithLeftAmountFromTo = new ArrayList<>();
        List<Client> listOfUserByName = new ArrayList<>();
        listOfAllClientFromDatabase = getAllClients();
        listOfClientWithLeftAmountFromTo = getClientInLeftAmountRange();

        simulatePayments();
        simulateTransaction();
        simulateTransactionWithPayment();
//TODO ZMIENIC ABY DLA KLIENTA JESLI PLACI HAJS NIE BYL REVENUE TYLKO EXPENSE ALBO STRATA,
// ODJECIE, itd.


    }


    private void simulateTransactionWithPayment(){
        Log.w(TAG, "simulateTransactionWithPayment: ");
        //        WLASCICIEL
        Owner owner = new Owner("rafal","dolega", 5000);
//        KLIECI
        Client clientManiek = db.getClientByID(1); //kupujacy 1
        Client clientJurand = db.getClientByID(14); //kupujacy 2

        TransactionForClient transactionWithPayment = new TransactionForClient(Utils.getDateTime
                (), owner, clientJurand, 6, 30, 100, true);

        getInfoAboutTransaction(transactionWithPayment);

        Log.w(TAG, "onCreate: BEFORE TRANSACTION WITH PAYMENT" );
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
        getListOfOwnerTransactions(owner);
        getListOfClientTransactions(clientJurand);

        RealizeTransactionHelper realizeTransactionHelper = new RealizeTransactionHelper();
        realizeTransactionHelper.realizeTransaction(transactionWithPayment);

        Log.w(TAG, "onCreate: AFTER TRANSACTION WITH PAYMENT ");

        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
        getListOfOwnerTransactions(owner);
        getListOfClientTransactions(clientJurand);

    }

    private void simulatePayments(){
        Log.w(TAG, "simulatePayments: " );
//        WLASCICIEL
        Owner owner = new Owner("rafal","dolega", 5000);
//        KLIECI
        Client clientManiek = db.getClientByID(1); //kupujacy 1
        Client clientJurand = db.getClientByID(14); //kupujacy 2

//        PLATNOSC, clientJurand - klient, 50 - kwota, true - dostaję, false - płacę
        Payment payment = new Payment(Utils.getDateTime(), owner, clientJurand, 50, true);
        //tworzony obiekt
        Log.w(TAG, "onCreate: BEFORE PAYMENT" );
        getInfoAboutPayments(payment);
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);


        RealizePaymentHelper realizePaymentHelper = new RealizePaymentHelper();
        realizePaymentHelper.realizePayment(payment);

        Log.w(TAG, "onCreate: AFTER PAYMENT ");
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
        getListOfOwnerPayments(owner);
        getListOfClientPayments(clientJurand);



    }

    private void simulateTransaction(){
        Log.w(TAG, "simulateTransaction: ");

//        WLASCICIEL
        Owner owner = new Owner("rafal","dolega", 5000);
//        KLIECI
        Client clientManiek = db.getClientByID(1); //kupujacy 1
        Client clientJurand = db.getClientByID(14); //kupujacy 2

//        TRANZAKCJA
        //o godziinie X owner robi tranzakcje z jurandem za 5 po 10,
        // true - owner - sprzedający,
        // false - owner - kupujący
        TransactionForClient transactionForClient = new TransactionForClient(Utils.getDateTime(), owner, clientJurand, 5, 10, true);
        getInfoAboutTransaction(transactionForClient);

        Log.w(TAG, "onCreate: BEFORE TRANSACTION" );
        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
        getListOfOwnerTransactions(owner);
        getListOfClientTransactions(clientJurand);

        RealizeTransactionHelper realizeTransactionHelper = new RealizeTransactionHelper();
        realizeTransactionHelper.realizeTransaction(transactionForClient);
        Log.w(TAG, "onCreate: AFTER TRANSACTION ");

        getInfoAboutOwner(owner);
        getInfoAboutClient(clientJurand);
        getListOfOwnerTransactions(owner);
        getListOfClientTransactions(clientJurand);

    }

    private void getListOfOwnerTransactions(Owner owner){
        Log.i(TAG, "getListOfOwnerTransactions: lista tranzakcji " + owner.getListOfTransaction());
    }

    private void getListOfClientTransactions(Client client){
        Log.i(TAG, "getListOfClientTransactions: lista tranzakcji " + client.getListOfTransaction());
    }

    private void getListOfOwnerPayments(Owner owner){
        Log.i(TAG, "getListOfOwnerPayments: lista zapłat: " + owner.getListOfPayments());
    }

    private void getListOfClientPayments(Client client){
        Log.i(TAG, "getListOfClientPayments: lista zapłat: " + client.getListOfPayments());
    }

    private void getInfoAboutTransaction(TransactionForClient transaction){
        Log.i(TAG, "getInfoAboutTransaction: " + transaction.toString());
    }

    private void getInfoAboutPayments(Payment payment){
        Log.i(TAG, "getInfoAboutPayments: " + payment.toString());
    }
    private void getInfoAboutOwner(Owner owner){
        Log.i(TAG, "getInfoAboutOwner: " + owner.toString());
    }

    private void getInfoAboutClient(Client client){
        Log.i(TAG, "getInfoAboutClient: " + client.toString());
    }

    private void printList(List<Client> list){
        if(list.size() == 0 )
            Log.i(TAG, "printList: LISTA PUSTA");
        Log.i(TAG, "printList: hao");
        for(Client c : list)
            Log.i(TAG, "printList: drukuje klienta: " + c.toString());
    }

    private void deleteAllClients() {
        
        List<Client> listOfClients = db.getAllClient();

        int liczbaklientuw = listOfClients.size();

//        while(liczbaklientuw>0){
//            db.deleteClient(liczbaklientuw);
//        }

        for(int i = 0 ; i<liczbaklientuw;i++){
            db.deleteClient(listOfClients.get(0).getClientId());
        }

    }

    private List<Client> getAllClients() {
        Log.i(TAG, "getAllClients: WSZYSCY KLIENCI");
        List<Client> listOfClients = db.getAllClient();
        Log.i(TAG, "getAllClients: liczba klientów: " + listOfClients.size());
        for(Client c : listOfClients)
            Log.i(TAG, "getAllClients: client: " + c.toString());

        return  listOfClients;
    }
    
    private List<Client> getClientInLeftAmountRange(){
        Log.i(TAG, "getClientInLeftAmountRange: Z ZAKRESU OD DO HAJSU");
        
        List<Client> listOfClientWithLeftAmountFromTo = new ArrayList<>();

        listOfClientWithLeftAmountFromTo = db.getClientWithLeftAmountSorted(50,150);


        for(Client client : listOfClientWithLeftAmountFromTo){
            Log.i(TAG, "onCreate: uzytkownik: " + client.toString());
        }
        
        return listOfClientWithLeftAmountFromTo;
    }

    private List<Client> getClientsByName(String name){

        List<Client> listOfUserByName = new ArrayList<>();

        listOfUserByName =  db.getClientByName(name);
        return listOfUserByName;
    }



}

