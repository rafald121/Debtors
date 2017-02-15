package com.example.android.debtors.Model;

/**
 * Created by Rafaello on 2017-02-09.
 */
public class Transaction {


    private String transactionDate; //kiedy
    private int transactionQuantity; //ile

    public Transaction(String transactionDate, int transactionQuantity) {
        this.transactionDate = transactionDate;
        this.transactionQuantity = transactionQuantity;
    }

    public Transaction() {

    }


    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getTransactionQuantity() {
        return transactionQuantity;
    }

    public void setTransactionQuantity(int transactionQuantity) {
        this.transactionQuantity = transactionQuantity;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionDate='" + transactionDate + '\'' +
                ", transactionQuantity=" + transactionQuantity +
                '}';
    }
}
