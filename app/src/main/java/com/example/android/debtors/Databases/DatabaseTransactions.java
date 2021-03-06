package com.example.android.debtors.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.android.debtors.Model.TransactionForClient;
import com.example.android.debtors.Utils.Utils;
import com.example.android.debtors.Utils.UtilsDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
        values.put(TRANSACTION_OWNER, transactionForClient.getTransactionOwnerID());
        values.put(TRANSACTION_CLIENT, transactionForClient.getTransactionClientID());
        values.put(TRANSACTION_QUANTITY, transactionForClient.getTransactionQuantity());
        values.put(TRANSACTION_ENTRY, transactionForClient.getTransactionEntryPayment());
        values.put(TRANSACTION_PRODUCT_VALUE, transactionForClient.getTransactionProductValue());
        values.put(TRANSACTION_DETAILS, transactionForClient.getTransactionDetails());
        int buyOrSell = (transactionForClient.isTransactionBuyOrSell())? 1:0;
        values.put(TRANSACTION_BUY_OR_SELL, buyOrSell);

        long transactionID = db.insert(TABLE_TRANSACTIONS, null, values);

        return transactionID;
    }

    public List<TransactionForClient> getAllTransactions(){
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

    public int[][] getArrayMapWithMostCommonClients(int fromLastDay){

        DatabaseClients databaseClients = new DatabaseClients(context);

        List<TransactionForClient> transactionForClientsList = new ArrayList<>();

        HashMap<Integer, Integer> hashMap = databaseClients.fillMapWithClientsIDs();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_TRANSACTIONS;

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst())
            do {
                TransactionForClient transactionForClient = new TransactionForClient();

                transactionForClient.setTransactionID(c.getInt(c.getColumnIndex(TRANSACTION_ID)));
                transactionForClient.setTransactionClientID(c.getInt(c.getColumnIndex(TRANSACTION_CLIENT)));
                transactionForClient.setTransactionDate(c.getString(c.getColumnIndex(TRANSACTION_DATE)));

                transactionForClientsList.add(transactionForClient);

            }while (c.moveToNext());

        for(TransactionForClient transactionForClient : transactionForClientsList){
            if(!hashMap.containsKey(transactionForClient.getTransactionClientID())){

                hashMap.put(transactionForClient.getTransactionClientID(), 1);

            } else {

                int val = hashMap.get(transactionForClient.getTransactionClientID());
                hashMap.put(transactionForClient.getTransactionClientID(), val + 1);

            }

        }

        int[][] sortedArray = Utils.sortByHashMapValue(hashMap);

        return sortedArray;
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

    public int getHighestQuantityOfTransactions(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = " SELECT  * FROM " + TABLE_TRANSACTIONS;

        Cursor c = db.rawQuery(query, null);

        int highestQuantity = 0;
        int currentQuantity = 0;

        if(c.moveToFirst()){
            do{
                currentQuantity = c.getInt(c.getColumnIndex(TRANSACTION_QUANTITY));

                if(currentQuantity > highestQuantity)
                    highestQuantity = currentQuantity;

            }while (c.moveToNext());
        }

        return highestQuantity;

    }

    public int getHighestTotalAmountOfTransaction(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = " SELECT  * FROM " + TABLE_TRANSACTIONS;

        Cursor c = db.rawQuery(query, null);

        int highestTotalAmount = 0;
        int currentTotalAmount = 0;

        if(c.moveToFirst()){
            do{
                currentTotalAmount = c.getInt(c.getColumnIndex(TRANSACTION_QUANTITY)) * c.getInt(c.getColumnIndex(TRANSACTION_PRODUCT_VALUE));

                if(currentTotalAmount > highestTotalAmount)
                    highestTotalAmount = currentTotalAmount;

            }while (c.moveToNext());
        }

        return highestTotalAmount;
    }

    public List<TransactionForClient> getTransactionsByQueryInMenuDialog(Date fromDate, Date toDate, int minQuantity, int maxQuantity, int minTotalAmount, int maxTotalAmount) {

        SQLiteDatabase db = this.getReadableDatabase();
        
        List<TransactionForClient> listOfTransactions = new ArrayList<>();
        
        String query = "SELECT  * FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANSACTION_QUANTITY + " >= " + minQuantity + " AND " + TRANSACTION_QUANTITY + " <= " + maxQuantity;

        Cursor c = db.rawQuery(query, null);
        
        int paymentTotalAmount = 0;
        
        if(c.moveToFirst()){
            do{
                String dateOfTransaction = c.getString(c.getColumnIndex(TRANSACTION_DATE));
                Date date = UtilsDate.getDateFromSting(dateOfTransaction);
                
                if(date.before(toDate) && date.after(fromDate)){
                    
                    paymentTotalAmount = c.getInt(c.getColumnIndex(TRANSACTION_QUANTITY))*c.getInt(c.getColumnIndex(TRANSACTION_PRODUCT_VALUE));
                    
                    if(paymentTotalAmount>=minTotalAmount && paymentTotalAmount<=maxTotalAmount){

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
                        
                    } else
                        Log.e(TAG, "getTransactionsByQueryInMenuDialog: totalAmount isn't fulfill condition, totalAmount: " + paymentTotalAmount );
                } else
                    Log.e(TAG, "getTransactionsByQueryInMenuDialog: date isn't fullfill condition" );
            }while (c.moveToNext());
        }

        return listOfTransactions;

    }

    public List<TransactionForClient> getTransactionsByQueryInMenuDialog(Date fromDate, Date toDate, int minQuantity, int maxQuantity, int minTotalAmount, int maxTotalAmount, int typeOfTransactions) {

        int tmpType=-1;
        if(typeOfTransactions == 1)
            tmpType = 1;
        else if (typeOfTransactions == 2)
            tmpType = 0;
        else
            Log.e(TAG, "getTransactionsByQueryInMenuDialog: ERROR, tmpType: " + tmpType + " type as an argument: " + typeOfTransactions );

        SQLiteDatabase db = this.getReadableDatabase();

        List<TransactionForClient> listOfTransactions = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANSACTION_QUANTITY + " >= " + minQuantity + " AND " + TRANSACTION_QUANTITY + " <= " + maxQuantity + " AND " + TRANSACTION_BUY_OR_SELL + " = " + tmpType;

        Cursor c = db.rawQuery(query, null);

        int paymentTotalAmount = 0;

        if(c.moveToFirst()){
            do{
                String dateOfTransaction = c.getString(c.getColumnIndex(TRANSACTION_DATE));
                Date date = UtilsDate.getDateFromSting(dateOfTransaction);

                if(date.before(toDate) && date.after(fromDate)){

                    paymentTotalAmount = c.getInt(c.getColumnIndex(TRANSACTION_QUANTITY))*c.getInt(c.getColumnIndex(TRANSACTION_PRODUCT_VALUE));

                    if(paymentTotalAmount>=minTotalAmount && paymentTotalAmount<=maxTotalAmount){

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

                    } else
                        Log.e(TAG, "getTransactionsByQueryInMenuDialog: totalAmount isn't fulfill condition, totalAmount: " + paymentTotalAmount );
                } else
                    Log.e(TAG, "getTransactionsByQueryInMenuDialog: date isn't fullfill condition" );
            }while (c.moveToNext());
        }

        return listOfTransactions;
    }

    public void deleteTransaction(long id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TRANSACTIONS, TRANSACTION_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
