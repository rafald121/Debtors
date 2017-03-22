package com.example.android.debtors.EventBus;

/**
 * Created by admin on 23.03.2017.
 */

public class SearchQueryForMe {

    private final String message;

    public SearchQueryForMe(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
