package com.example.android.twoactivities;

import android.widget.TextView;

import java.util.ArrayList;

public class Holder {
    TextView text1,text2;
    ArrayList<String> money = new ArrayList<String>();
    ArrayList<String> kind = new ArrayList<String>();
    int num;

    public ArrayList<String> getMoney() {
        return money;
    }

    public ArrayList<String> getKind() {
        return kind;
    }
}
