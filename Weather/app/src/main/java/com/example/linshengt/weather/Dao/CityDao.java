package com.example.linshengt.weather.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.linshengt.weather.Bean.CityBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linshengt on 2016/8/20.
 */
public class CityDao {

    private String TAG = "CityDao";

    private CitySqlHelper mCitySqlHelper;
    public CityDao(Context context) {
        mCitySqlHelper = new CitySqlHelper(context);
    }

    public void SetCityList(List<CityBean> list){
        SQLiteDatabase db = mCitySqlHelper.getWritableDatabase();

        for (int i = 0; i < list.size(); i ++){
            ContentValues cv = new ContentValues();

            cv.put("id", list.get(i).getId());
            cv.put("province", list.get(i).getProvince());
            cv.put("city", list.get(i).getCity());
            cv.put("district", list.get(i).getDistrict());

            db.insert("TABLE_CITYLIST", null, cv);
        }
    }
    public List<CityBean> GetCityList(){
        List<CityBean> list = new ArrayList<>();

        SQLiteDatabase db = mCitySqlHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM TABLE_CITYLIST", null);

        while (c.moveToNext()){
            CityBean cityBean = new CityBean();
            cityBean.setId(c.getString(c.getColumnIndex("id")));
            cityBean.setCity(c.getString(c.getColumnIndex("city")));
            cityBean.setProvince(c.getString(c.getColumnIndex("province")));
            cityBean.setDistrict(c.getString(c.getColumnIndex("district")));
            list.add(cityBean);
        }

        c.close();
        return list;
    }

    public String GetCityID(String district){
        String cityID = "";
        String sql = String.format("SELECT * FROM TABLE_CITYLIST WHERE district='%s'", district);

        SQLiteDatabase db = mCitySqlHelper.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            cityID = c.getString(c.getColumnIndex("id"));
        }
        return cityID;
    }
    public String GetCity(String id){
        String district = "";
        String sql = String.format("SELECT * FROM TABLE_CITYLIST WHERE id='%s'", id);

        SQLiteDatabase db = mCitySqlHelper.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            district = c.getString(c.getColumnIndex("district"));
        }
        return district;
    }
}
