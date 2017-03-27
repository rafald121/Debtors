package com.example.android.debtors.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bumptech.glide.load.data.StreamAssetPathFetcher;
import com.example.android.debtors.Model.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

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

        if ( c!= null && c.getCount() >= 0){
            c.moveToFirst();

            Client client = new Client();

            client.setClientId(c.getInt(c.getColumnIndex(CLIENT_ID)));
            client.setClientName(c.getString(c.getColumnIndex(CLIENT_NAME)));
            client.setClientLeftAmount(c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT)));

            return client;
        } else
            return null;
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

    public List<Client> fillListFromHashMap(HashMap<Integer,Integer> hashMap){
        List<Client> clientsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS;

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst())
            do{
                Client client = new Client();
                client.setClientId(c.getInt(c.getColumnIndex(CLIENT_ID)));

            }while (c.moveToNext());

        return clientsList;
    }


    public List<String> getListOfClientsNames(){

        List<String> clientsNamesList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS;

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                clientsNamesList.add(c.getString(c.getColumnIndex(CLIENT_NAME)));

            } while (c.moveToNext());
        }

        return clientsNamesList;
    }

    public List<HashMap<Integer, String>> getListOfMapIntStringOfAllClients(){

        List<HashMap<Integer, String>> listOfMap = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = " SELECT  * FROM " + TABLE_CLIENTS;

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst())
            do{
                HashMap<Integer, String> map = new HashMap<>();

                map.put(c.getInt(c.getColumnIndex(CLIENT_ID)), c.getString(c.getColumnIndex(CLIENT_NAME)));

                listOfMap.add(map);
            } while (c.moveToNext());

        return listOfMap;
    }

    public HashMap<Integer, Integer> fillMapWithClientsIDs() {

        HashMap<Integer,Integer> map = new HashMap<>();

        List<Client> listOfClients = getAllClient();

        for(Client c: listOfClients){
            map.put(c.getClientId(),0);
        }

        return map;
    }

    public HashMap<Integer, String> getHashMapIntStringOfAllClients(){

        HashMap<Integer, String> hashmap = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = " SELECT  * FROM " + TABLE_CLIENTS;

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst())
            do{
                hashmap.put(c.getInt(c.getColumnIndex(CLIENT_ID)), c.getString(c.getColumnIndex(CLIENT_NAME)));
            } while (c.moveToNext());

        return hashmap;
    }

    public int getAmountOfAllClient(){
        int clientsAmount = 0 ;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS;

        Cursor c = db.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
                clientsAmount ++;
            }while (c.moveToNext());
        }

        return clientsAmount;
    }

    public int getDebtorsAmount(){
        int debtorsAmount = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS + " WHERE " + CLIENT_LEFT_AMOUNT + " != " + " 0";

        Cursor c = db.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
                debtorsAmount++;
            }while (c.moveToNext());
        }

        return debtorsAmount;
    }

    public List<Client> getListOfClientWithLeftAmountInRange(int min, int max){
        List<Client> clientsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS + " WHERE " + CLIENT_LEFT_AMOUNT + " >= "
                + min + " AND " + CLIENT_LEFT_AMOUNT + " <= " + max;

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
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

    public List<Client> getClientWithLeftAmountMoreOrLessZero(boolean flag) {//if true >0, false <0
        List<Client> clientList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + TABLE_CLIENTS;

        Cursor c = db.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
                if(flag) {//wez klientow z kwotą większą od zera włącznie
                    if (c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT)) > 0) {
                        Client client = new Client();

                        client.setClientId(c.getInt(c.getColumnIndex(CLIENT_ID)));
                        client.setClientName(c.getString(c.getColumnIndex(CLIENT_NAME)));
                        client.setClientLeftAmount(c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT)));

                        clientList.add(client);
                    }
                }else{
                    if(c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT))<0){
                        Client client = new Client();

                        client.setClientId(c.getInt(c.getColumnIndex(CLIENT_ID)));
                        client.setClientName(c.getString(c.getColumnIndex(CLIENT_NAME)));
                        client.setClientLeftAmount(c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT)));

                        clientList.add(client);
                    }
                }
            } while (c.moveToNext());
        }
        return clientList;
    }

    public int getClientWithHighestLeftAmount(){

        int highestLeftAmount = 0;
        int indexOfHighest = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS;

        Cursor c = db.rawQuery(query, null);

        int currLeftAmount = 0;
        int currIndex = 0;

        if(c.moveToFirst()){
            do{
                currLeftAmount = c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT));
                currIndex = c.getInt(c.getColumnIndex(CLIENT_ID));

                if(currLeftAmount > highestLeftAmount ){
                    highestLeftAmount = currLeftAmount;
                    indexOfHighest = currIndex;
                }
            }while (c.moveToNext());
        }

        return highestLeftAmount;
    }

    public int getClientWithLowestLeftAmount(){

        int lowestLeftAmount = 0;
        int indexOflowest = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS;

        Cursor c = db.rawQuery(query, null);

        int currLeftAmount = 0;
        int currIndex = 0;

        if(c.moveToFirst()){
            do{
                currLeftAmount = c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT));
                currIndex = c.getInt(c.getColumnIndex(CLIENT_ID));

                if(currLeftAmount < lowestLeftAmount ){
                    lowestLeftAmount = currLeftAmount;
                    indexOflowest = currIndex;
                }
            }while (c.moveToNext());
        }

        return lowestLeftAmount;
    }


    public void deleteClient(long clientID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CLIENTS, CLIENT_ID + " = ?", new String[]{String.valueOf(clientID)});
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

    public boolean isClientWithNameAlreadyExist(String name){

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS;

        Cursor c= db.rawQuery(query, null);

        int index;

        if(c.moveToFirst())
            do{
                if(c.getString(c.getColumnIndex(CLIENT_NAME)).equals(name)) {
                    index = c.getInt(c.getColumnIndex(CLIENT_ID));
                    Log.i(TAG, "isClientWithNameAlreadyExist: index: " + index );
                    return true;
                }
            } while (c.moveToNext());

        return false;

    }

    public int getAmountForMe() {
//        List<Client> listOfClients = new ArrayList<>();

        SQLiteDatabase db= this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS + " WHERE " + CLIENT_LEFT_AMOUNT + " > 0";

        Cursor c = db.rawQuery(query, null);

        int amountForMe = 0;

        if(c.moveToFirst())
            do{
                amountForMe += c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT));
            } while (c.moveToNext());

        return amountForMe;
    }

    public int getAmountMeToOther() {

        SQLiteDatabase db= this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_CLIENTS + " WHERE " + CLIENT_LEFT_AMOUNT + " < 0";

        Cursor c = db.rawQuery(query, null);

        int amountMeToOther = 0;

        if(c.moveToFirst())
            do{
                amountMeToOther += c.getInt(c.getColumnIndex(CLIENT_LEFT_AMOUNT));
            } while (c.moveToNext());

        return amountMeToOther;

    }
}


