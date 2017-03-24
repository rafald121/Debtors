package com.example.android.debtors.EventBus;

import java.util.Date;

/**
 * Created by admin on 24.03.2017.
 */

public class DialogMenuPaymentsApplyReceivedOrGiven {

    private Date fromDate = null;
    private Date toDate = null;
    private int minRange = 0;
    private int maxRange = 0;
    private boolean type; //true = received , false = given

    public DialogMenuPaymentsApplyReceivedOrGiven(Date fromDate, Date toDate, int minRange, int maxRange, boolean type) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.type = type;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public boolean isReceivedOrGiven() {
        return type;
    }
}
