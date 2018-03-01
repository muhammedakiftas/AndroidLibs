package com.sinifdefterim.widget;

import android.util.Log;
import android.widget.Toast;

public class ListItem {

    public String id;
    public String name;
    public String date;
    public String update_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUpdate_date() {

        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }
}