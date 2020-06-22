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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    // Class name for Log tag
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // Unique tag required for the intent extra
    public static final String EXTRA_MESSAGE
            = "com.example.android.twoactivities.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;
    private EditText mMessageEditText;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView,moneytextview;
    private ListView mListView;
    private Button schedule, costList;
    int totalmoney,incomenum,costnum = 0;
    ArrayList<String> kindArray ;
    ArrayList<String> moneyArray;
    ArrayList<String> accountArray;
    ArrayList<Integer> color ; //0 == red,1 == blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(new MyAdapter());
        mReplyTextView = findViewById(R.id.text_message_reply);
        moneytextview = findViewById(R.id.Moneytextview);
        schedule = findViewById(R.id.button2);
        costList = findViewById(R.id.button5);
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
    public void SaveData(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("kind",MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("money",MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getSharedPreferences("color",MODE_PRIVATE);
        SharedPreferences sharedPreferences3 = getSharedPreferences("account",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor moneyEditor = sharedPreferences1.edit();
        SharedPreferences.Editor colorEditor = sharedPreferences2.edit();
        SharedPreferences.Editor accountEditor = sharedPreferences3.edit();
        String json = gson.toJson(kindArray);
        String moneyJson = gson.toJson(moneyArray);
        String colorJson = gson.toJson(color);
        String accountJson = gson.toJson(accountArray);
        editor.putString("kind",json);
        moneyEditor.putString("money",moneyJson);
        colorEditor.putString("color",colorJson);
        accountEditor.putString("account",accountJson);
        editor.apply();
        moneyEditor.apply();
        colorEditor.apply();
        accountEditor.apply();
    }
    public void  loadData(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("kind",MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("money",MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getSharedPreferences("color",MODE_PRIVATE);
        SharedPreferences sharedPreferences3 = getSharedPreferences("account",MODE_PRIVATE);
        String json = sharedPreferences.getString("kind",null);
        String moneyJson = sharedPreferences1.getString("money",null);
        String colorJson = sharedPreferences2.getString("color",null);
        String accountJson = sharedPreferences3.getString("account",null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        Type type1 = new TypeToken<ArrayList<String>>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Integer>>(){}.getType();
        Type type3 = new TypeToken<ArrayList<String>>(){}.getType();
        kindArray = gson.fromJson(json,type);
        moneyArray = gson.fromJson(moneyJson,type1);
        color = gson.fromJson(colorJson,type2);
        accountArray = gson.fromJson(accountJson,type3);
        if(kindArray == null){
            kindArray = new ArrayList<String>();
            Log.d("Load", "2323");
        }
        if(moneyArray == null){
            moneyArray = new ArrayList<String>();
            Log.d("Load", "1233");
        }
        if(color == null){
            color = new ArrayList<Integer>();
        }
        if(accountArray == null){
            accountArray = new ArrayList<String>();
        }
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
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
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
            kindArray.add(costListData.getArray().get(costListData.getArray().size()-1).Kind);
            accountArray.add(costListData.getArray().get(costListData.getArray().size()-1).Account);
            moneyArray.add(s);
            color.add(0);
            totalmoney -=j;
            costnum += 1;
            SaveData();
        }
        else if(incomeListData.getArray().size() != incomenum){
            int j = Integer.parseInt(incomeListData.getArray().get(incomeListData.getArray().size()-1).Price);
            String s = String.valueOf(j);
            kindArray.add(incomeListData.getArray().get(incomeListData.getArray().size()-1).Kind);
            accountArray.add(incomeListData.getArray().get(incomeListData.getArray().size()-1).Account);
            moneyArray.add(s);
            color.add(1);
            totalmoney +=j;
            incomenum += 1;
            SaveData();
        }
        String s3 = String.valueOf(totalmoney);
        moneytextview.setText(s3);
        mListView.setAdapter(new MyAdapter());
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
                    v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item, null);
                    holder = new Holder();
                    holder.text1 = (TextView) v.findViewById(R.id.text1);
                    holder.text2 = (TextView) v.findViewById(R.id.text2);
                    holder.text4 = (TextView) v.findViewById(R.id.text4);

                    v.setTag(holder);
                } else {
                    holder = (Holder) v.getTag();
                }
                holder.text1.setText(kindArray.get(position));
                holder.text4.setText(accountArray.get(position));
                if(color.get(position) == 0){
                    holder.text1.setTextColor(Color.RED);
                    holder.text2.setText("-$" + moneyArray.get(position));
                    holder.text2.setTextColor(Color.RED);
                 }
                else if(color.get(position) == 1){
                    holder.text1.setTextColor(Color.BLUE);
                    holder.text2.setText("+$" + moneyArray.get(position));
                    holder.text2.setTextColor(Color.BLUE);
                }
                return v;
            }
        }
    }