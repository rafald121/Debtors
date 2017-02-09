package com.example.android.debtors.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-09.
 */

public class Client {

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
