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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
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
    private TextView moneytextview,incometextview,costtextview;
    private Toolbar toolbar;
    private ListView mListView;
    private ImageView imageView;
    private Button schedule, costList;
    private Boolean sceduleDisplay = false;
    private ImageButton imageButton;
    private int totalmoney,income,cost,index,position = 0;
    private String curYear,curMonth,curDay,curDate;
    private ArrayList<Integer> Date ;
    private ArrayList<String> EditArray;
    private ArrayList<String> kindArray ;
    private ArrayList<String> moneyArray;
    private ArrayList<String> accountArray;
    private ArrayList<String> remarkArray;
    private ArrayList<String> dateArray;
    private ArrayList<Integer> colorArray; //0 == red,1 == blue;
    private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadData();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = (ImageView) findViewById(R.id.imageView);
        //imageView.setImageResource(R.drawable.ic_launcher_background);
                Calendar calendar = Calendar.getInstance();
        curDate = Integer.toString(calendar.get(Calendar.YEAR))+Integer.toString(calendar.get(Calendar.MONTH)+1)
                + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        SetIndex();
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                curYear = String.valueOf(year);
                curMonth = String.valueOf(month + 1);
                curDay = String.valueOf(dayOfMonth);
                curDate = curYear + curMonth + curDay;
                mListView = (ListView) findViewById(R.id.list);
                SetIndex();
                mListView.setAdapter(new MyAdapter());
            }
        });
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(new MyAdapter());
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
        costList.setSelected(false);
        costList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedule.setSelected(false);
                costList.setSelected(true);
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                intent.putStringArrayListExtra("kindArray",kindArray);
                intent.putStringArrayListExtra("moneyArray",moneyArray);
                intent.putStringArrayListExtra("accountArray",accountArray);
                intent.putStringArrayListExtra("remarkArray",remarkArray);
                intent.putStringArrayListExtra("dateArray",dateArray);
                intent.putIntegerArrayListExtra("colorArray",colorArray);
                startActivity(intent);
            }
        });
        for(int i=0;i<moneyArray.size();i++){
            if(colorArray.get(i) == 0) {
                cost -= Integer.parseInt(moneyArray.get(i));
            }
            else{
                income += Integer.parseInt(moneyArray.get(i));
            }
        }
        String Cost = String.valueOf(cost);
        String Income = String.valueOf(income);
        String s3 = String.valueOf(totalmoney);
        moneytextview.setText(" 收入:" + Income + "  支出:" + Cost+"  淨資產:" + s3);
    }
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(contextMenu, view, menuInfo);
        contextMenu.add(0, 0, 0, "編輯");
        contextMenu.add(0, 1, 0, "刪除");
    }
    public void  SaveData(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("kind",MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("money",MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getSharedPreferences("color",MODE_PRIVATE);
        SharedPreferences sharedPreferences3 = getSharedPreferences("account",MODE_PRIVATE);
        SharedPreferences sharedPreferences4 = getSharedPreferences("remark",MODE_PRIVATE);
        SharedPreferences sharedPreferences5 = getSharedPreferences("total",MODE_PRIVATE);
        SharedPreferences sharedPreferences6 = getSharedPreferences("date",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor moneyEditor = sharedPreferences1.edit();
        SharedPreferences.Editor colorEditor = sharedPreferences2.edit();
        SharedPreferences.Editor accountEditor = sharedPreferences3.edit();
        SharedPreferences.Editor remarkEditor = sharedPreferences4.edit();
        SharedPreferences.Editor totalEditor = sharedPreferences5.edit();
        SharedPreferences.Editor dateEditor = sharedPreferences6.edit();
        String json = gson.toJson(kindArray);
        String moneyJson = gson.toJson(moneyArray);
        String colorJson = gson.toJson(colorArray);
        String accountJson = gson.toJson(accountArray);
        String remarkJson = gson.toJson(remarkArray);
        String dateJson = gson.toJson(dateArray);
        editor.putString("kind",json);
        moneyEditor.putString("money",moneyJson);
        colorEditor.putString("color",colorJson);
        accountEditor.putString("account",accountJson);
        remarkEditor.putString("remark",remarkJson);
        totalEditor.putInt("total",totalmoney);
        dateEditor.putString("date",dateJson);
        editor.apply();
        moneyEditor.apply();
        colorEditor.apply();
        accountEditor.apply();
        remarkEditor.apply();
        totalEditor.commit();
        dateEditor.apply();
    }
    public void  LoadData(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("kind",MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("money",MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getSharedPreferences("color",MODE_PRIVATE);
        SharedPreferences sharedPreferences3 = getSharedPreferences("account",MODE_PRIVATE);
        SharedPreferences sharedPreferences4 = getSharedPreferences("remark",MODE_PRIVATE);
        SharedPreferences sharedPreferences5 = getSharedPreferences("total",MODE_PRIVATE);
        SharedPreferences sharedPreferences6 = getSharedPreferences("date",MODE_PRIVATE);
        String json = sharedPreferences.getString("kind",null);
        String moneyJson = sharedPreferences1.getString("money",null);
        String colorJson = sharedPreferences2.getString("color",null);
        String accountJson = sharedPreferences3.getString("account",null);
        String remarkJson = sharedPreferences4.getString("remark",null);
        String dateJson = sharedPreferences6.getString("date",null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        Type type1 = new TypeToken<ArrayList<String>>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Integer>>(){}.getType();
        Type type3 = new TypeToken<ArrayList<String>>(){}.getType();
        Type type4 = new TypeToken<ArrayList<String>>(){}.getType();
        Type type5 = new TypeToken<ArrayList<String>>(){}.getType();
        kindArray = gson.fromJson(json,type);
        moneyArray = gson.fromJson(moneyJson,type1);
        colorArray = gson.fromJson(colorJson,type2);
        accountArray = gson.fromJson(accountJson,type3);
        remarkArray = gson.fromJson(remarkJson,type4);
        totalmoney = sharedPreferences5.getInt("total",0);
        dateArray = gson.fromJson(dateJson,type5);
        if(kindArray == null){
            kindArray = new ArrayList<String>();
        }
        if(moneyArray == null){
            moneyArray = new ArrayList<String>();
        }
        if(colorArray == null){
            colorArray = new ArrayList<Integer>();
        }
        if(accountArray == null){
            accountArray = new ArrayList<String>();
        }
        if(remarkArray == null){
            remarkArray = new ArrayList<String>();
        }
        if(dateArray == null){
            Log.d("qwe","qwe");
            dateArray = new ArrayList<String>();
        }
    }
    public void  DeleteData(int position){
        int money = Integer.parseInt(moneyArray.get(position));
        if(colorArray.get(position) == 0) totalmoney += money;
        else totalmoney -= money;
        kindArray.remove(position);
        moneyArray.remove(position);
        remarkArray.remove(position);
        dateArray.remove(position);
        accountArray.remove(position);
        colorArray.remove(position);
        Show();
    }
    public void Show(){
        cost = 0;
        income = 0;
        for(int i=0;i<moneyArray.size();i++){
            if(colorArray.get(i) == 0) {
                cost -= Integer.parseInt(moneyArray.get(i));
            }
            else{
                income += Integer.parseInt(moneyArray.get(i));
            }
        }
        SetIndex();
        SaveData();
        String Cost = String.valueOf(cost);
        String Income = String.valueOf(income);
        String s3 = String.valueOf(totalmoney);
        mListView.setAdapter(new MyAdapter());
        moneytextview.setText(" 收入:" + Income + "  支出:" + Cost+"  淨資產:" + s3);
    }
    public void launchExpendActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, CostActivity.class);
        startActivityForResult(intent, 123);
    }
    public void ShowListPage(View view){
        Intent intent = new Intent(this,ListActivity.class);
        intent.putStringArrayListExtra("kindArray",kindArray);
        intent.putStringArrayListExtra("moneyArray",moneyArray);
        intent.putStringArrayListExtra("accountArray",accountArray);
        intent.putStringArrayListExtra("remarkArray",remarkArray);
        intent.putStringArrayListExtra("dateArray",dateArray);
        intent.putIntegerArrayListExtra("colorArray",colorArray);
        startActivity(intent);
    }
    public void setSchedule (View view){
        sceduleDisplay = true;
        mListView.setAdapter(new MyAdapter());
    }
    public void setScheduleList(View view){
        sceduleDisplay = false;
        mListView.setAdapter(new MyAdapter());
    }
    public void launchIncomeActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, IncomeActivity.class);
        startActivityForResult(intent, 456);
    }
    public void SetIndex(){
        index = 0;
        Date = new ArrayList<Integer>();
        for (int i = 0; i < kindArray.size(); i++) {
            if (dateArray.get(i).equals(curDate)) {
                index += 1;
                Date.add((i));
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CostListData costListData = new CostListData();
        EditArray = new ArrayList<String>();
        IncomeListData incomeListData = new IncomeListData();
        if (requestCode == 123) {
            costListData = (CostListData) data.getSerializableExtra("CostListData");
        }
        if (requestCode == 456) {
            incomeListData = (IncomeListData) data.getSerializableExtra("IncomeListData");
        }
        if(requestCode == 789){
            position = data.getIntExtra("position",0);
            EditArray = data.getStringArrayListExtra("data");
        }
        if(costListData.getArray().size() != 0){
            String dater = curDate;
            int j = Integer.parseInt(costListData.getArray().get(costListData.getArray().size()-1).Price);
            String s = String.valueOf(j);
            kindArray.add(costListData.getArray().get(costListData.getArray().size()-1).Kind);
            accountArray.add(costListData.getArray().get(costListData.getArray().size()-1).Account);
            remarkArray.add(costListData.getArray().get(costListData.getArray().size()-1).Remark);
            dateArray.add(dater);
            moneyArray.add(s);
            colorArray.add(0);
            totalmoney -=j;
        }
        else if(incomeListData.getArray().size() != 0){
            int j = Integer.parseInt(incomeListData.getArray().get(incomeListData.getArray().size()-1).Price);
            String s = String.valueOf(j);
            kindArray.add(incomeListData.getArray().get(incomeListData.getArray().size()-1).Kind);
            accountArray.add(incomeListData.getArray().get(incomeListData.getArray().size()-1).Account);
            remarkArray.add(incomeListData.getArray().get(incomeListData.getArray().size()-1).Remark);
            dateArray.add(curDate);
            moneyArray.add(s);
            colorArray.add(1);
            totalmoney +=j;
        }
        else if(EditArray.size() != 0){
            int money = Integer.parseInt(EditArray.get(1)) - Integer.parseInt(moneyArray.get(position));
            kindArray.set(position,EditArray.get(0));
            moneyArray.set(position,EditArray.get(1));
            accountArray.set(position,EditArray.get(2));
            remarkArray.set(position,EditArray.get(3));
            if(colorArray.get(position) == 0) totalmoney -= money;
            else totalmoney += money;
        }
        Show();
    }
    public class MyAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                if(!sceduleDisplay)return index;
                else {
                    return kindArray.size();
                }
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
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = convertView;
                Holder holder;
                if (v == null) {
                    v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item, null);
                    holder = new Holder();
                    holder.text1 = (TextView) v.findViewById(R.id.text1);
                    holder.text2 = (TextView) v.findViewById(R.id.text2);
                    holder.text3 = (TextView) v.findViewById(R.id.text3);
                    holder.text4 = (TextView) v.findViewById(R.id.text4);
                    holder.imageButton1 = (ImageButton) v.findViewById(R.id.imageButton);
                    v.setTag(holder);
                } else {
                    holder = (Holder) v.getTag();
                }
                holder.imageButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String[] action = {"編輯","刪除"};
                        AlertDialog.Builder customizeDialog =
                                new AlertDialog.Builder(MainActivity.this);
                        final View dialogView = LayoutInflater.from(MainActivity.this)
                                .inflate(R.layout.dialog_list,null);
//                        customizeDialog.setView(dialogView);
                        customizeDialog.setItems(action, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                int index = 0;
                                int arrayPosition = 0;
                                Toast.makeText(MainActivity.this, "你選的是" + action[which], Toast.LENGTH_SHORT).show();
                                for(int i = 0; i< dateArray.size();i++)
                                {
                                    if(dateArray.get(i).equals(curDate)) {
                                        if (index == position) {
                                            Log.d("qwe", dateArray.get(i));
                                            break;
                                        }
                                        else index += 1;
                                    }
                                    arrayPosition += 1;
                                }
                                if(action[which].equals("編輯")){
                                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                                    intent.putExtra("position",arrayPosition);
                                    intent.putExtra("color",colorArray.get(arrayPosition));
                                    startActivityForResult(intent,789);
                                }
                                else if(action[which].equals("刪除")) {
                                    DeleteData(arrayPosition);
                                }
                            }
                        });
                        customizeDialog.show();
                    }
                });
                if (!sceduleDisplay) {
                    Log.d("123",Integer.toString(moneyArray.size()));
                    if(moneyArray.size() != 0) {
                        holder.text1.setText(kindArray.get(Date.get(position)));
                        holder.text3.setText(accountArray.get(Date.get(position)));
                        holder.text4.setText(remarkArray.get(Date.get(position)));
                        if (colorArray.get(Date.get(position)) == 0) {
                            holder.text2.setText("-$" + moneyArray.get(Date.get(position)));
                            holder.text2.setTextColor(Color.RED);
                        } else if (colorArray.get(Date.get(position)) == 1) {
                            holder.text2.setText("+$" + moneyArray.get(Date.get(position)));
                            holder.text2.setTextColor(Color.BLUE);
                        }
                    }
              }
                else {
                    holder.text1.setText(kindArray.get(position));
                    holder.text3.setText(accountArray.get(position));
                    holder.text4.setText(remarkArray.get(position));
                    if (colorArray.get(position) == 0) {
                        holder.text2.setText("-$" + moneyArray.get(position));
                        holder.text2.setTextColor(Color.RED);
                    } else if (colorArray.get(position) == 1) {
                        holder.text2.setText("+$" + moneyArray.get(position));
                        holder.text2.setTextColor(Color.BLUE);
                    }
               }
                return v;
            }
        }
    }