package com.example.android.debtors.EventBus;

/**
 * Created by admin on 16.03.2017.
 */

public class SearchQuery{
    private final String message;


    public SearchQuery(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}