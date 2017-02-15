package com.example.android.debtors.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.debtors.Model.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-09.
 */

public class DatabaseClients extends SQLiteOpenHelper{

    // Logcat tag
    private static final String TAG = DatabaseClients.class.getName();

       // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "clientsDatabase";

    //TABLE NAMES
    private static final String TABLE_CLIENTS = "clients";
    private static final String TABLE_PRODUCTS = "products";

    private static final String CLIENT_ID = "clientID";
    private static final String CLIENT_NAME = "clientName";
    private static final String CLIENT_LEFT_AMOUNT = "clientLeftAmount";
    private static final String CLIENT_TRANSACTIONS_LIST = "clientTransactions";
    private static final String CLIENT_PAYMENTS_LIST = "clientPayments";
    //TODO list of transaction

    private static final String PRODUCT_ID = "productID";
    private static final String PRODUCT_NAME = "productName";
    private static final String PRODUCT_LEFT_QUANTITY = "productLeftQuantity";

    private static final String CREATE_TABLE_CLIENT = "CREATE TABLE " + TABLE_CLIENTS
            + "("
            + CLIENT_ID + " INTEGER  PRIMARY KEY, "
            + CLIENT_NAME + " TEXT, "
            + CLIENT_LEFT_AMOUNT + " INTEGER, "
            + CLIENT_PAYMENTS_LIST + " TEXT, "
            + CLIENT_TRANSACTIONS_LIST + " TEXT " + ")";

    public DatabaseClients(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseClients(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseClients(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    //CREATE TABLES
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLIENT);
    }


    //DODAJ KLIENTA
    public long createClient(Client client){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CLIENT_NAME, client.getClientName());
        values.put(CLIENT_LEFT_AMOUNT, client.getClientLeftAmount());

        long clientID = db.insert(TABLE_CLIENTS, null, values);

        Log.i(TAG, "createClient: before return clientID: " + clientID);

        return clientID;
    }

    //ZWROC POJEDYNCZEGO KLIENTA
    public Client getClientByID(long clientID){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS + " WHERE " + CLIENT_ID + " = " +
                clientID ;

        Cursor c = db.rawQuery(query,null);

        if ( c!= null)
            c.moveToFirst();

        Client client = new Client();

        client.setClientId(c.getInt(c.getColumnIndex(CLIENT_ID)));
        client.setClientName(c.getString(c.getColumnIndex(CLIENT_NAME)));
        client.setClientLeftAmount(c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT)));

        return client;
    }

    public List<Client> getClientByName(String name){
        List<Client> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS + " WHERE " + CLIENT_NAME + " = \"" +
                name +"\"";

        Cursor c = db.rawQuery(query,null);

        if(c.moveToFirst())
            do{
                Client client = new Client();

                client.setClientId(c.getInt(c.getColumnIndex(CLIENT_ID)));
                client.setClientName(c.getString(c.getColumnIndex(CLIENT_NAME)));
                client.setClientLeftAmount(c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT)));

                list.add(client);
            } while (c.moveToNext());

        return list;
    }



    public List<Client> getAllClient(){
        List<Client> clientsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + TABLE_CLIENTS;

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do {
                Client client = new Client();
                client.setClientId(c.getInt(c.getColumnIndex(CLIENT_ID)));
                client.setClientName(c.getString(c.getColumnIndex(CLIENT_NAME)));
                client.setClientLeftAmount(c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT)));

                clientsList.add(client);
            } while (c.moveToNext());
        }

        return clientsList;
    }

    public List<Client> getClientWithLeftAmountSorted(int min, int max){
        List<Client> clientsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + TABLE_CLIENTS;

        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst())
            do {
                if(     c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT)) >= min &&
                        c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT)) <= max){
                    Client client = new Client();

                    client.setClientId(c.getInt(c.getColumnIndex(CLIENT_ID)));
                    client.setClientName(c.getString(c.getColumnIndex(CLIENT_NAME)));
                    client.setClientLeftAmount(c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT)));

                    clientsList.add(client);
                }
            } while (c.moveToNext());

        return clientsList;
    }

    public void deleteClient(long clientID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CLIENTS, CLIENT_ID + " = ?", new String[]{String.valueOf(clientID)});
    }

    public void deleteClientInRange(long minID, long maxID){
        SQLiteDatabase db = this.getWritableDatabase();

        for(long i = minID; i<maxID ; i++){
            db.delete(TABLE_CLIENTS, CLIENT_ID + " = ?", new String[]{String.valueOf(i)});
        }
    }

    public int updateClient(Client client){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CLIENT_NAME, client.getClientName());
        values.put(CLIENT_LEFT_AMOUNT, client.getClientLeftAmount());

        return db.update(TABLE_CLIENTS, values, CLIENT_ID + " = ?", new String[]{String.valueOf
                (client.getClientId())});
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

