package com.example.android.debtors.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.debtors.Model.Client;

/**
 * Created by Rafaello on 2017-02-09.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    // Logcat tag
    private static final String TAG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "debtsDatabase";

    //TABLE NAMES
    private static final String TABLE_CLIENTS = "clients";
    private static final String TABLE_PRODUCTS = "products";

    private static final String CLIENT_ID = "clientID";
    private static final String CLIENT_NAME = "clientName";
    private static final String CLIENT_LEFT_AMOUNT = "clientLeftAmount";
    //TODO list of transaction

    private static final String PRODUCT_ID = "productID";
    private static final String PRODUCT_NAME = "productName";
    private static final String PRODUCT_LEFT_QUANTITY = "productLeftQuantity";

    private static final String CREATE_TABLE_CLIENT = "CREATE TABLE " + TABLE_CLIENTS + "(" +
            CLIENT_ID + " INTEGER PRIMARY KEY, " + CLIENT_NAME + " TEXT, " + CLIENT_LEFT_AMOUNT +
            " INTEGER, ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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

        Log.i(TAG, "createClient: before return clientID" + clientID);

        return clientID;
    }

    //ZWROC POJEDYNCZEGO KLIENTA
    public Client getClient(long clientID){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS + " WHERE " + CLIENT_ID + " = " + clientID;

        Cursor c = db.rawQuery(query,null);

        if ( c!= null)
            c.moveToFirst();

        Client client = new Client();
        client.setClientId(c.getInt(c.getColumnIndex(CLIENT_ID)));
        client.setClientName(c.getString(c.getColumnIndex(CLIENT_NAME)));
        client.setClientLeftAmount(c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT)));

        return client;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

