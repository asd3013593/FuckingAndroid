package com.example.android.twoactivities;

import android.net.Uri;

public class CostList {
    public String Kind;
    public String Price;
    public String Account;
    public String Remark;
    public CostList(String kind,String price, String account, String remark) {
        this.Kind = kind;
        this.Price = price;
        this.Account = account;
        this.Remark = remark;
    }
}
