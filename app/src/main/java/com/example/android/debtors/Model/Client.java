package com.example.android.debtors.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-09.
 */

public class Client {

    private static final String TAG = Client.class.getSimpleName();

    private int clientId;
    private String clientName;
    private int clientLeftAmount; // pozostała kwota
    private long clientPhoneNumber; //optional

    private List<TransactionForClient> listOfTransaction = new ArrayList<>(); //optional
    private List<Payment> listOfPayments = new ArrayList<>();
//KONSTRUKTORY
    public Client() {
    }

    public Client(String clientName, int clientLeftAmount) {
        this.clientName = clientName;
        this.clientLeftAmount = clientLeftAmount;
    }
//    METODY, KTORE MODYFIKUJĄ DANE KLIENTA
    //PAYMENTS
    public void payForClient(Payment payment){
        if(this.equals(payment.getPaymentClient()))
            Log.e(TAG, "changeLeftAmount: nie mozesz placic samemu sobie");

        listOfPayments.add(payment);
        this.clientLeftAmount += payment.getPaymentAmount();
    }


    //    TRANSACTIONS
    //TODO zmienic, aby value nalezalo do klasy Product
    // akceptowac transakcje powinien
//    public void acceptTransaction(TransactionForClient transaction, Client transactionClient){
//        int amount = transaction.getTransactionQuantity();
//        int value = transaction.getProductValue();
//        int totalValue = amount * value;
//
//        Client seller = transaction.getTransactionSeller();
//        Client buyer = transaction.getTransactionBuyer();
//        //jeśli to ja jestem sprzedającym
//        if(this.equals(transaction.getTransactionSeller())) {
//            Log.i(TAG, "acceptTransaction: transaction.getTransactionSeller() = true");
//            this.changeClientLeftAmount(totalValue);
//        } else{
//            Log.i(TAG, "acceptTransaction: transaction.getTransactionSeller() = false");
//            this.changeClientLeftAmount(totalValue*(-1));
//        }
//
//
//    }

//        CHANGING LEFT AMOUNT FOR CLIENT
    public void changeClientLeftAmount( int amount ){
        this.clientLeftAmount += amount;
    }

    public void changeClientLeftAmount(TransactionForClient transaction){
        int value = transaction.getProductValue();
        int quantity = transaction.getTransactionQuantity();
        int totalValue = value * quantity;



        this.clientLeftAmount += totalValue;
    }



//GETTERS AND SETTERS
//      ID
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
//      NAME
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
//      AMOUNT
    public int getClientLeftAmount() {
        return clientLeftAmount;
    }

    public void setClientLeftAmount(int clientLeftAmount) {
        this.clientLeftAmount = clientLeftAmount;
    }

//      LISTS OF TRANSACTION

    public List<TransactionForClient> getListOfTransaction() {
        return listOfTransaction;
    }

    public void setListOfTransaction(List<TransactionForClient> listOfTransaction) {
        this.listOfTransaction = listOfTransaction;
    }

//    LISTA PLATNOSCI

    public List<Payment> getListOfPayments() {
        return listOfPayments;
    }

    public void setListOfPayments(List<Payment> listOfPayments) {
        this.listOfPayments = listOfPayments;
    }

//    DODAC TRANZAKCJE DO LISTY TRANZAKCJI DLA DANEG CLIENTA
    public void addTransactionToListOfTransaction(TransactionForClient transaction){
        this.listOfTransaction.add(transaction);
    }

//  DODAJ PLATNOSC DO LISTY PLATNOSCI DLA DANEGO KLIENTA

    public void addPaymentToListOfPayments(Payment payment){
        this.listOfPayments.add(payment);
    }

//    OTHERS
    @Override
    public String toString(){
        return this.clientName;
    }

    public String toString(boolean halo) {
        return "Client{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientLeftAmount=" + clientLeftAmount +
                '}';
    }
}
