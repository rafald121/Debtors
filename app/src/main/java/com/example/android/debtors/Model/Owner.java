package com.example.android.debtors.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-11.
 */

public class Owner {

    private int ownerID;
    private String ownerName;
    private int ownerTotalAmount;
    private int ownerOwnAmount;

    public Owner() {
    }

    public Owner(int ownerOwnAmount, String ownerName, int ownerTotalAmount) {
        this.ownerOwnAmount = ownerOwnAmount;
        this.ownerName = ownerName;
        this.ownerTotalAmount = ownerTotalAmount;
    }

    public Owner(String pawulon, int i) {
        this.ownerName = pawulon;
        this.ownerTotalAmount = i;
        this.ownerOwnAmount = i/2;
    }

    public Owner(String name, int totalAmount, int ownAmount) {
        this.ownerName = name;
        this.ownerTotalAmount = totalAmount;
        this.ownerOwnAmount = ownAmount;
    }

//   LOGIC METHODS

    public void changeOwnerAmountWhenTransaction(int amount){
        this.ownerTotalAmount +=amount;
    }

    public void changeOwnerAmountWhenPayments(int amount){
        this.ownerOwnAmount +=amount;
    }

//    GETTERS AND SETTERS

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getOwnerTotalAmount() {
        return ownerTotalAmount;
    }

    public void setOwnerTotalAmount(int ownerTotalAmount) {
        this.ownerTotalAmount = ownerTotalAmount;
    }

    public int getOwnerOwnAmount() {
        return ownerOwnAmount;
    }

    public void setOwnerOwnAmount(int ownerOwnAmount) {
        this.ownerOwnAmount = ownerOwnAmount;
    }


//    OTHERS

    @Override
    public String toString() {
        return "Owner{" +
                "ownerID=" + ownerID +
                ", ownerName='" + ownerName + '\'' +
                ", ownerTotalAmount=" + ownerTotalAmount +
                ", ownerOwnAmount=" + ownerOwnAmount +
                '}';
    }
}

