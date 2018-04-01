package com.dpaulb.paul.android_sqlite_dbhelper.listView;

/**
 * Created by paul on 2018. 4. 1..
 */

public class ListItem {

    private int _id;
    private String country;
    private String capital;


    public ListItem(int _id, String country, String capital) {
        this._id = _id;
        this.country = country;
        this.capital = capital;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
