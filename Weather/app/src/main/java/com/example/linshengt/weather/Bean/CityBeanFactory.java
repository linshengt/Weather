package com.example.linshengt.weather.Bean;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 94437 on 2017/1/4.
 */
public class CityBeanFactory {
    public static CityBean createSqlCityBean(Cursor c ){
        return new CityBean.CityBeanBuilder().setId(c.getString(c.getColumnIndex("id")))
                .setCity(c.getString(c.getColumnIndex("city")))
                .setProvince(c.getString(c.getColumnIndex("province")))
                .setDistrict(c.getString(c.getColumnIndex("district")))
                .bulid();
    }

    public static CityBean createJsonCityBean(JSONObject cityJson){
        try {
            return new CityBean.CityBeanBuilder().setId(cityJson.getString("id"))
                    .setCity(cityJson.getString("province"))
                    .setProvince(cityJson.getString("city"))
                    .setDistrict(cityJson.getString("district"))
                    .bulid();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }
}
