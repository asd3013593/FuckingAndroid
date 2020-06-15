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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The TwoActivities app contains two activities and sends messages
 * (intents) between them.
 */
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
    private TextView mReplyTextView;
    private Button schedule,costList;
    private TextView costData;
    String Price,Kind,Data;
    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all the view variables.
        //mReplyHeadTextView = findViewById(R.id.text_header_reply);
        mReplyTextView = findViewById(R.id.text_message_reply);
        schedule = findViewById(R.id.button2);
        costList = findViewById(R.id.button5);
        costData = findViewById(R.id.CostData);
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
        Intent intent = new Intent(this, ExpendActivity.class);
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
        CostListData costListData = new CostListData();
        IncomeListData incomeListData = new IncomeListData();
        if(requestCode == 123) {
            costListData = (CostListData) data.getSerializableExtra("CostListData");
        }
        if(requestCode == 456) {
            incomeListData = (IncomeListData) data.getSerializableExtra("IncomeListData");
        }
        for(int i =0 ;i <costListData.getArray().size();i++) {
            Data += costListData.getArray().get(i).Kind+ " "+costListData.getArray().get(i).Price + "\n";
        }
        for(int i =0 ;i <incomeListData.getArray().size();i++) {
            Data += incomeListData.getArray().get(i).Kind+ " "+incomeListData.getArray().get(i).Price + "\n";
        }
        costData.setText(Data);
    }
}

