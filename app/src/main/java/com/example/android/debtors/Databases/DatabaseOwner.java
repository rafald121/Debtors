package com.example.android.debtors.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.Payment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            + "("
//            + OWNER_NR_STATE + " INTEGER  PRIMARY KEY, "
//            + OWNER_STATE_DATE + " TEXT, "
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
        Log.i(TAG, "createOwner: argument: " + owner.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        List<Payment> listOfPayments = owner.getListOfPayments();
        Log.i(TAG, "createOwner: after assign list from ownerlist to listOfPayment" + listOfPayments.toString());

        List<JSONObject> listOfPaymentsObject = new ArrayList<>();
        for(int i = 0 ; i < listOfPayments.size() ; i++) {
            HashMap<String, Payment> map = new HashMap<>();
            map.put("object", listOfPayments.get(i));
            JSONObject jsonPayment = new JSONObject(map);
            listOfPaymentsObject.add(jsonPayment);
        }

        JSONArray jsonArray = new JSONArray(listOfPaymentsObject);
        Log.i(TAG, "createOwner: JSON ARRAY : " + jsonArray.toString());
        String payments = jsonArray.toString();
        Log.i(TAG, "createOwner: payment string : " + payments);


        ContentValues values = new ContentValues();

//        values.put(OWNER_STATE_DATE, Utils.getDateTime());
        values.put(OWNER_TOTAL_AMOUNT, owner.getOwnerTotalAmount());
        values.put(OWNER_OWN_AMOUNT, owner.getOwnerOwnAmount());
        values.put(OWNER_PAYMENTS, payments);
//        values.put(OWNER_TRANSACTIONS, transactions);

        long ownerID = db.insert(TABLE_OWNER, null, values);

        return ownerID;

    }

    public Owner getOwner() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_OWNER;
        Cursor c = db.rawQuery(query, null);

        if(c!=null)
            c.moveToFirst();

        JSONObject payments = new JSONObject(c.getString(c.getColumnIndex(OWNER_PAYMENTS)));
        JSONArray paymentsList = payments.optJSONArray("uniqueArrays");
        ArrayList<Payment> listOfPayments = new ArrayList<>();

//        for(int i = 0 ; i< paymentsList.length() ; i++){
//            listOfPayments.add(paymentsList.getJSONObject(paymentsList));
//        }

        Owner owner = new Owner();
        owner.setOwnerTotalAmount(c.getInt(c.getColumnIndex(OWNER_TOTAL_AMOUNT)));
        owner.setOwnerOwnAmount(c.getInt(c.getColumnIndex(OWNER_OWN_AMOUNT)));

        return owner;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
