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

/*public class CostListData {
    static int a = 3;
    static String Price;
    static void SaveData(String price)
    {
        Price = price;
    }
    static LinkedList<CostList> costLists;
    static LinkedList<CostList> getCostLists() {return costLists;}
    static int GetA(){return  a;}
    static {
        costLists = new LinkedList<CostList>();
        costLists.add(new CostList(Price));
    }*/
public class CostListData implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    static LinkedList<CostList> array = new LinkedList<CostList>();
    public LinkedList<CostList>getArray() {return array;}
    public void SaveData(String kind,String price)
    {
        array.add(new CostList(kind,price));
    }
    public CostListData ReturnThis() {
        return this;
    }
}
