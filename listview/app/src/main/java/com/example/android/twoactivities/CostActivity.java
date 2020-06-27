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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CostActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.android.twoactivities.extra.REPLY";
    private EditText price;
    private EditText remark;
    private Spinner costKind;
    private Spinner accountKind;
    private CostListData costListData = new CostListData();
    private String[] kind;
    private String[] account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        price = findViewById(R.id.editText_money);
        costKind = (Spinner)findViewById(R.id.EditKind);
        accountKind = (Spinner)findViewById(R.id.accountKind);
        remark = findViewById(R.id.remark);
        kind = getResources().getStringArray(R.array.costedarray);
        account = getResources().getStringArray(R.array.accountArray);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    public static int isNumeric(String str){
        if(str.length() == 0)
            return 1;
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return 2;
            }
        }
        return 0;
    }
    public void SaveCostData(View view) {
        String Account = account[accountKind.getSelectedItemPosition()];
        String Kind = kind[costKind.getSelectedItemPosition()];
        String Price = price.getText().toString();
        String Remark = remark.getText().toString();
        if(isNumeric(Price) == 0) {
            costListData.SaveData(Kind, price.getText().toString(),Account,Remark);
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            intent.putExtra("CostListData", costListData);
            setResult(RESULT_OK, intent);
            CostActivity.this.finish();
        }
        else if (isNumeric(Price) == 1)
           Toast.makeText(this,"請輸入金額",Toast.LENGTH_LONG).show();
        else if (isNumeric(Price) == 2)
            Toast.makeText(this,"請輸入正確金額",Toast.LENGTH_LONG).show();
    }
}
