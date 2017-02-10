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

    public Client() {
    }

    public Client(String clientName, int clientLeftAmount) {
        this.clientName = clientName;
        this.clientLeftAmount = clientLeftAmount;
    }

    public void payForClient(Payment payment){
        if(this.equals(payment.getPaymentClient()))
            Log.e(TAG, "changeLeftAmount: nie mozesz placic samemu sobie");

        listOfPayments.add(payment);
        this.clientLeftAmount += payment.getPaymentAmount();
    }

    //TODO zmienic, aby value nalezalo do klasy Product
    public void payForTransaction(TransactionForClient transaction){
        int amount = transaction.getTransactionQuantity();
        int value = transaction.getProductValue();
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getClientLeftAmount() {
        return clientLeftAmount;
    }

    public void setClientLeftAmount(int clientLeftAmount) {
        this.clientLeftAmount = clientLeftAmount;
    }

    public void addClientLeftAmount( int amount){
        this.clientLeftAmount += amount;
    }

    public long getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(long clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public List<TransactionForClient> getListOfTransaction() {
        return listOfTransaction;
    }

    public void setListOfTransaction(List<TransactionForClient> listOfTransaction) {
        this.listOfTransaction = listOfTransaction;
    }

    public List<Payment> getListOfPayments() {
        return listOfPayments;
    }

    public void setListOfPayments(List<Payment> listOfPayments) {
        this.listOfPayments = listOfPayments;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientLeftAmount=" + clientLeftAmount +
                '}';
    }
}
