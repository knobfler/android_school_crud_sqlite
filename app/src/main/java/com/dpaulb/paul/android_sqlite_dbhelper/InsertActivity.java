package com.dpaulb.paul.android_sqlite_dbhelper;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertActivity extends AppCompatActivity {

    private EditText countryEditText;
    private EditText capitalEditText;
    private Button insertButton;

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        dbHelper = new DBHelper(this);

        countryEditText = (EditText) findViewById(R.id.countryEditText);
        capitalEditText = (EditText) findViewById(R.id.capitalEditText);
        insertButton = (Button) findViewById(R.id.insertButton);



        Intent intent = getIntent();

        if (intent.getStringExtra("isInsert") != null && intent.getStringExtra("isInsert").equals("true")) {
            insertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    contentValues = new ContentValues();
                    if (countryEditText.length() > 0 && capitalEditText.length() > 0) {
                        contentValues.put("country", countryEditText.getText().toString());
                        contentValues.put("capital", capitalEditText.getText().toString());
                        sqLiteDatabase.insert("country_and_capital", null, contentValues);
                        dbHelper.close();
                        Intent mainIntent = new Intent(InsertActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }

                }
            });

        } else if(intent.getStringExtra("isUpdate") != null && intent.getStringExtra("isUpdate").equals("true")){

            final int _id = intent.getIntExtra("_id", -1);
            Log.d("id", "id: " + _id);
            sqLiteDatabase = dbHelper.getWritableDatabase();
            contentValues = new ContentValues();
            countryEditText.setText(intent.getStringExtra("country"));
            capitalEditText.setText(intent.getStringExtra("capital"));

            insertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (countryEditText.length() > 0 && capitalEditText.length() > 0) {
                        String country = countryEditText.getText().toString();
                        String capital = capitalEditText.getText().toString();
                        contentValues.put("country", country);
                        contentValues.put("capital", capital);
                        String sql = "UPDATE country_and_capital SET country = '" + country + "', capital = '" + capital + "' where _id = " + _id;
                        sqLiteDatabase.execSQL(sql);
                        dbHelper.close();
                        Intent mainIntent = new Intent(InsertActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }

                }
            });

        }


    }

}
