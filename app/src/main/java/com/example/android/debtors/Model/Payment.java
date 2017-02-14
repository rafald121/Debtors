package com.example.android.debtors.Model;

import android.util.Log;

/**
 * Created by Rafaello on 2017-02-09.
 */
public class Payment {

    private int paymentID;
    private String paymentDate; // kiedy zaplacono
    private int paymentOwnerID;
    private int paymentClientID;
    private Owner paymentOwner;
    private Client paymentClient; // kto płaci
    private int paymentAmount; // ile zapłacono
    private boolean paymentGotOrGiven; //if true = got

    public Payment(){

    }

    public Payment(String paymentData, Owner paymentOwner, Client paymentClient, int paymentAmount, boolean paymentGotOrGiven) {
        this.paymentDate = paymentData;
        this.paymentOwner = paymentOwner;
        this.paymentClient = paymentClient;
        this.paymentAmount = paymentAmount;
        this.paymentGotOrGiven = paymentGotOrGiven;
    }

    //ID
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
//CLIENT AND OWNER ID GET SET


    public int getPaymentOwnerID() {
        return paymentOwnerID;
    }

    public void setPaymentOwnerID(int paymentOwnerID) {
        this.paymentOwnerID = paymentOwnerID;
    }

    public int getPaymentClientID() {
        return paymentClientID;
    }

    public void setPaymentClientID(int paymentClientID) {
        this.paymentClientID = paymentClientID;
    }


    //DATA
    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    //    WLASCICIEL KTOREMU SIE PLACI

    public Owner getPaymentOwner() {
        return paymentOwner;
    }

    public void setPaymentOwner(Owner paymentOwner) {
        this.paymentOwner = paymentOwner;
    }

//KLIENT KTOREGO DOTYCZY PLATNOSC


    public Client getPaymentClient() {
        return paymentClient;
    }

    public void setPaymentClient(Client paymentClient) {
        this.paymentClient = paymentClient;
    }
//KWOTA PLATNOSCI
    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

//    CZY DANO CZY WZIĘTO

    public boolean isPaymentGotOrGiven() {
        return paymentGotOrGiven;
    }

    public void setPaymentGotOrGiven(boolean paymentGotOrGiven) {
        this.paymentGotOrGiven = paymentGotOrGiven;
    }
    public void setPaymentGotOrGiven(int paymentGotOrGiven) {
        if(paymentGotOrGiven==1)
            this.paymentGotOrGiven = true;
        else if (paymentGotOrGiven == 0 )
            this.paymentGotOrGiven = false;
        else
            Log.e("Payment", "setPaymentGotOrGiven: PaymentGotOrGiven can't be other than 0 or 1");
    }

//    OTHERS

    @Override
    public String toString() {
        String revenueOrExpense = "";
        if(this.paymentGotOrGiven)
            revenueOrExpense = "revenue";
        else
            revenueOrExpense = "expense";
        return "Payment{" +
                "paymentID=" + paymentID +
                ", paymentData='" + paymentDate + '\'' +
                ", paymentOwner=" + paymentOwner +
                ", paymentClient=" + paymentClient +
                ", paymentAmount=" + paymentAmount +
                ", revenueOrExpense? " + revenueOrExpense +
                '}';
    }
//IF TO STRING WITH FLAGS WE GET INFO ABOUT PAYMENTS WITH CLIENT AND OWNER ID NOT OBJECT
    public String toString(boolean flags) {
        String revenueOrExpense = "";
        if(this.paymentGotOrGiven)
            revenueOrExpense = "revenue";
        else
            revenueOrExpense = "expense";
        return "Payment{" +
                "paymentID=" + paymentID +
                ", paymentData='" + paymentDate + '\'' +
                ", paymentOwnerID=" + paymentOwnerID +
                ", paymentClientID=" + paymentClientID +
                ", paymentAmount=" + paymentAmount +
                ", revenueOrExpense? " + revenueOrExpense +
                '}';
    }

}
