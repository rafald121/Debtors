package com.example.android.debtors.Model;

/**
 * Created by Rafaello on 2017-02-09.
 */
public class Payment {

    private String paymentData; // kiedy zaplacono
    private Client paymentClient; // kto płaci
    private int paymentAmount; // ile zapłacono

    public Payment(String paymentData, Client paymentClient, int paymentAmount) {
        this.paymentData = paymentData;
        this.paymentClient = paymentClient;
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(String paymentData) {
        this.paymentData = paymentData;
    }

    public Client getPaymentClient() {
        return paymentClient;
    }

    public void setPaymentClient(Client paymentClient) {
        this.paymentClient = paymentClient;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentData='" + paymentData + '\'' +
                ", paymentClient=" + paymentClient +
                ", paymentAmount=" + paymentAmount +
                '}';
    }
}
