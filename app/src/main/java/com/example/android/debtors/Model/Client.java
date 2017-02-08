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






}
