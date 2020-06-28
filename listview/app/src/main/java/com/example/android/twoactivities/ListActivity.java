package com.example.android.twoactivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ListView mListView;
    private ArrayList<ArrayList<String>> Data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Data = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("data");
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(new ListActivity.MyAdapter());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Data.size();
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
            holder.text1.setText(Data.get(position).get(2));
            holder.text3.setText(Data.get(position).get(4));
            holder.text4.setText(Data.get(position).get(5));
            holder.text5.setText(Data.get(position).get(0));
            if (Data.get(position).get(1).equals("cost")) {
                holder.text1.setTextColor(Color.RED);
                holder.text2.setText("-$" + Data.get(position).get(3));
                holder.text2.setTextColor(Color.RED);
            } else if (Data.get(position).get(1).equals("income")) {
                holder.text1.setTextColor(Color.BLUE);
                holder.text2.setText("+$" + Data.get(position).get(3));
                holder.text2.setTextColor(Color.BLUE);
            }
            return v;
        }
    }
}
