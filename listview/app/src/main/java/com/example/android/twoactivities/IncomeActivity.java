package com.example.android.twoactivities;

        import android.content.Intent;
        import android.media.TimedText;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.Toast;

public class IncomeActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.android.twoactivities.extra.REPLY";
    private EditText price;
    private EditText remark;
    private Spinner incomeKind;
    private Spinner accountKind;
    private IncomeListData incomeListData = new IncomeListData();
    private String[] kind;
    private String[] account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        price = findViewById(R.id.editText_money);
        incomeKind = (Spinner)findViewById(R.id.incomedKind);
        accountKind = (Spinner)findViewById(R.id.accountKind);
        kind = getResources().getStringArray(R.array.incomedarray);
        remark = findViewById(R.id.remark);
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
    public void SaveIncomeData(View view) {
        String Kind = kind[incomeKind.getSelectedItemPosition()];
        String Price = price.getText().toString();
        String Account = account[accountKind.getSelectedItemPosition()];
        String Remark = remark.getText().toString();
        if(isNumeric(Price) == 0) {
            incomeListData.SaveData(Kind, Price, Account,Remark);
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            intent.putExtra("IncomeListData",incomeListData);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("IncomeListData", incomeListData);
//            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            IncomeActivity.this.finish();
        }
        else if (isNumeric(Price) == 1)
            Toast.makeText(this,"請輸入金額",Toast.LENGTH_LONG).show();
        else if (isNumeric(Price) == 2)
            Toast.makeText(this,"請輸入正確金額",Toast.LENGTH_LONG).show();
        else if (isNumeric(Price) == 3)
            Toast.makeText(this,"首位數字不能為0",Toast.LENGTH_LONG).show();
    }
    public static int isNumeric(String str){
        if(str.length() == 0 )
            return 1;
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return 2;
            }
        }
        if(str.charAt(0) == '0')
            return 3;
        return 0;
    }
}