package com.example.android.debtors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.debtors.Databases.DatabaseHelper;
import com.example.android.debtors.Logic.RealizeTransaction;
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

    DatabaseHelper db;
    String[] names = {"rafal", "marek", "karol", "adrian" , "tomek" , "jan", "andrzejek",
            "maniek", "maniok", "chamiok"};
    HashMap<Long,Client> clientsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());

        List<Client> listOfClient = new ArrayList<>();
        List<Client> listOfAllClientFromDatabase = new ArrayList<>();
        List<Client> listOfClientWithLeftAmountFromTo = new ArrayList<>();
        List<Client> listOfUserByName = new ArrayList<>();
        listOfAllClientFromDatabase = getAllClients();
        listOfClientWithLeftAmountFromTo = getClientInLeftAmountRange();

        RealizeTransaction realizeTransaction = null;

//        WLASCICIEL
        Owner owner = new Owner("rafal","dolega", 5000);
//        KLIECI
        Client clientManiek = db.getClientByID(1); //kupujacy 1
        Client clientJurand = db.getClientByID(14); //kupujacy 2

//        PLATNOSC, clientJurand - klient, 50 - kwota, true - dostaję, false - płacę
        Payment payment = new Payment(Utils.getDateTime(), clientJurand, 50, true);//tworzony obiekt

//        TRANZAKCJA
        //o godziinie X owner robi tranzakcje z jurandem za 5 po 10,
        // true - owner - sprzedający,
        // false - owner - kupujący
        TransactionForClient transactionForClient = new TransactionForClient(Utils.getDateTime(), owner, clientJurand, 5, 10, true);

        


        //wlasciciel przyjmuje platnosc za tranzakcje,
        //clientJurand - klient wlasciciela
        // \/ wlasciciel aktualizuje kto zakupił
//        TODO jesli "wlasciciel" akceptuje tranzakcje to jemu przybywa tyle ile tranzakcja
//        wlasciciel.acceptTransaction(transaction,clientJurand);
//        Log.i(TAG, "onCreate: wlasciciel sprzedal i teraz: " + wlasciciel.toString());




        // clientJurand zakupił więc sie wykosztował za transaction
        //TODO jeśli tranzakcja będzie miała kupującego i sprzedającego to dodać
        //TODO aby jurandowi usuwało
//        Log.i(TAG, "onCreate: ");
//        clientJurand.changeClientLeftAmount(transaction);
//        Log.i(TAG, "onCreate: jurand kupił i teraz: " + clientJurand.toString());


//        wlasciciel.payForClient(payment);
//        db.updateClient(wlasciciel);
//        Log.i(TAG, "onCreate: client info: " + wlasciciel.toString());



//        printList(listOfUserByName);

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

