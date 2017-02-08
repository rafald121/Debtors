package com.example.android.debtors.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafaello on 2017-02-09.
 */
public class Product {

    private int productId;
    private String productName;
    private int productValue;
    List<Client> productBoughtBy = new ArrayList<>();
    List<TransactionForProduct> productTransationHistory = new ArrayList<>(); //lista tranzakcji
    // dla danego produktu, która zawiera kiedy, ile, kto kupił




}
