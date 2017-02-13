package com.example.android.debtors.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-11.
 */

public class Owner {
    private String ownerName, ownerSurname;
    private int ownerTotalAmount;
    private int ownerOwnAmount;

    private List<Client> listOfClients = new ArrayList<>();
    private List<TransactionForClient> listOfTransaction = new ArrayList<>(); //optional
    private List<Payment> listOfPayments = new ArrayList<>();

    public Owner() {
    }

    public Owner(String ownerName, String ownerSurname, int ownerAmount) {
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.ownerTotalAmount = ownerAmount;
        this.ownerOwnAmount = ownerAmount/2;

    }

//   LOGIC METHODS

    public void changeOwnerAmountWhenTransaction(int amount){
        this.ownerTotalAmount +=amount;
    }

    public void changeOwnerAmountWhenPayments(int amount){
        this.ownerOwnAmount +=amount;
    }

//    GETTERS AND SETTERS

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public void setOwnerSurname(String ownerSurname) {
        this.ownerSurname = ownerSurname;
    }

    public int getOwnerTotalAmount() {
        return ownerTotalAmount;
    }

    public void setOwnerTotalAmount(int ownerTotalAmount) {
        this.ownerTotalAmount = ownerTotalAmount;
    }

    public int getOwnerOwnAmount() {
        return ownerOwnAmount;
    }

    public void setOwnerOwnAmount(int ownerOwnAmount) {
        this.ownerOwnAmount = ownerOwnAmount;
    }

    //      LISTS

//    ADD TRANSACTION TO LIST
    public void addTransactionToList(TransactionForClient transactionForClient){
        this.listOfTransaction.add(transactionForClient);
    }

//    ADD PAYMENT TO LIST
    public void addPaymentToList(Payment payment){
        this.listOfPayments.add(payment);
    }

    public List<Client> getListOfClients() {
        return listOfClients;
    }

    public void setListOfClients(List<Client> listOfClients) {
        this.listOfClients = listOfClients;
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


//    OTHERS

    @Override
    public String toString() {
        return "Owner{" +
                "ownerName='" + ownerName + '\'' +
                ", ownerSurname='" + ownerSurname + '\'' +
                ", ownerTotalAmount=" + ownerTotalAmount +
                ", ownerOwnAmount=" + ownerOwnAmount +
                '}';
    }
}

