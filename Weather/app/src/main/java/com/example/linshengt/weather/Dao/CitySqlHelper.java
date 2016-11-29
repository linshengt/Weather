package com.example.linshengt.weather.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by linshengt on 2016/8/20.
 */
public class CitySqlHelper extends SQLiteOpenHelper {

    public CitySqlHelper(Context context) {
        super(context, "city.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TABLE_CITYLIST ( id INTEGER PRIMARY KEY, province VACHAR, city VACHAR, district VACHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
