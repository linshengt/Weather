package com.example.linshengt.weather.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.linshengt.weather.Bean.Future24HWeatherBean;
import com.example.linshengt.weather.Bean.FutureWeatherBean;
import com.example.linshengt.weather.Bean.WeatherBean;
import com.example.linshengt.weather.Utils.HLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linshengt on 2016/8/11.
 */
public class WeatherDao {

    private WeatherSqlHelper mWeatherSqlHelper;
    private static  String TAG = "WeatherDao";
    private Context mContext;
    private static WeatherDao mWeatherDao;


    public WeatherDao(Context context) {
        mWeatherSqlHelper = new WeatherSqlHelper(context);
    }

    public void addWeatherBean(WeatherBean weatherBean){

        SQLiteDatabase db = mWeatherSqlHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("city", weatherBean.getCity());
        cv.put("weather_str",  weatherBean.getWeather_str());
        cv.put("now_temp",  weatherBean.getNow_temp());
        cv.put("windDirection", weatherBean.getWindDirection());
        cv.put("windStrenth", weatherBean.getWindStrenth());
        cv.put("humdity", weatherBean.getHumdity());
        cv.put("tempHL", weatherBean.getTempHL());

        db.insert("TABLEWEATHER", null, cv);

        for (int i = 0; i < 7; i ++){
            addFutureWeatherBean(weatherBean.getCity(), String.valueOf(i), weatherBean.getFutureWeatherBeanList().get(i), db);
        }

        for (int i = 0; i<weatherBean.getFuture24HWeatherBeanList().size(); i ++){
            add24HFutureWeatherBean(weatherBean.getCity(),String.valueOf(i), weatherBean.getFuture24HWeatherBeanList().get(i), db);
        }
    }

    public void updateWeatherBean(WeatherBean weatherBean){

        SQLiteDatabase db = mWeatherSqlHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("city", weatherBean.getCity());
        cv.put("weather_str",  weatherBean.getWeather_str());
        cv.put("now_temp",  weatherBean.getNow_temp());
        cv.put("windDirection", weatherBean.getWindDirection());
        cv.put("windStrenth", weatherBean.getWindStrenth());
        cv.put("humdity", weatherBean.getHumdity());
        cv.put("tempHL", weatherBean.getTempHL());

        db.update("TABLEWEATHER",cv, "city=?", new String[]{weatherBean.getCity()});
        for (int i = 0; i < 7; i ++){
            updateFutureWeatherBean(weatherBean.getCity(), String.valueOf(i), weatherBean.getFutureWeatherBeanList().get(i), db);
        }

        for (int i = 0; i<weatherBean.getFuture24HWeatherBeanList().size(); i ++){
            update24HFutureWeatherBean(weatherBean.getCity(),String.valueOf(i), weatherBean.getFuture24HWeatherBeanList().get(i), db);
        }

    }

    public void deleteWeatherBean(String city){
        SQLiteDatabase db = mWeatherSqlHelper.getWritableDatabase();
        db.delete("TABLEWEATHER", "city=?", new String[]{city});
    }
    private void updateFutureWeatherBean(String city, String position, FutureWeatherBean mFutureWeatherBean, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put("city", city);
        cv.put("num", position);
        cv.put("temp", mFutureWeatherBean.getTemp());
        cv.put("weather_str", mFutureWeatherBean.getWeather_str());
        cv.put("week", mFutureWeatherBean.getWeekk());
        cv.put("weather_id_fa", mFutureWeatherBean.getWeather_id_fa());

        db.update("TABLE_FUTUREWEATHER",cv, "city=? AND num=?", new String[]{city, position});
    }
    private void addFutureWeatherBean(String city, String position, FutureWeatherBean mFutureWeatherBean, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put("city", city);
        cv.put("num", position);
        cv.put("temp", mFutureWeatherBean.getTemp());
        cv.put("weather_str", mFutureWeatherBean.getWeather_str());
        cv.put("week", mFutureWeatherBean.getWeekk());
        cv.put("weather_id_fa", mFutureWeatherBean.getWeather_id_fa());

        db.insert("TABLE_FUTUREWEATHER", null, cv);
    }

    public boolean isExists(String city){

        String cityTemp = null;

        SQLiteDatabase db=mWeatherSqlHelper.getReadableDatabase();
        String  sql = String.format("SELECT * FROM TABLEWEATHER WHERE city = '%s'", city);
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            cityTemp = c.getString(c.getColumnIndex("city"));
        }
        c.close();


        if (cityTemp == null){
            return false;
        }
        return true;
    }
    public WeatherBean getWeatherBean(String city){
        WeatherBean weatherBean = new WeatherBean();

        SQLiteDatabase db=mWeatherSqlHelper.getReadableDatabase();
        String  sql = String.format("SELECT * FROM TABLEWEATHER WHERE city = '%s'", city);
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            weatherBean.setCity(c.getString(c.getColumnIndex("city")));
            weatherBean.setWeather_str(c.getString(c.getColumnIndex("weather_str")));
            weatherBean.setNow_temp(c.getString(c.getColumnIndex("now_temp")));
            weatherBean.setTempHL(c.getString(c.getColumnIndex("tempHL")));
            weatherBean.setWindDirection(c.getString(c.getColumnIndex("windDirection")));
            weatherBean.setWindStrenth(c.getString(c.getColumnIndex("windStrenth")));
            weatherBean.setHumdity(c.getString(c.getColumnIndex("humdity")));
        }
        c.close();

        weatherBean.setFutureWeatherBeanList(getFutureBeanList(city,db));
        weatherBean.setFuture24HWeatherBeanList(get24HFutureBeanList(city,db));

        return  weatherBean;
    }

    private List<FutureWeatherBean> getFutureBeanList(String city, SQLiteDatabase db){
        List<FutureWeatherBean> mFutureWeatherBeanList = new ArrayList<>();

        for (int i = 0; i < 7; i ++){
            FutureWeatherBean mFutureWeatherBean = new FutureWeatherBean();
            String  sql = String.format("SELECT * FROM TABLE_FUTUREWEATHER WHERE city='%s' AND num='%s'", city, String.valueOf(i));

            Cursor c = db.rawQuery(sql, null);

            while(c.moveToNext()){
                mFutureWeatherBean.setTemp(c.getString(c.getColumnIndex("temp")));
                mFutureWeatherBean.setWeather_id_fa(c.getString(c.getColumnIndex("weather_id_fa")));
                mFutureWeatherBean.setWeather_str(c.getString(c.getColumnIndex("weather_str")));
                mFutureWeatherBean.setWeekk(c.getString(c.getColumnIndex("week")));
                mFutureWeatherBeanList.add(i, mFutureWeatherBean);
            }
        }

        return  mFutureWeatherBeanList;
    }

    private void add24HFutureWeatherBean(String city , String position, Future24HWeatherBean mFuture24HWeatherBean, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put("city", city);
        cv.put("num", position);
        cv.put("weather_id", mFuture24HWeatherBean.getWeather_id());
        cv.put("weather", mFuture24HWeatherBean.getWeather());
        cv.put("temp", mFuture24HWeatherBean.getTemp());
        cv.put("time", mFuture24HWeatherBean.getTime());
        cv.put("date", mFuture24HWeatherBean.getDate());

        db.insert("TABLE_24HFUTURE", null, cv);

    }
    private void update24HFutureWeatherBean(String city ,String position, Future24HWeatherBean mFuture24HWeatherBean, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put("city", city);
        cv.put("num", position);
        cv.put("weather_id", mFuture24HWeatherBean.getWeather_id());
        cv.put("weather", mFuture24HWeatherBean.getWeather());
        cv.put("temp", mFuture24HWeatherBean.getTemp());
        cv.put("time", mFuture24HWeatherBean.getTime());
        cv.put("date", mFuture24HWeatherBean.getDate());

        db.insert("TABLE_24HFUTURE", null, cv);
        db.update("TABLE_24HFUTURE",cv, "city=? AND num=?", new String[]{city, position});

    }
    public List<Future24HWeatherBean> get24HFutureBeanList(String city, SQLiteDatabase db){
        List<Future24HWeatherBean>  mFuture24HWeatherBeanList = new ArrayList<>();

        for(int i = 0; i < 15; i ++){
            Future24HWeatherBean m24HFutureBean = new Future24HWeatherBean();

            String sql = String.format("SELECT * FROM TABLE_24HFUTURE WHERE city='%s' AND num='%s'", city, String.valueOf(i));
            Cursor c = db.rawQuery(sql, null);

            while (c.moveToNext()){
                m24HFutureBean.setTemp(c.getString(c.getColumnIndex("temp")));
                m24HFutureBean.setWeather_id(c.getString(c.getColumnIndex("weather_id")));
                m24HFutureBean.setTime(c.getString(c.getColumnIndex("time")));
                m24HFutureBean.setDate(c.getString(c.getColumnIndex("date")));
            }
            c.close();

            mFuture24HWeatherBeanList.add(i, m24HFutureBean);

        }

        return mFuture24HWeatherBeanList;
    }

    public List<String> getCityList(){
        List<String> list = new ArrayList<>();
        SQLiteDatabase db=mWeatherSqlHelper.getReadableDatabase();
        String  sql = String.format("SELECT * FROM TABLEWEATHER");
        Cursor c = db.rawQuery(sql, null);


        while (c.moveToNext()){
           list.add(c.getString(c.getColumnIndex("city")));
        }
        c.close();

        return list;
    }

    public void deleteCity(String city){
        SQLiteDatabase db=mWeatherSqlHelper.getReadableDatabase();

        db.delete("TABLEWEATHER", "city=?", new String[]{city});
        db.delete("TABLE_FUTUREWEATHER", "city=?", new String[]{city});
        db.delete("TABLE_24HFUTURE", "city=?", new String[]{city});
    }
}
