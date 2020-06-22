/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    // Class name for Log tag
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // Unique tag required for the intent extra
    public static final String EXTRA_MESSAGE
            = "com.example.android.twoactivities.extra.MESSAGE";
    // Unique tag for the intent reply
    public static final int TEXT_REQUEST = 1;

    // EditText view for the message
    private EditText mMessageEditText;
    // TextView for the reply header
    private TextView mReplyHeadTextView;
    // TextView for the reply body
    private TextView mReplyTextView,moneytextview;
    private ListView mListView;
    private Button schedule, costList;
    //    private TextView Datalist;
    String Price, Kind;  //,Data,costdata,incomedata
    private MyAdapter adapter;
    int totalmoney,incomenum,costnum = 0;
    ArrayList<String> kindarray = new ArrayList<String>();
    ArrayList<String> moneyarray = new ArrayList<String>();
    ArrayList<Integer> color = new ArrayList<Integer>(); //0 == red,1 == blue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(new MyAdapter());


        // Initialize all the view variables.
        //mReplyHeadTextView = findViewById(R.id.text_header_reply);
        mReplyTextView = findViewById(R.id.text_message_reply);
        moneytextview = findViewById(R.id.Moneytextview);
        schedule = findViewById(R.id.button2);
        costList = findViewById(R.id.button5);
//      Datalist = findViewById(R.id.datalist);
        schedule.setSelected(true);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedule.setSelected(true);
                costList.setSelected(false);
            }
        });
        costList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedule.setSelected(false);
                costList.setSelected(true);
            }
        });
    }

    public void launchExpendActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, CostActivity.class);
        startActivityForResult(intent, 123);
    }

    public void launchIncomeActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, IncomeActivity.class);
        startActivityForResult(intent, 456);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Data = "";



        CostListData costListData = new CostListData();
        IncomeListData incomeListData = new IncomeListData();
        if (requestCode == 123) {
            costListData = (CostListData) data.getSerializableExtra("CostListData");
        }
        if (requestCode == 456) {
            incomeListData = (IncomeListData) data.getSerializableExtra("IncomeListData");
        }

        String s1 = String.valueOf(costListData.getArray().size());
        String s2 = String.valueOf(incomeListData.getArray().size());
        mReplyTextView.setText(s2+","+s1);

        if(costListData.getArray().size() != costnum){
            int j = Integer.parseInt(costListData.getArray().get(costListData.getArray().size()-1).Price);
            String s = String.valueOf(j);
            kindarray.add(costListData.getArray().get(costListData.getArray().size()-1).Kind);
            moneyarray.add(s);
            color.add(0);
            totalmoney -=j;
            costnum += 1;
        }
        else if(incomeListData.getArray().size() != incomenum){
            int j = Integer.parseInt(incomeListData.getArray().get(incomeListData.getArray().size()-1).Price);
            String s = String.valueOf(j);
            kindarray.add(incomeListData.getArray().get(incomeListData.getArray().size()-1).Kind);
            moneyarray.add(s);
            color.add(1);
            totalmoney +=j;
            incomenum += 1;
        }
        String s3 = String.valueOf(totalmoney);
        moneytextview.setText(s3);
        mListView.setAdapter(new MyAdapter());

    }

        public class MyAdapter extends BaseAdapter {
            ArrayList<String> kindlist = kindarray;
            ArrayList<String> moneylist = moneyarray;
            ArrayList<Integer> colorlist = color;

            @Override
            public int getCount() {
                return kindlist.size();
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
                int i = 0;
                if (v == null) {
                    v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item, null);
                    holder = new Holder();
                    holder.text1 = (TextView) v.findViewById(R.id.text1);
                    holder.text2 = (TextView) v.findViewById(R.id.text2);

                    v.setTag(holder);
                } else {
                    holder = (Holder) v.getTag();
                }

                holder.text1.setText(kindlist.get(position));
                if(colorlist.get(position) == 0){
                    holder.text1.setTextColor(Color.RED);
                    holder.text2.setText("                               -$" + moneylist.get(position));
                    holder.text2.setTextColor(Color.RED);
                }
                else if(colorlist.get(position) == 1){
                    holder.text1.setTextColor(Color.BLUE);
                    holder.text2.setText("                              +$" + moneylist.get(position));
                    holder.text2.setTextColor(Color.BLUE);
                }
//                switch (position) {
//                    case 0:
//                        holder.text1.setText(kindlist.get(0));
//                        holder.text1.setTextColor(Color.rgb(255, 0, 0));
//                        break;
//                    case 1:
//                        holder.text1.setText("monkey");
//                        holder.text1.setTextColor(Color.rgb(0, 0, 255));
//                        break;
//                }
                return v;
            }
    }
}