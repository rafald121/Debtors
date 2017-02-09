package com.example.android.debtors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.debtors.Databases.DatabaseHelper;
import com.example.android.debtors.Model.Client;

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

//        Client client1 = new Client("rafal", 50);
////        Client client2 = new Client("tomek", 100);
////        Client client3 = new Client("konrad", 150);
//
////        Log.i(TAG, "onCreate: info about added clients" + client1.toString());
////        Log.i(TAG, "onCreate: info about added clients" + client2.toString());
////
//        long client1_id = db.createClient(client1);
//        long client2_id = db.createClient(client2);
//        long client3_id = db.createClient(client3);

//        Client client001 = db.getClient(client1_id);
//        Log.i(TAG, "onCreate: get client: "+ client001.toString());



//        deleteAllClients();


//        client1.setClientLeftAmount(500);
//        client1.setClientName("rafaellodp");

//        Client clientToEdit = db.getClient(1);
//        clientToEdit.setClientName("rafaellodp");
//        clientToEdit.setClientLeftAmount(1000);
//
//        int updatedID = db.updateClient(clientToEdit);
//
//
//        Log.i(TAG, "onCreate: before delete in range");
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
//        db.deleteClient(24);
        listOfAllClientFromDatabase = getAllClients();
//        db.deleteClientInRange(14,24);
        listOfClientWithLeftAmountFromTo = db.getClientWithLeftAmountSorted(50,150);
//        Log.i(TAG, "onCreate: id = 0 : " + db.getClient(0));
        for(Client client : listOfClientWithLeftAmountFromTo){
            Log.i(TAG, "onCreate: uzytkownik: " + client.toString());
        }
//                listOfClientWithLeftAmountFromTo.toString());

    }

    private void deleteAllClients() {
        List<Client> listOfClients = db.getAllClient();

        int liczbaklientuw = listOfClients.size();

        while(liczbaklientuw>0){
            db.deleteClient(liczbaklientuw);
        }

    }

    private List<Client> getAllClients() {

        List<Client> listOfClients = db.getAllClient();
        Log.i(TAG, "getAllClients: liczba klientów: " + listOfClients.size());
        for(Client c : listOfClients)
            Log.i(TAG, "getAllClients: client: " + c.toString());

        return  listOfClients;
    }


}

