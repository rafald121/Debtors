package com.example.android.debtors.Model;

/**
 * Created by Rafaello on 2017-02-09.
 */

public class TransactionForClient extends Transaction {

    private Owner transactionOwner;
    private Client transactionClient;
    private Product transactionProduct; //co
    int productValue;
    int transactionEntryPayment = 0;
    boolean transactionSeller; // if true = sprzedawca = owner, if false = sprzedawca = ktos inny

// TRANSACTION CONSTRUCTOR WITHOUT PAYMENT
    public TransactionForClient(String transactionDate, Owner transactionOwner, Client transactionClient, int transactionQuantity, int productValue, boolean transactionSeller) {
        super(transactionDate, transactionQuantity);
        this.transactionOwner = transactionOwner;
        this.transactionClient = transactionClient;
        this.productValue = productValue;
        this.transactionSeller = transactionSeller;
    }

// TRANSACTION CONSTRUCTOR WITH PAYMENT
public TransactionForClient(String transactionDate, Owner transactionOwner, Client transactionClient, int transactionQuantity, int productValue, int entryPayment,  boolean transactionSeller) {

    super(transactionDate, transactionQuantity);
    this.transactionOwner = transactionOwner;
    this.transactionClient = transactionClient;
    this.productValue = productValue;
    this.transactionEntryPayment = entryPayment;
    this.transactionSeller = transactionSeller;
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

    //    GET SET TRANSACTION SELLER

    public boolean isTransactionSeller() {
        return transactionSeller;
    }

    public void setTransactionSeller(boolean transactionSeller) {
        this.transactionSeller = transactionSeller;
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
        String revenueOrExpense = "";
        if(this.transactionSeller)
            revenueOrExpense = "revenue";
        else
            revenueOrExpense = "expense";
        return "TransactionForClient{" +
                "transactionOwner=" + transactionOwner.getOwnerName() +
                ", transactionClient=" + transactionClient.getClientName() +
                ", transactionQuantity=" + super.getTransactionQuantity() +
                ", transactionEntryPayment=" + this.getTransactionEntryPayment() +
                ", transactionProductValue=" + this.getProductValue() +
                ", revenue or expense? " + revenueOrExpense;

    }
}
