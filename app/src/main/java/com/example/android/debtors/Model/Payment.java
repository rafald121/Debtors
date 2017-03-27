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
    private int paymentAmount; // ile zapłacono
    private boolean paymentGotOrGiven; //if true = got
    private String paymentDetails = "";
    public Payment(){

    }

    public Payment(String paymentData, Owner paymentOwner, Client paymentClient, int paymentAmount, boolean paymentGotOrGiven) {
        this.paymentDate = paymentData;
        this.paymentAmount = paymentAmount;
        this.paymentGotOrGiven = paymentGotOrGiven;
    }

    public Payment(String paymentDate, int paymentOwnerID, int paymentClientID, int paymentAmount, String paymentDetails, boolean paymentGotOrGiven) {
        this.paymentDetails = paymentDetails;
        this.paymentDate = paymentDate;
        this.paymentOwnerID = paymentOwnerID;
        this.paymentClientID = paymentClientID;
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

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String toString() {
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
                ", paymentDetails=" + paymentDetails +
                ", revenueOrExpense? " + revenueOrExpense +
                '}';
    }

}
