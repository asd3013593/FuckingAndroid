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

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE
            = "com.example.android.twoactivities.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;
    private EditText mMessageEditText;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;
    private Button schedule,costList;
    private TextView Datalist;
    private CostListData costListData;
    private IncomeListData incomeListData;
    private SharedPreferences mPreferences;
    String Price,Kind,Data,costdata,incomedata;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all the view variables.
        //mReplyHeadTextView = findViewById(R.id.text_header_reply);

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        mReplyTextView = findViewById(R.id.text_message_reply);
        schedule = findViewById(R.id.button2);
        costList = findViewById(R.id.button5);
        Datalist = findViewById(R.id.datalist);
        CostListData costListData = new CostListData();
        IncomeListData incomeListData = new IncomeListData();
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
        if (savedInstanceState != null) {
            costListData = savedInstanceState.getSerializable("COUNT_KEY");
            if (mCount != 0) {
                mShowCountTextView.setText(String.format("%s", mCount));
            }
            mColor = savedInstanceState.getInt(COLOR_KEY);
            mShowCountTextView.setBackgroundColor(mColor);
        }
    }
    @Override
    protected void onPause(){
        super.onPause();

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.put(COUNT_KEY, mCount);
        preferencesEditor.putInt(COLOR_KEY, mColor);
        preferencesEditor.apply();
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
        Data = "";
        if(requestCode == 123) {
            costListData = (CostListData) data.getSerializableExtra("CostListData");
        }
        if(requestCode == 456) {
            incomeListData = (IncomeListData) data.getSerializableExtra("IncomeListData");
        }
        for(int i =0 ;i <costListData.getArray().size();i++) {
            int j = Integer.parseInt(costListData.getArray().get(i).Price);
            String s = String.valueOf(j);
            Data += costListData.getArray().get(i).Kind+ "  -$"+ j + "\n";
            Datalist.setTextColor(Color.rgb(255, 0, 0));
        }
        for(int i =0 ;i <incomeListData.getArray().size();i++) {
            int j = Integer.parseInt(incomeListData.getArray().get(i).Price);
            String s = String.valueOf(j);
            Data += incomeListData.getArray().get(i).Kind+ "  +$"+ j + "\n";
            Datalist.setTextColor(Color.rgb(0, 0, 255));
        }
        Datalist.setText(Data);
    }
}