package com.example.android.debtors.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-11.
 */

public class Owner {
    private String ownerName, ownerSurname;
    private int ownerAmount;

    private List<Client> listOfClients = new ArrayList<>();
    private List<TransactionForClient> listOfTransaction = new ArrayList<>(); //optional
    private List<Payment> listOfPayments = new ArrayList<>();

    public Owner(String ownerName, String ownerSurname, int ownerAmount) {
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.ownerAmount = ownerAmount;
    }

//   LOGIC METHODS

    public void changeOwnerAmount(int amount){
        this.ownerAmount +=amount;
    }


    public void acceptTransaction(Transaction transaction){

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

    public int getOwnerAmount() {
        return ownerAmount;
    }

    public void setOwnerAmount(int ownerAmount) {
        this.ownerAmount = ownerAmount;
    }

//      LISTS

    public void addTransactionToList(TransactionForClient transactionForClient){
        this.listOfTransaction.add(transactionForClient);
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
                ", ownerAmount=" + ownerAmount +
                '}';
    }
}

