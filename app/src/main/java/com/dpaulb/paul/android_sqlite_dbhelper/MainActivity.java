package com.dpaulb.paul.android_sqlite_dbhelper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dpaulb.paul.android_sqlite_dbhelper.listView.ListItem;
import com.dpaulb.paul.android_sqlite_dbhelper.listView.ListViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final int RQCODE_INSERT = 1;
    public static final int RQCODE_UPDATE = 2;
    public static final int RQCODE_DELETE = 3;

    private static final int MENU_INSERT = Menu.FIRST;
    private static final int MENU_UPDATE = Menu.FIRST + 1;
    private static final int MENU_DELETE = Menu.FIRST + 2;
    private static final int MENU_READ = Menu.FIRST + 3;
    private static final int MENU_DELETE_ALL = Menu.FIRST + 4;

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    private String[] projection = {"_id", "country", "capital"};


    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private ArrayList<ListItem> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        listView = (ListView) findViewById(R.id.listView);

        registerForContextMenu(listView);

        readData();

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, MENU_UPDATE, 0, "데이터 수정하기").setIcon(R.drawable.ic_launcher_foreground);
        menu.add(0, MENU_DELETE, 1, "데이터 삭제하기").setIcon(R.drawable.ic_launcher_foreground);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU_UPDATE:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                intent.putExtra("isUpdate", "true");
                intent.putExtra("_id", arrayList.get(info.position).get_id());
                intent.putExtra("capital", arrayList.get(info.position).getCapital());
                intent.putExtra("country", arrayList.get(info.position).getCountry());
                startActivity(intent);
                return true;
            case MENU_DELETE:
                AdapterView.AdapterContextMenuInfo deleteInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int _id = arrayList.get(deleteInfo.position).get_id();
                delete(_id);
                readData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, MENU_INSERT, 0, "데이터 저장하기").setIcon(R.drawable.ic_launcher_background);
        menu.add(0, MENU_READ, 1, "데이터 리스트 보기").setIcon(R.drawable.ic_launcher_background);
        menu.add(0, MENU_DELETE_ALL, 2, "데이터 전체 삭제").setIcon(R.drawable.ic_launcher_background);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_INSERT:
                Intent insertIntent = new Intent(MainActivity.this, InsertActivity.class);
                insertIntent.putExtra("isInsert", "true");
                startActivity(insertIntent);
                return true;
            case MENU_READ:
                readData();
                return true;
            case MENU_DELETE_ALL:
                deleteAll();
                readData();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void readData() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query("country_and_capital", projection, null, null, null, null, null);
        if (cursor != null) {
            int idColumn = cursor.getColumnIndex("_id");
            int countryColumn = cursor.getColumnIndex("country");
            int capitalColumn = cursor.getColumnIndex("capital");
            arrayList = new ArrayList<>();
            while(cursor.moveToNext()){
                int _id = cursor.getInt(idColumn);
                String country = cursor.getString(countryColumn);
                String capital = cursor.getString(capitalColumn);
                arrayList.add(new ListItem(_id, country, capital));

            }

            listViewAdapter = new ListViewAdapter(MainActivity.this, arrayList);
            listView.setAdapter(listViewAdapter);

        }

    }

    public void deleteAll(){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete("country_and_capital", null, null);
        dbHelper.close();

    }

    public void delete(int _id) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete("country_and_capital", "_id = ?", new String[] {_id + ""});
    }
}
