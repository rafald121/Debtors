package com.example.android.debtors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.debtors.Databases.DatabaseHelper;
import com.example.android.debtors.Model.Client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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


//        for(int i = 0 ; i< 10; i++) {
//            Client client = new Client();
//            client.setClientName(names[i]);
//            client.setClientLeftAmount(i*20+35);
//
////            listOfClient.add(client);
//            long clientId = db.createClient(client);
//
//            clientsMap.put(clientId, client);
//        }




        listOfAllClientFromDatabase = getAllClients();

        listOfClientWithLeftAmountFromTo = getClientInLeftAmountRange();

        List<Client> listOfUserByName = new ArrayList<>();
        
        listOfUserByName = db.getClientByName("rafal");

        printList(listOfUserByName);

    }

    private void printList(List<Client> list){
        Log.i(TAG, "printList: hao");
        for(Client c : list)
            Log.i(TAG, "printList: drukuje klienta: " + c.toString());
    }

    private void deleteAllClients() {
        
        List<Client> listOfClients = db.getAllClient();

        int liczbaklientuw = listOfClients.size();

        while(liczbaklientuw>0){
            db.deleteClient(liczbaklientuw);
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


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}

