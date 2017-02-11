package com.example.android.debtors.Model;

/**
 * Created by Rafaello on 2017-02-09.
 */

public class TransactionForClient extends Transaction {

    private Client transactionSeller;
    private Client transactionBouyer;
    private Product transactionProduct; //co
    int productValue;

    public TransactionForClient(String transactionDate, int transactionQuantity) {
        super(transactionDate, transactionQuantity);
    }

    public TransactionForClient(String transactionDate, int transactionQuantity, Product transactionProduct) {
        super(transactionDate, transactionQuantity);
        this.transactionProduct = transactionProduct;
    }

    public TransactionForClient(String transactionDate,
                                Client transactionSeller,
                                Client transactionBouyer,
                                int transactionQuantity,
                                int productValue) {

        super(transactionDate, transactionQuantity);
        this.transactionSeller = transactionSeller;
        this.transactionBouyer = transactionBouyer;
        this.productValue = productValue;
    }

    public TransactionForClient(String transactionDate, int transactionQuantity, int
            productValue) {
        super(transactionDate, transactionQuantity);
        this.productValue = productValue;
    }

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


    //TODO usunac\/ , a value musi należeć do transactionProduct
    public int getProductValue() {
        return productValue;
    }

    public void setProductValue(int productValue) {
        this.productValue = productValue;
    }
}
