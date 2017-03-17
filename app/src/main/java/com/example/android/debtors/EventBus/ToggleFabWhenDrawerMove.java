package com.example.android.debtors.EventBus;

/**
 * Created by admin on 16.03.2017.
 */

public class ToggleFabWhenDrawerMove {
    private final boolean direction;

    public ToggleFabWhenDrawerMove(boolean direction) {
        this.direction = direction;
    }

    public boolean isDirection() {
        return direction;
    }
}
