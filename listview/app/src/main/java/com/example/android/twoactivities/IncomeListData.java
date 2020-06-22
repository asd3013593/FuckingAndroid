package com.example.android.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class IncomeListData implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    static ArrayList<IncomeList> array = new ArrayList<IncomeList>();
    public ArrayList<IncomeList>getArray() {return array;}
    public void SaveData(String kind,String price,String account) {
        array.add(new IncomeList(kind,price,account));
    }
    public IncomeListData ReturnThis() {
        return this;
    }
}
