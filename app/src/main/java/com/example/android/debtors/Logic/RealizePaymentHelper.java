package com.example.android.debtors.Logic;

import android.util.Log;

import com.example.android.debtors.Model.Client;
import com.example.android.debtors.Model.Owner;
import com.example.android.debtors.Model.Payment;

/**
 * Created by Rafaello on 2017-02-11.
 */

public class RealizePaymentHelper {

    private static final String TAG = RealizePaymentHelper.class.getSimpleName();

    public RealizePaymentHelper() {
    }

    public void realizePayment(Payment payment){
        if(payment==null){
            Log.e(TAG, "realizePayment: PAYMENT IS NULL");
        } else {
            Owner owner = payment.getPaymentOwner();
            Client client = payment.getPaymentClient();

            int paidAmount = payment.getPaymentAmount();

            boolean revenueOrExpense = payment.isPaymentGotOrGiven();

            changeOwnerAmount(owner, paidAmount, revenueOrExpense);
            addPaymentToOwnerList(owner, payment);

            changeClientAmount(client, paidAmount, revenueOrExpense);
            addPaymentToClientList(client, payment);
        }
    }




    private void changeOwnerAmount(Owner owner, int paidAmount, boolean revenueOrExpense) {
        if(revenueOrExpense)//jesli true = revenue
            owner.changeOwnerAmountWhenPayments(paidAmount);
        else
            owner.changeOwnerAmountWhenPayments(paidAmount*(-1));
    }

    private void addPaymentToOwnerList(Owner owner, Payment payment) {
        owner.addPaymentToList(payment);
    }

    private void changeClientAmount(Client client, int paidAmount, boolean revenueOrExpense) {
        if(revenueOrExpense) // owner dostaje od clienta, wiec client ma mniejszy dlug
            client.changeClientLeftAmount(paidAmount*(-1));
        else
            client.changeClientLeftAmount(paidAmount);

    }

    private void addPaymentToClientList(Client client, Payment payment) {
        client.addPaymentToListOfPayments(payment);
    }
}
