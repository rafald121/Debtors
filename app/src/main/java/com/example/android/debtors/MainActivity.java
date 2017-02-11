package com.example.android.debtors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.debtors.Databases.DatabaseHelper;
import com.example.android.debtors.Model.Client;
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


        Client wlasciciel = db.getClientByID(1); //sprzedajacy
        Client clientJurand = db.getClientByID(14); //kupujacy

        Log.i(TAG, "onCreate: przed tranzakcja");
        Log.i(TAG, "onCreate: wlasciciel info: " + wlasciciel.toString());
        Log.i(TAG, "onCreate: jurand info: " + clientJurand.toString());

        // clientJurand to kto płaci,
//        TODO trzeba chyba dodać kto komu placi
        Payment payment = new Payment(Utils.getDateTime(), clientJurand, 50);//tworzony obiekt
        // payment,


        //transakcja
//        TODO dodac kupującego i sprzedającego w tranzakcji
        //jurand placi wlascicielowi za 5 po 10 o godzinie
        TransactionForClient transaction = new TransactionForClient(Utils.getDateTime(),  3, 50);
        TransactionForClient transactionForClient = new TransactionForClient(Utils.getDateTime(),
                wlasciciel, clientJurand, 5,  10);


        //wlasciciel przyjmuje platnosc za tranzakcje,
        //clientJurand - klient wlasciciela
        // \/ wlasciciel aktualizuje kto zakupił
//        TODO jesli "wlasciciel" akceptuje tranzakcje to jemu przybywa tyle ile tranzakcja
        wlasciciel.acceptTransaction(transaction,clientJurand);
        Log.i(TAG, "onCreate: wlasciciel sprzedal i teraz: " + wlasciciel.toString());

        // clientJurand zakupił więc sie wykosztował za transaction
        //TODO jeśli tranzakcja będzie miała kupującego i sprzedającego to dodać
        //TODO aby jurandowi usuwało
        Log.i(TAG, "onCreate: ");
        clientJurand.changeClientLeftAmount(transaction);
        Log.i(TAG, "onCreate: jurand kupił i teraz: " + clientJurand.toString());


        wlasciciel.payForClient(payment);
        db.updateClient(wlasciciel);
        Log.i(TAG, "onCreate: client info: " + wlasciciel.toString());



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

