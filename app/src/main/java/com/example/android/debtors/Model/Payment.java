package com.example.android.debtors.Model;

/**
 * Created by Rafaello on 2017-02-09.
 */
public class Payment {

    private String paymentData; // kiedy zaplacono
    private Client paymentClient; // kto płaci
    private int paymentAmount; // ile zapłacono
    private boolean paymentGotOrGiven; //if true = got

    public Payment(String paymentData, Client paymentClient, int paymentAmount, boolean paymentGotOrGiven) {
        this.paymentData = paymentData;
        this.paymentClient = paymentClient;
        this.paymentAmount = paymentAmount;
        this.paymentGotOrGiven = paymentGotOrGiven;
    }
//DATA
    public String getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(String paymentData) {
        this.paymentData = paymentData;
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

//    OTHERS

    @Override
    public String toString() {
        return "Payment{" +
                "paymentData='" + paymentData + '\'' +
                ", paymentClient=" + paymentClient +
                ", paymentAmount=" + paymentAmount +
                '}';
    }
}
