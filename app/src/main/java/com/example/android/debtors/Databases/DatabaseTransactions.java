package com.example.android.debtors.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.debtors.Model.TransactionForClient;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Rafaello on 2017-02-14.
 */

public class DatabaseTransactions extends SQLiteOpenHelper {

    Context context;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "transactionsDatabase";

    private static final String TABLE_TRANSACTIONS = "transactions";

    private static final String TRANSACTION_ID = "transactionID";
    private static final String TRANSACTION_DATE = "transacationDate";
    private static final String TRANSACTION_OWNER ="transactionOwner";
    private static final String TRANSACTION_CLIENT="transactionClient";
    private static final String TRANSACTION_QUANTITY = "transactionQuantity";
    private static final String TRANSACTION_PRODUCT_VALUE = "transcationProductValue";
    private static final String TRANSACTION_ENTRY="transcationEntry";
    private static final String TRANSACTION_DETAILS = "transactionDetails";
    private static final String TRANSACTION_BUY_OR_SELL = "transcationBuyOrSell";

    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_TRANSACTIONS
            + "("
            + TRANSACTION_ID + " INTEGER  PRIMARY KEY, "
            + TRANSACTION_DATE + " TEXT, "
            + TRANSACTION_OWNER + " INTEGER, "
            + TRANSACTION_CLIENT + " INTEGER, "
            + TRANSACTION_QUANTITY + " INTEGER, "
            + TRANSACTION_PRODUCT_VALUE + " INTEGER, "
            + TRANSACTION_ENTRY + " INTEGER, "
            + TRANSACTION_DETAILS + " TEXT, "
            + TRANSACTION_BUY_OR_SELL + " INTEGER "
            + ")";

    public DatabaseTransactions(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
    }

    public long createTransaction(TransactionForClient transactionForClient){
        if (transactionForClient == null)
            Log.e(TAG, "createTransaction: TRANSACTION IS NULL" );
        else
            Log.i(TAG, "createTransaction: TRANSACTION IS NOT NULL");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TRANSACTION_DATE, transactionForClient.getTransactionDate());
        values.put(TRANSACTION_OWNER, transactionForClient.getTransactionOwner().getOwnerID());
        values.put(TRANSACTION_CLIENT, transactionForClient.getTransactionClient().getClientId());
        values.put(TRANSACTION_QUANTITY, transactionForClient.getTransactionQuantity());
        values.put(TRANSACTION_ENTRY, transactionForClient.getTransactionEntryPayment());
        values.put(TRANSACTION_PRODUCT_VALUE, transactionForClient.getTransactionProductValue());
        values.put(TRANSACTION_DETAILS, transactionForClient.getTransactionDetails());
        int buyOrSell = (transactionForClient.isTransactionBuyOrSell())? 1:0;
        values.put(TRANSACTION_BUY_OR_SELL, buyOrSell);

        long transactionID = db.insert(TABLE_TRANSACTIONS, null, values);

        return transactionID;
    }

    public List<TransactionForClient> getListOfTransaction(){
        List<TransactionForClient> listOfTransaction = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_TRANSACTIONS;

        Cursor c = db.rawQuery(query,null);

        if(c.moveToFirst()){
            do {
                TransactionForClient transaction = new TransactionForClient();

                transaction.setTransactionID(c.getInt(c.getColumnIndex(TRANSACTION_ID)));
                transaction.setTransactionDate(c.getString(c.getColumnIndex(TRANSACTION_DATE)));
                transaction.setTransactionOwnerID(c.getInt(c.getColumnIndex(TRANSACTION_OWNER)));
                transaction.setTransactionClientID(c.getInt(c.getColumnIndex(TRANSACTION_CLIENT)));
                transaction.setTransactionQuantity(c.getInt(c.getColumnIndex(TRANSACTION_QUANTITY)));
                transaction.setTransactionProductValue(c.getInt(c.getColumnIndex(TRANSACTION_PRODUCT_VALUE)));
                transaction.setTransactionEntryPayment(c.getInt(c.getColumnIndex(TRANSACTION_ENTRY)));
                transaction.setTransactionDetails(c.getString(c.getColumnIndex(TRANSACTION_DETAILS)));
                transaction.setTransactionBuyOrSell(c.getInt(c.getColumnIndex(TRANSACTION_BUY_OR_SELL)));

                listOfTransaction.add(transaction);

            }while (c.moveToNext());
        }
        return listOfTransaction;
    }

    public int getAmountOfTransactions(){
        int transactionsAmount = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_TRANSACTIONS;

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do {
                transactionsAmount++;
            }while (c.moveToNext());
        }

        return transactionsAmount;

    }

    public List<TransactionForClient> getTransactionFromClient(long clientID){
        SQLiteDatabase db = this.getReadableDatabase();

        List<TransactionForClient> listOFTransaction = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_TRANSACTIONS + " WHERE "+
                TRANSACTION_CLIENT + " = " + clientID;

        Cursor c = db.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
                TransactionForClient transaction = new TransactionForClient();

                transaction.setTransactionID(c.getInt(c.getColumnIndex(TRANSACTION_ID)));
                transaction.setTransactionDate(c.getString(c.getColumnIndex(TRANSACTION_DATE)));
                transaction.setTransactionOwnerID(c.getInt(c.getColumnIndex(TRANSACTION_OWNER)));
                transaction.setTransactionClientID(c.getInt(c.getColumnIndex(TRANSACTION_CLIENT)));
                transaction.setTransactionQuantity(c.getInt(c.getColumnIndex(TRANSACTION_QUANTITY)));
                transaction.setTransactionProductValue(c.getInt(c.getColumnIndex(TRANSACTION_PRODUCT_VALUE)));
                transaction.setTransactionDetails(c.getString(c.getColumnIndex(TRANSACTION_DETAILS)));
                transaction.setTransactionEntryPayment(c.getInt(c.getColumnIndex(TRANSACTION_ENTRY)));
                transaction.setTransactionBuyOrSell(c.getInt(c.getColumnIndex(TRANSACTION_BUY_OR_SELL)));

                listOFTransaction.add(transaction);
            }while (c.moveToNext());
        }

        return listOFTransaction;
    }

    public List<TransactionForClient> getTransactionFromOwned(long ownerID){
        SQLiteDatabase db = this.getReadableDatabase();

        List<TransactionForClient> listOfTransaction = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANSACTION_OWNER + "" +
                " = " + ownerID;

        Cursor c = db.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
                TransactionForClient transaction = new TransactionForClient();

                transaction.setTransactionID(c.getInt(c.getColumnIndex(TRANSACTION_ID)));
                transaction.setTransactionDate(c.getString(c.getColumnIndex(TRANSACTION_DATE)));
                transaction.setTransactionOwnerID(c.getInt(c.getColumnIndex(TRANSACTION_OWNER)));
                transaction.setTransactionClientID(c.getInt(c.getColumnIndex(TRANSACTION_CLIENT)));
                transaction.setTransactionQuantity(c.getInt(c.getColumnIndex(TRANSACTION_QUANTITY)));
                transaction.setTransactionProductValue(c.getInt(c.getColumnIndex(TRANSACTION_PRODUCT_VALUE)));
                transaction.setTransactionDetails(c.getString(c.getColumnIndex(TRANSACTION_DETAILS)));
                transaction.setTransactionEntryPayment(c.getInt(c.getColumnIndex(TRANSACTION_ENTRY)));
                transaction.setTransactionBuyOrSell(c.getInt(c.getColumnIndex(TRANSACTION_BUY_OR_SELL)));

                listOfTransaction.add(transaction);
            } while (c.moveToNext());
        }

        return listOfTransaction;
    }
//buyOrSell - TRUE - SALE,   buyOrSell - FALSE - PURCHASE
    public List<TransactionForClient> getTransactionsByType(boolean buyOrSell){
        List<TransactionForClient> listOfTransactions = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "";

        if(buyOrSell)
            query = "SELECT  * FROM " + TABLE_TRANSACTIONS + " WHERE " +
                TRANSACTION_BUY_OR_SELL + " = 1";
        else
            query = "SELECT  * FROM " + TABLE_TRANSACTIONS + " WHERE " +
                TRANSACTION_BUY_OR_SELL + " = 0";

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do {
                TransactionForClient transaction = new TransactionForClient();

                transaction.setTransactionID(c.getInt(c.getColumnIndex(TRANSACTION_ID)));
                transaction.setTransactionDate(c.getString(c.getColumnIndex(TRANSACTION_DATE)));
                transaction.setTransactionOwnerID(c.getInt(c.getColumnIndex(TRANSACTION_OWNER)));
                transaction.setTransactionClientID(c.getInt(c.getColumnIndex(TRANSACTION_CLIENT)));
                transaction.setTransactionQuantity(c.getInt(c.getColumnIndex(TRANSACTION_QUANTITY)));
                transaction.setTransactionProductValue(c.getInt(c.getColumnIndex(TRANSACTION_PRODUCT_VALUE)));
                transaction.setTransactionDetails(c.getString(c.getColumnIndex(TRANSACTION_DETAILS)));
                transaction.setTransactionEntryPayment(c.getInt(c.getColumnIndex(TRANSACTION_ENTRY)));
                transaction.setTransactionBuyOrSell(c.getInt(c.getColumnIndex(TRANSACTION_BUY_OR_SELL)));

                listOfTransactions.add(transaction);
            } while (c.moveToNext());
        }

        return listOfTransactions;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
