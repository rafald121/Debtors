package com.example.android.debtors.Model;

/**
 * Created by Rafaello on 2017-02-09.
 */

public class TransactionForProduct extends Transaction {


    private Client transactionClient;  //kto

    public TransactionForProduct(String transactionDate, int transactionQuantity) {
        super(transactionDate, transactionQuantity);
    }

    public TransactionForProduct(String transactionDate, int transactionQuantity, Client transactionClient) {
        super(transactionDate, transactionQuantity);
        this.transactionClient = transactionClient;
    }
}
