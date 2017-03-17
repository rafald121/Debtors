package com.example.android.debtors.Utils;

import android.util.Log;

import com.bumptech.glide.load.data.StreamAssetPathFetcher;
import com.example.android.debtors.Interfaces.InterfaceViewPager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Rafaello on 2017-02-11.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getStringFromResource(int r){
        return String.valueOf(r);
    }

    public static int[][] sortByHashMapValue(HashMap<Integer,Integer> hashMap){

        HashMap<Integer,Integer> argument = new HashMap<>();
        argument = (HashMap) hashMap.clone();

        int[][] sortedArray = new int[hashMap.size()][2];

        int najwiekszaWartoscWystapien = -1; //przechowuje najwieksza ilosc wystąpień

        for(int i = 0 ; i < hashMap.size() ; i++){
            najwiekszaWartoscWystapien = -1;

            for(Map.Entry<Integer,Integer> entry: argument.entrySet()){

                if(entry.getValue() > najwiekszaWartoscWystapien) {

                    sortedArray[i][0] = entry.getKey();
                    sortedArray[i][1] = entry.getValue();

                    najwiekszaWartoscWystapien = entry.getValue();
                }

            }
            argument.remove(sortedArray[i][0]);
        }

        return sortedArray;
    }
}