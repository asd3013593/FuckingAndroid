package com.example.android.twoactivities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private int position;
    private TextView title;
    private EditText price;
    private EditText remark;
    private Spinner EditKind;
    private Spinner accountKind;
    private int color;
    private String[] kind;
    private String[] account;
    private ArrayList<String> data = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        //Log.d("asd",Data.get(0).get(0));
        position = intent.getIntExtra("position",0);
        color = intent.getIntExtra("color",0);
        title = (TextView) findViewById(R.id.textView);
        price = findViewById(R.id.editText_money);
        EditKind = (Spinner)findViewById(R.id.EditKind);
        accountKind = (Spinner)findViewById(R.id.accountKind);
        remark = findViewById(R.id.remark);
        if(color == 0){
            ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
                    this, R.array.costedarray, android.R.layout.simple_spinner_dropdown_item );
            EditKind.setAdapter(nAdapter);
            title.setText(getResources().getString(R.string.CostTitle));
            kind = getResources().getStringArray(R.array.costedarray);
        }
        else{
            ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
                    this, R.array.incomedarray, android.R.layout.simple_spinner_dropdown_item );
             EditKind.setAdapter(nAdapter);
             title.setText(getResources().getString(R.string.IncomeTitle));
             kind = getResources().getStringArray(R.array.incomedarray);
        }
        account = getResources().getStringArray(R.array.accountArray);
        Log.d("position",Integer.toString(position));
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
    public void EditData(View view) {
        String Account = account[accountKind.getSelectedItemPosition()];
        String Kind = kind[EditKind.getSelectedItemPosition()];
        String Price = price.getText().toString();
        String Remark = remark.getText().toString();
        if(isNumeric(Price) == 0) {
            data.add(Kind);
            data.add(Price);
            data.add(Account);
            data.add(Remark);
            Intent intent = new Intent();
            intent.setClass(this,MainActivity.class);
            intent.putExtra("position",position);
            intent.putExtra("data",data);
            setResult(RESULT_OK,intent);
            EditActivity.this.finish();
        }
        else if (isNumeric(Price) == 1)
            Toast.makeText(this,"請輸入金額",Toast.LENGTH_LONG).show();
        else if (isNumeric(Price) == 2)
            Toast.makeText(this,"請輸入正確金額",Toast.LENGTH_LONG).show();
    }

}
