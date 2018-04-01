package com.dpaulb.paul.android_sqlite_dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by paul on 2018. 4. 1..
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "CountryAndNation.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE country_and_capital(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "country TEXT, capital TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS country_and_capital;");
        onCreate(sqLiteDatabase);
    }
}
