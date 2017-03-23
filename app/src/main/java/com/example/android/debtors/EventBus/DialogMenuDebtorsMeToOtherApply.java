package com.example.android.debtors.EventBus;

/**
 * Created by admin on 22.03.2017.
 */

public class DialogMenuDebtorsMeToOtherApply {
    private int min;
    private int max = 0;

    public DialogMenuDebtorsMeToOtherApply(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
