package com.example.linshengt.weather.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by linshengt on 2016/8/11.
 */
public class WeatherSqlHelper extends SQLiteOpenHelper {
    public WeatherSqlHelper(Context context) {
        super(context, "weather.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TABLEWEATHER ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "city VARCHAR, " +
                "weather_str VARCHAR, " +
                "now_temp VARCHAR, " +
                "windDirection VARCHAR, " +
                "windStrenth VARCHAR, " +
                "humdity VARCHAR, " +
                "tempHL VARCHAR" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS TABLE_FUTUREWEATHER ( " +
                "id_future INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "city VARCHAR, " +
                "temp VARCHAR, " +
                "num, " +
                "weather_str VARCHAR, " +
                "week VARCHAR, " +
                "weather_id_fa VARCHAR" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS TABLE_24HFUTURE ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "city VARCHAR, " +
                "num, " +
                "weather_id VARCHAR, " +
                "weather VARCHAR, " +
                "temp VARCHAR, " +
                "time VARCHAR, " +
                "date VARCHAR" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
