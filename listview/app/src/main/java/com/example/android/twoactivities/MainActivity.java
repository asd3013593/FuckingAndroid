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
    private TextView moneytextview;
    private Toolbar toolbar;
    private ListView mListView;
    private ImageView imageView;
    private Button schedule, costList;
    private Boolean sceduleDisplay = false;
    private ImageButton imageButton;
    private int income,cost,index,position = 0;
    private String curYear,curMonth,curDay,curDate;
    private ArrayList<ArrayList<String>>Data;
    private ArrayList<Integer> Date ;
    private ArrayList<String> EditArray;
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
        curDate = Integer.toString(calendar.get(Calendar.YEAR)) + "0" +Integer.toString(calendar.get(Calendar.MONTH)+1)
                + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        SetIndex();
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                curYear = String.valueOf(year);
//                curMonth = String.valueOf(month + 1);
//                curDay = String.valueOf(dayOfMonth);
                if( (month+1) < 10) curMonth = "0" + String.valueOf(month + 1);
                else curMonth = String.valueOf(month + 1);
                if( (dayOfMonth+1) < 10) curDay = "0" + String.valueOf(dayOfMonth);
                else curDay = String.valueOf(dayOfMonth);
                curDate = curYear + curMonth + curDay;
                mListView = (ListView) findViewById(R.id.list);
                SetIndex();
                mListView.setAdapter(new MyAdapter());
                Show();
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
        Show();
    }
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(contextMenu, view, menuInfo);
        contextMenu.add(0, 0, 0, "編輯");
        contextMenu.add(0, 1, 0, "刪除");
    }
    public void  SaveData(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences5 = getSharedPreferences("total",MODE_PRIVATE);
        SharedPreferences sharedPreferences7 = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor dataEditor = sharedPreferences7.edit();
        String dataJson = gson.toJson(Data);
        dataEditor.putString("data",dataJson);
        dataEditor.apply();
    }
    public void  LoadData(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences7 = getSharedPreferences("data",MODE_PRIVATE);
        String dataJson = sharedPreferences7.getString("data",null);
        Type dataType = new TypeToken<ArrayList<ArrayList<String>>>(){}.getType();
        Data = gson.fromJson(dataJson,dataType);
        if(Data == null){
            Data = new ArrayList<ArrayList<String>>();
        }
    }
    public void  DeleteData(int position){
        Data.remove(position);
        Show();
    }
    public void Show(){
        cost = 0;
        income = 0;
        int totalmoney = 0;
        for(int i=0;i<Data.size();i++){
            if(Data.get(i).get(0).equals(curDate)) {
                if (Data.get(i).get(1).equals("cost")) {
                    cost -= Integer.parseInt(Data.get(i).get(3));
                } else {
                    income += Integer.parseInt(Data.get(i).get(3));
                }
            }
        }
        totalmoney = cost+income;
        SetIndex();
        SaveData();
        String Cost = String.valueOf(cost);
        String Income = String.valueOf(income);
        String s3 = String.valueOf(totalmoney);
        mListView.setAdapter(new MyAdapter());
        if(cost == 0 && income == 0) moneytextview.setText(" 沒有紀錄，按「收入」或「支出」新增紀錄。");
        else moneytextview.setText(" 收入:" + Income + "  支出:" + Cost+"  淨資產:" + s3);
    }
    public void launchExpendActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, CostActivity.class);
        startActivityForResult(intent, 123);
    }
    public void ShowListPage(View view){
        costList.setSelected(true);
        schedule.setSelected(false);
        Intent intent = new Intent(this,ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",Data);
        intent.putExtras(bundle);
        startActivity(intent);
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
        for (int i =0 ; i<Data.size();i++){
            if((Data.get(i).get(0)).equals(curDate)){
                Log.d("data",(Data.get(i).get(0)));
                index += 1;
                Date.add(i);
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EditArray = new ArrayList<String>();
        ArrayList<String> storge = new ArrayList<String>();
        CostListData costListData = new CostListData();
        IncomeListData incomeListData = new IncomeListData();
        if (requestCode == 123) {
            costListData = (CostListData) data.getSerializableExtra("CostListData");
            String dater = curDate;
            int j = Integer.parseInt(costListData.getArray().get(costListData.getArray().size()-1).Price);
            String s = String.valueOf(j);
            storge.add(dater);
            storge.add("cost");
            storge.add(costListData.getArray().get(costListData.getArray().size()-1).Kind);
            storge.add(s);
            storge.add(costListData.getArray().get(costListData.getArray().size()-1).Account);
            storge.add(costListData.getArray().get(costListData.getArray().size()-1).Remark);
            Data.add(storge);
        }
        else if (requestCode == 456) {
            incomeListData = (IncomeListData) data.getSerializableExtra("IncomeListData");
            int j = Integer.parseInt(incomeListData.getArray().get(incomeListData.getArray().size()-1).Price);
            String s = String.valueOf(j);
            storge.add(curDate);
            storge.add("income");
            storge.add(incomeListData.getArray().get(incomeListData.getArray().size()-1).Kind);
            storge.add(s);
            storge.add(incomeListData.getArray().get(incomeListData.getArray().size()-1).Account);
            storge.add(incomeListData.getArray().get(incomeListData.getArray().size()-1).Remark);
            Data.add(storge);
        }
        else if(requestCode == 789){
            position = data.getIntExtra("position",0);
            EditArray = data.getStringArrayListExtra("data");
            int money = Integer.parseInt(EditArray.get(1)) - Integer.parseInt(Data.get(position).get(3));
            Data.get(position).set(2,EditArray.get(0));
            Data.get(position).set(3,EditArray.get(1));
            Data.get(position).set(4,EditArray.get(2));
            Data.get(position).set(5,EditArray.get(3));
        }
        Show();
    }
    public class MyAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                if(!sceduleDisplay)return index;
                else {
                    return Data.size();
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
                        final String[] action = {"編輯", "刪除"};
                        AlertDialog.Builder customizeDialog =
                                new AlertDialog.Builder(MainActivity.this);
                        final View dialogView = LayoutInflater.from(MainActivity.this)
                                .inflate(R.layout.dialog_list, null);
//                        customizeDialog.setView(dialogView);
                        customizeDialog.setItems(action, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                int index = 0;
                                int arrayPosition = 0;
                                Toast.makeText(MainActivity.this, "你選的是" + action[which], Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < Data.size(); i++) {
                                    if (Data.get(i).get(0).equals(curDate)) {
                                        if (index == position) {
                                            break;
                                        } else index += 1;
                                    }
                                    arrayPosition += 1;
                                }
                                if (action[which].equals("編輯")) {
                                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                                    intent.putExtra("position", arrayPosition);
                                    intent.putExtra("color", Data.get(arrayPosition).get(1));
                                    startActivityForResult(intent, 789);
                                } else if (action[which].equals("刪除")) {
                                    DeleteData(arrayPosition);
                                }
                            }
                        });
                        customizeDialog.show();
                    }
                });
                if (!sceduleDisplay) {
                    Log.d("1234", "1234");
                    if (Data.size() != 0) {
                        holder.text1.setText(Data.get(Date.get(position)).get(2));
                        holder.text3.setText(Data.get(Date.get(position)).get(4));
                        holder.text4.setText(Data.get(Date.get(position)).get(5));
                        if (Data.get(Date.get(position)).get(1).equals("cost")) {
                            holder.text2.setText("-$" + Data.get(Date.get(position)).get(3));
                            holder.text2.setTextColor(Color.RED);
                        } else if (Data.get(Date.get(position)).get(1).equals("income")) {
                            holder.text2.setText("+$" + Data.get(Date.get(position)).get(3));
                            holder.text2.setTextColor(Color.BLUE);
                        }
                    }
                }
                return v;
            }
        }
    }