package com.dpaulb.paul.android_sqlite_dbhelper.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dpaulb.paul.android_sqlite_dbhelper.R;

import java.util.ArrayList;

/**
 * Created by paul on 2018. 4. 1..
 */

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListItem> arrayList;

    public ListViewAdapter(Context context, ArrayList<ListItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_item, null);

            TextView countryTextView = (TextView) view.findViewById(R.id.countryTextView);
            TextView capitalTextView = (TextView) view.findViewById(R.id.capitalTextView);

            countryTextView.setText(arrayList.get(i).getCountry());
            capitalTextView.setText(arrayList.get(i).getCapital());


        }
        return view;
    }
}
