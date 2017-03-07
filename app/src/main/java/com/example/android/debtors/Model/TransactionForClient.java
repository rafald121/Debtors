package com.example.android.debtors.Model;

import android.util.Log;

/**
 * Created by Rafaello on 2017-02-09.
 */

public class TransactionForClient extends Transaction {

    private int transactionID;
    private int transactionOwnerID;
    private int transactionClientID;
    private Owner transactionOwner;
    private Client transactionClient;
    private Product transactionProduct; //co
    int productValue;
    int transactionEntryPayment = 0;
    boolean transactionBuyOrSell; // if true = sprzedawca = owner, if false = sprzedawca = ktos inny

// TRANSACTION CONSTRUCTOR WITHOUT PAYMENT
    public TransactionForClient(String transactionDate, Owner transactionOwner, Client transactionClient, int transactionQuantity, int productValue, boolean transactionBuyOrSell) {
        super(transactionDate, transactionQuantity);
        this.transactionOwner = transactionOwner;
        this.transactionClient = transactionClient;
        this.productValue = productValue;
        this.transactionBuyOrSell = transactionBuyOrSell;
    }

    // TRANSACTION CONSTRUCTOR WITH PAYMENT
    public TransactionForClient(String transactionDate, Owner transactionOwner, Client transactionClient, int transactionQuantity, int productValue, int entryPayment,  boolean transactionBuyOrSell) {

        super(transactionDate, transactionQuantity);
        this.transactionOwner = transactionOwner;
        this.transactionClient = transactionClient;
        this.productValue = productValue;
        this.transactionEntryPayment = entryPayment;
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

    public int getProductValue() {
        return productValue;
    }

    public void setProductValue(int productValue) {
        this.productValue = productValue;
    }

    public int getTransactionEntryPayment() {
        return transactionEntryPayment;
    }

    public void setTransactionEntryPayment(int transactionEntryPayment) {
        this.transactionEntryPayment = transactionEntryPayment;
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

    //TRANSACTION OWNER
    public Owner getTransactionOwner() {
        return transactionOwner;
    }

    public void setTransactionOwner(Owner transactionOwner) {
        this.transactionOwner = transactionOwner;
    }
//TRANSACTION CLIENT
    public Client getTransactionClient() {
        return transactionClient;
    }

    public void setTransactionClient(Client transactionClient) {
        this.transactionClient = transactionClient;
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
                ", transactionProductValue=" + this.getProductValue() +
                ", sell or buy? " + buyOrSell;

    }
}
