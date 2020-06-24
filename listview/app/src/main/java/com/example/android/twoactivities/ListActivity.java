package com.example.android.twoactivities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private TextView moneytextview;
    private ListView mListView;
    private ArrayList<String> kindArray ;
    private ArrayList<String> moneyArray;
    private ArrayList<String> accountArray;
    private ArrayList<String> remarkArray;
    private ArrayList<String> dateArray;
    private ArrayList<Integer> colorArray ; //0 == red,1 == blue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        moneyArray = intent.getStringArrayListExtra("moneyArray");
        kindArray = intent.getStringArrayListExtra("kindArray");
        accountArray = intent.getStringArrayListExtra("accountArray");
        remarkArray = intent.getStringArrayListExtra("remarkArray");
        dateArray = intent.getStringArrayListExtra("dateArray");
        colorArray = intent.getIntegerArrayListExtra("colorArray");
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(new ListActivity.MyAdapter());
        Log.d("aaa",kindArray.get(0));
    }
    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return kindArray.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            Holder holder;
            if (v == null) {
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_page, null);
                holder = new Holder();
                holder.text1 = (TextView) v.findViewById(R.id.text1);
                holder.text2 = (TextView) v.findViewById(R.id.text2);
                holder.text3 = (TextView) v.findViewById(R.id.text3);
                holder.text4 = (TextView) v.findViewById(R.id.text4);
                holder.text5 = (TextView) v.findViewById(R.id.text5);
                v.setTag(holder);
            } else {
                holder = (Holder) v.getTag();
            }
            holder.text1.setText(kindArray.get(position));
            holder.text3.setText(accountArray.get(position));
            holder.text4.setText(remarkArray.get(position));
            holder.text5.setText(dateArray.get(position));
            if (colorArray.get(position) == 0) {
                holder.text1.setTextColor(Color.RED);
                holder.text2.setText("-$" + moneyArray.get(position));
                holder.text2.setTextColor(Color.RED);
            } else if (colorArray.get(position) == 1) {
                holder.text1.setTextColor(Color.BLUE);
                holder.text2.setText("+$" + moneyArray.get(position));
                holder.text2.setTextColor(Color.BLUE);
            }
            return v;
        }
    }
}
