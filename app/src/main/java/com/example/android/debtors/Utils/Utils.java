package com.example.android.debtors.Utils;

import com.bumptech.glide.load.data.StreamAssetPathFetcher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rafaello on 2017-02-11.
 */

public class Utils {
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getStringFromResource(int r){
        return String.valueOf(r);
    }
}
