package com.example.android.twoactivities;

import android.net.Uri;

public class IncomeList {
    public String Kind;
    public String Price;
    public String Account;

    public IncomeList(String kind,String price,String account) {
        this.Kind = kind;
        this.Price = price;
        this.Account = account;
    }
}
