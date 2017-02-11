package com.example.android.debtors.Model;

/**
 * Created by Rafaello on 2017-02-09.
 */

public class TransactionForClient extends Transaction {

    private Owner transactionOwner;
    private Client transactionClient;
    private Product transactionProduct; //co
    int productValue;
    boolean transactionSeller; // if true = sprzedawca = owner, if false = sprzedawca = ktos inny


    public TransactionForClient(String transactionDate, Owner transactionOwner, Client transactionClient, int transactionQuantity, int productValue, boolean transactionSeller) {
        super(transactionDate, transactionQuantity);
        this.transactionOwner = transactionOwner;
        this.transactionClient = transactionClient;
        this.productValue = productValue;
        this.transactionSeller = transactionSeller;
    }

//KONSTRUKTORY SUPER Z KLASY TRANSACTION
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

// KONSTRUKTORY Z TEJ KLASY


    public Owner getTransactionOwner() {
        return transactionOwner;
    }

    public void setTransactionOwner(Owner transactionOwner) {
        this.transactionOwner = transactionOwner;
    }

    public Client getTransactionClient() {
        return transactionClient;
    }

    public void setTransactionClient(Client transactionClient) {
        this.transactionClient = transactionClient;
    }
}
