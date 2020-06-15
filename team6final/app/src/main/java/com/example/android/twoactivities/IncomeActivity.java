package com.example.android.twoactivities;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.Toast;

public class IncomeActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.android.twoactivities.extra.REPLY";
    private EditText price;
    private Spinner incomedKind;
    private IncomeListData incomeListData = new IncomeListData();
    private String[] kind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        price = findViewById(R.id.editText_money);
        incomedKind = (Spinner)findViewById(R.id.incomedKind);
        kind = getResources().getStringArray(R.array.incomedarray);
    }

    public void SaveIncomeData(View view) {
        String Kind = kind[incomedKind.getSelectedItemPosition()];
        String Price = price.getText().toString();
        if(isNumeric(Price) == 0) {
            incomeListData.SaveData(Kind, price.getText().toString());
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            intent.putExtra(EXTRA_REPLY, "123");
            Bundle bundle = new Bundle();
            bundle.putSerializable("IncomeListData", incomeListData);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            IncomeActivity.this.finish();
        }
        else if (isNumeric(Price) == 1)
            Toast.makeText(this,"請輸入金額",Toast.LENGTH_LONG).show();
        else if (isNumeric(Price) == 2)
            Toast.makeText(this,"請輸入正確金額",Toast.LENGTH_LONG).show();
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
}