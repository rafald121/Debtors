package com.example.android.debtors.EventBus;

import java.util.Date;

/**
 * Created by admin on 24.03.2017.
 */

public class DialogMenuTransactionsApplyAll {

    private Date fromDate = null;
    private Date toDate = null;
    private int minQuantity = 0;
    private int maxQuantity = 0;
    private int minTotalAmount = 0;
    private int maxTotalAmount = 0;

    public DialogMenuTransactionsApplyAll(Date fromDate, Date toDate, int minQuantity, int maxQuantity, int minTotalAmount, int maxTotalAmount) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
        this.minTotalAmount = minTotalAmount;
        this.maxTotalAmount = maxTotalAmount;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public int getMinTotalAmount() {
        return minTotalAmount;
    }

    public int getMaxTotalAmount() {
        return maxTotalAmount;
    }
}