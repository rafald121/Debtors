package com.example.android.debtors.Utils;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 24.03.2017.
 */

public class UtilsDate {

    private static final String TAG = UtilsDate.class.getSimpleName();

    public static Date getDateFromSting(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date datee = null;
        try {
            datee = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "getDateFromSting: " + datee.toString());
        return datee;
    }


//    public static boolean ifDateIsBefore
}
