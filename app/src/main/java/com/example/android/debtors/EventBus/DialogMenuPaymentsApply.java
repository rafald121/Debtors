package com.example.android.debtors.EventBus;


import java.util.Date;

/**
 * Created by admin on 24.03.2017.
 */

public class DialogMenuPaymentsApply {

    private Date fromDate = null;
    private Date toDate = null;
    private int minRange = 0;
    private int maxRange = 0;

    public DialogMenuPaymentsApply(Date fromDate, Date toDate, int minRange, int maxRange) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public Date getMinDate() {
        return fromDate;
    }

    public Date getMaxDate() {
        return toDate;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }
}
