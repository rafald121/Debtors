package com.example.android.debtors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.debtors.Databases.DatabaseHelper;
import com.example.android.debtors.Model.Client;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());

        Client client1 = new Client("rafal", 50);
        Client client2 = new Client("tomek", 100);
        Client client3 = new Client("konrad", 150);

        Log.i(TAG, "onCreate: info about added clients" + client1.toString());
        Log.i(TAG, "onCreate: info about added clients" + client2.toString());

        long client1_id = db.createClient(client1);
        long client2_id = db.createClient(client2);
        long client3_id = db.createClient(client3);

        Client client001 = db.getClient(client1_id);
        Log.i(TAG, "onCreate: get client: "+ client001.toString());


        
    }


}
