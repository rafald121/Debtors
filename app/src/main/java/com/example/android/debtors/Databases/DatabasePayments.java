package com.example.android.debtors.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.debtors.Model.Payment;

import static android.content.ContentValues.TAG;

/**
 * Created by Rafaello on 2017-02-14.
 */

public class DatabasePayments extends SQLiteOpenHelper {


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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PAYMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long createPayment(Payment payment){
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
}


