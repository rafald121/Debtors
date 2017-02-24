package com.example.android.debtors.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.debtors.Model.Owner;

import java.util.ArrayList;
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

    private static final String OWNER_ID = "ownerId";
    private static final String OWNER_NAME = "ownerName";
    private static final String OWNER_TOTAL_AMOUNT = "ownerTotalAmount";
    private static final String OWNER_OWN_AMOUNT = "ownerOwnAmount";

    private static final String CREATE_TABLE_OWNER = "CREATE TABLE " + TABLE_OWNER
            + "("
            + OWNER_ID + " INTEGER  PRIMARY KEY, "
            + OWNER_NAME + " TEXT, "
            + OWNER_TOTAL_AMOUNT + " INTEGER, "
            + OWNER_OWN_AMOUNT + " INTEGER "
            + ")";

    public DatabaseOwner(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_OWNER);
    }

    public long createOwner(Owner owner){
        Log.i(TAG, "createOwner: argument: " + owner.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(OWNER_NAME, owner.getOwnerName());
        values.put(OWNER_TOTAL_AMOUNT, owner.getOwnerTotalAmount());
        values.put(OWNER_OWN_AMOUNT, owner.getOwnerOwnAmount());

        long ownerID = db.insert(TABLE_OWNER, null, values);

        Log.i(TAG, "createOwner: before return ownerID: " + ownerID);

        return ownerID;
    }

    public Owner getOwner(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_OWNER + " WHERE " + OWNER_ID + " = " + id;
        Cursor c = db.rawQuery(query, null);

        if(c!=null)
            c.moveToFirst();

        Owner owner = new Owner();
        owner.setOwnerID(c.getInt(c.getColumnIndex(OWNER_ID)));
        owner.setOwnerName(c.getString(c.getColumnIndex(OWNER_NAME)));
        owner.setOwnerTotalAmount(c.getInt(c.getColumnIndex(OWNER_TOTAL_AMOUNT)));
        owner.setOwnerOwnAmount(c.getInt(c.getColumnIndex(OWNER_OWN_AMOUNT)));

        return owner;
    }

    public List<Owner> getAllOwners(){
        List<Owner> owners = new ArrayList<>();
        String query = " SELECT  * FROM " + TABLE_OWNER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Owner owner = new Owner();

                owner.setOwnerID(c.getInt(c.getColumnIndex(OWNER_ID)));
                owner.setOwnerName(c.getString(c.getColumnIndex(OWNER_NAME)));
                owner.setOwnerTotalAmount(c.getInt(c.getColumnIndex(OWNER_TOTAL_AMOUNT)));
                owner.setOwnerOwnAmount(c.getInt(c.getColumnIndex(OWNER_OWN_AMOUNT)));

                owners.add(owner);
            } while (c.moveToNext());
        }

        return owners;

    }


    public int updateOwner(Owner owner){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(OWNER_NAME, owner.getOwnerName());
        values.put(OWNER_TOTAL_AMOUNT, owner.getOwnerTotalAmount());
        values.put(OWNER_OWN_AMOUNT, owner.getOwnerOwnAmount());

        return db.update(TABLE_OWNER, values, OWNER_ID + " = ?", new String[]{String.valueOf(owner.getOwnerID())});

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OWNER);
    }
}
