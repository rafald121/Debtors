package com.example.android.debtors.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rafaello on 2017-02-12.
 */

public class DatabaseOwner extends SQLiteOpenHelper {

    private static final String TAG = DatabaseOwner.class.getName();
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "ownerDatabase";

    private static final String TABLE_OWNER = "owner";

    private static final String OWNER_NR_STATE = "ownerNrState";
    private static final String OWNER_STATE_DATE = "ownerStateDate";
    private static final String OWNER_TOTAL_AMOUNT = "ownerTotalAmount";
    private static final String OWNER_OWN_AMOUNT = "ownerOwnAmount";
    private static final String OWNER_PAYMENTS = "ownerPayments";
    private static final String OWNER_TRANSACTIONS = "ownerTransactions";

    private static final String CREATE_TABLE_OWNER = "CREATE TABLE " + TABLE_OWNER
            + "(" + OWNER_NR_STATE + " INTEGER  PRIMARY KEY, "
            + OWNER_STATE_DATE + " TEXT, "
            + OWNER_TOTAL_AMOUNT + " INTEGER, "
            + OWNER_OWN_AMOUNT + " INTEGER, "
            + OWNER_PAYMENTS + " TEXT, "
            + OWNER_TRANSACTIONS + " TEXT " + ")";

    public DatabaseOwner(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_OWNER);
    }

    public long createOwner(Owner owner) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();

        JSONObject jsonPayments = new JSONObject();
        jsonPayments.put("payments", new JSONArray(owner.getListOfPayments()));
        String payments = jsonPayments.toString();

        JSONObject jsonTransactions = new JSONObject();
        jsonTransactions.put("transactions", new JSONArray(owner.getListOfTransaction()));
        String transactions = jsonTransactions.toString();

        ContentValues values = new ContentValues();

        values.put(OWNER_STATE_DATE, Utils.getDateTime());
        values.put(OWNER_TOTAL_AMOUNT, owner.getOwnerTotalAmount());
        values.put(OWNER_OWN_AMOUNT, owner.getOwnerOwnAmount());
        values.put(OWNER_PAYMENTS, payments);
        values.put(OWNER_TRANSACTIONS, transactions);

        long ownerID = db.insert(TABLE_OWNER, null, values);

        return ownerID;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
