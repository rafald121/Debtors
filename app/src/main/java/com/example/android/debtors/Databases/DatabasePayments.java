package com.example.android.debtors.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.debtors.Model.Payment;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Rafaello on 2017-02-14.
 */

public class DatabasePayments extends SQLiteOpenHelper {

    Context context;

    DatabaseClients dbClient = new DatabaseClients(context);



    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "paymentsDatabase";
    private static final String TABLE_PAYMENTS = "payments";

    private static final String PAYMENT_ID = "paymentID";
    private static final String PAYMENT_DATE = "paymentDate";
    private static final String PAYMENT_OWNER = "paymentOwner";
    private static final String PAYMENT_CLIENT = "paymentClient";
    private static final String PAYMENT_AMOUNT = "paymentAmount";
    private static final String PAYMENT_GOT_OR_GIVEN = "paymentGotOrGiven";

    private static final String CREATE_TABLE_PAYMENTS = "CREATE TABLE " + TABLE_PAYMENTS
            + "("
            + PAYMENT_ID + " INTEGER  PRIMARY KEY, "
            + PAYMENT_DATE + " TEXT, "
            + PAYMENT_OWNER + " INTEGER, "
            + PAYMENT_CLIENT + " INTEGER, "
            + PAYMENT_AMOUNT + " INTEGER, "
            + PAYMENT_GOT_OR_GIVEN + " INTEGER "
            + ")";

    public DatabasePayments(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PAYMENTS);
//You'll have to attach Database X with Database Y using the ATTACH command, then run the appropriate Insert Into commands for the tables you want to transfer.
//        INSERT INTO X.TABLE(fieldname1, fieldname2) SELECT fieldname1, fieldname2 FROM Y.TABLE;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long createPayment(Payment payment){
        if (payment == null)
            Log.e(TAG, "createPayment: PAYMENT IS NULL" );
        else
            Log.e(TAG, "createPayment: PAYMENT IS NOT NULL" );

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PAYMENT_DATE, payment.getPaymentDate());
        values.put(PAYMENT_OWNER, payment.getPaymentOwner().getOwnerID());
        values.put(PAYMENT_CLIENT, payment.getPaymentClient().getClientId());
        values.put(PAYMENT_AMOUNT, payment.getPaymentAmount());
        int gotOrGiven = (payment.isPaymentGotOrGiven())? 1:0;
        Log.i(TAG, "createPayment: gotOrGiven value " + gotOrGiven);
        values.put(PAYMENT_GOT_OR_GIVEN, gotOrGiven);

        long paymentID = db.insert(TABLE_PAYMENTS, null, values);

        Log.i(TAG, "createPayment: payment ID before add to db: " + paymentID);

        return paymentID;

    }

    public List<Payment> getAllPayments(){
        List<Payment> listOfPayments = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_PAYMENTS;

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Payment payment = new Payment();

                payment.setPaymentID(c.getInt(c.getColumnIndex(PAYMENT_ID)));
                payment.setPaymentDate(c.getString(c.getColumnIndex(PAYMENT_DATE)));
                payment.setPaymentOwnerID(c.getInt(c.getColumnIndex(PAYMENT_OWNER)));
                payment.setPaymentClientID(c.getInt(c.getColumnIndex(PAYMENT_CLIENT)));
                payment.setPaymentAmount(c.getInt(c.getColumnIndex(PAYMENT_AMOUNT)));
                payment.setPaymentGotOrGiven(c.getInt(c.getColumnIndex(PAYMENT_GOT_OR_GIVEN)));

                listOfPayments.add(payment);
            } while (c.moveToNext());
        }

        return listOfPayments;
    }


//    RETURN LIST OF PAYMENTS THAT CLIENT PAID ,,, BY CLIENT ID
    public List<Payment> getPaymentsFromClient(long clientID){

        SQLiteDatabase db = this.getReadableDatabase();

        List<Payment> listOfPayments = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_PAYMENTS + " WHERE " + PAYMENT_CLIENT + " = " + clientID;

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Payment payment = new Payment();

                payment.setPaymentID(c.getInt(c.getColumnIndex(PAYMENT_ID)));
                payment.setPaymentDate(c.getString(c.getColumnIndex(PAYMENT_DATE)));
                payment.setPaymentOwnerID(c.getInt(c.getColumnIndex(PAYMENT_OWNER)));
                payment.setPaymentClientID(c.getInt(c.getColumnIndex(PAYMENT_CLIENT)));
                payment.setPaymentAmount(c.getInt(c.getColumnIndex(PAYMENT_AMOUNT)));
                payment.setPaymentGotOrGiven(c.getInt(c.getColumnIndex(PAYMENT_GOT_OR_GIVEN)));

                listOfPayments.add(payment);

            }while (c.moveToNext());
        }

        return listOfPayments;
    }

    public List<Payment> getPaymentsFromOwner(long ownerID){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Payment> listOfPayments = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_PAYMENTS + " WHERE " + PAYMENT_OWNER + " = " +
                ownerID;
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Payment payment = new Payment();

                payment.setPaymentID(c.getInt(c.getColumnIndex(PAYMENT_ID)));
                payment.setPaymentDate(c.getString(c.getColumnIndex(PAYMENT_DATE)));
                payment.setPaymentOwnerID(c.getInt(c.getColumnIndex(PAYMENT_OWNER)));
                payment.setPaymentClientID(c.getInt(c.getColumnIndex(PAYMENT_CLIENT)));
                payment.setPaymentAmount(c.getInt(c.getColumnIndex(PAYMENT_AMOUNT)));
                payment.setPaymentGotOrGiven(c.getInt(c.getColumnIndex(PAYMENT_GOT_OR_GIVEN)));

                listOfPayments.add(payment);

            }while (c.moveToNext());
        }

        return listOfPayments;
    }
    public void deletePayment(long paymentID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PAYMENTS, PAYMENT_ID + " = ?", new String[]{String.valueOf(paymentID)});
    }

    public void deletePaymentInRange(long minID, long maxID){

        SQLiteDatabase db = this.getWritableDatabase();

        for(long i = minID ; i < maxID ; i++){
            db.delete(TABLE_PAYMENTS, PAYMENT_ID + " = ?" , new String[]{String.valueOf(i)});
        }

    }


    public List<Payment> getPaymentsByType(boolean receivedOrGive) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Payment> listOfPayments = new ArrayList<>();

        String query = "";

        if (receivedOrGive)//received
            query = "SELECT  * FROM " + TABLE_PAYMENTS + " WHERE " + PAYMENT_GOT_OR_GIVEN + " = 1";
        else//give
            query = "SELECT  * FROM " + TABLE_PAYMENTS + " WHERE " + PAYMENT_GOT_OR_GIVEN + " = 0";

        Cursor c = db.rawQuery(query, null);


        if (c.moveToFirst()) {
            do {
                Payment payment = new Payment();

                payment.setPaymentID(c.getInt(c.getColumnIndex(PAYMENT_ID)));
                payment.setPaymentDate(c.getString(c.getColumnIndex(PAYMENT_DATE)));
                payment.setPaymentOwnerID(c.getInt(c.getColumnIndex(PAYMENT_OWNER)));
                payment.setPaymentClientID(c.getInt(c.getColumnIndex(PAYMENT_CLIENT)));
                payment.setPaymentAmount(c.getInt(c.getColumnIndex(PAYMENT_AMOUNT)));
                payment.setPaymentGotOrGiven(c.getInt(c.getColumnIndex(PAYMENT_GOT_OR_GIVEN)));

                listOfPayments.add(payment);
            } while (c.moveToNext());
        }

        return listOfPayments;
    }
}


