package com.example.android.debtors.Model;

import android.util.Log;

/**
 * Created by Rafaello on 2017-02-09.
 */

public class TransactionForClient extends Transaction {

    private int transactionID;
    private int transactionOwnerID;
    private int transactionClientID;
    private int transactionProductValue;
    private int transactionEntryPayment = 0;



    private String transactionDetails = "";
    boolean transactionBuyOrSell; // if true = sprzedawca = owner, if false = sprzedawca = ktos inny

// TRANSACTION CONSTRUCTOR WITHOUT PAYMENT
    public TransactionForClient(String transactionDate, int transactionQuantity, int transactionProductValue, boolean transactionBuyOrSell) {
        super(transactionDate, transactionQuantity);
        this.transactionProductValue = transactionProductValue;
        this.transactionBuyOrSell = transactionBuyOrSell;
    }

    // TRANSACTION CONSTRUCTOR WITH PAYMENT
    public TransactionForClient(String transactionDate, int transactionQuantity, int transactionProductValue, int entryPayment,  boolean transactionBuyOrSell) {

        super(transactionDate, transactionQuantity);
        this.transactionProductValue = transactionProductValue;
        this.transactionEntryPayment = entryPayment;
        this.transactionBuyOrSell = transactionBuyOrSell;
    }

    public TransactionForClient(String transactionDate, int transactionOwnerID, int transactionClientID, int transactionQuantity, int transactionProductValue, int transactionEntryPayment, String transactionDetails, boolean transactionBuyOrSell) {
        super(transactionDate, transactionQuantity);
        this.transactionOwnerID = transactionOwnerID;
        this.transactionClientID = transactionClientID;
        this.transactionProductValue = transactionProductValue;
        this.transactionEntryPayment = transactionEntryPayment;
        this.transactionDetails = transactionDetails;
        this.transactionBuyOrSell = transactionBuyOrSell;
    }

    public TransactionForClient() {
        super();

    }

    //GETTERY I SETTERY SUPER Z KLASY TRANSACTION
    public String getTransactionDate(){
        return super.getTransactionDate();
    }
    public void setTransactionDate(String transactionDate){
        super.setTransactionDate(transactionDate);
    }

    public int getTransactionQuantity(){
        return super.getTransactionQuantity();
    }
    public void setTransactionQuantity(int transactionQuantity){
        super.setTransactionQuantity(transactionQuantity);
    }

// GETTERY I SETTERY Z TEJ KLASY

//    ID
    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getTransactionOwnerID() {
        return transactionOwnerID;
    }

    public void setTransactionOwnerID(int transactionOwnerID) {
        this.transactionOwnerID = transactionOwnerID;
    }

    public int getTransactionClientID() {
        return transactionClientID;
    }


    public void setTransactionClientID(int transactionClientID) {
        this.transactionClientID = transactionClientID;
    }

    public int getTransactionProductValue() {
        return transactionProductValue;
    }

    public void setTransactionProductValue(int transactionProductValue) {
        this.transactionProductValue = transactionProductValue;
    }

    public int getTransactionEntryPayment() {
        return transactionEntryPayment;
    }

    public void setTransactionEntryPayment(int transactionEntryPayment) {
        this.transactionEntryPayment = transactionEntryPayment;
    }


    public String getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    //    GET SET TRANSACTION BuyOrSell

    public boolean isTransactionBuyOrSell() {
        return transactionBuyOrSell;
    }

    public void setTransactionBuyOrSell(boolean transactionBuyOrSell) {
        this.transactionBuyOrSell = transactionBuyOrSell;
    }

    public void setTransactionBuyOrSell(int transactionBuyOrSell) {
        if(transactionBuyOrSell==1)
            this.transactionBuyOrSell = true;
        else if(transactionBuyOrSell==0)
            this.transactionBuyOrSell = false;
        else
            Log.e("TransactionForClient", "setTransactionBuyOrSell: TRANSACTION BUY OR SELL CAN'T BE OTHER THAN 0 or 1!1!" );
    }

    @Override
    public String toString(){
        String buyOrSell = "";
        if(this.transactionBuyOrSell)
            buyOrSell = "sell";
        else
            buyOrSell = "buy";
        return "TransactionForClient{" +
                "transactionID=" + this.transactionID +
                ", transactionDate=" + super.getTransactionDate() +
                ", transactionOwner=" + this.transactionOwnerID +
                ", transactionClient=" + this.transactionClientID +
                ", transactionQuantity=" + super.getTransactionQuantity() +
                ", transactionEntryPayment=" + this.getTransactionEntryPayment() +
                ", transactiontransactionProductValue=" + this.getTransactionProductValue() +
                ", sell or buy? " + buyOrSell;

    }
}
