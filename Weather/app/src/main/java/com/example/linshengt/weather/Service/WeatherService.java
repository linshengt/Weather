package com.example.linshengt.weather.Service;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.example.linshengt.weather.Bean.Future24HWeatherBean;
import com.example.linshengt.weather.Bean.FutureWeatherBean;
import com.example.linshengt.weather.Bean.WeatherBean;
import com.example.linshengt.weather.Utils.HLog;
import com.example.linshengt.weather.Utils.NetworkUtil;
import com.linshengt.volley.Request;
import com.linshengt.volley.RequestQueue;
import com.linshengt.volley.Response;
import com.linshengt.volley.VolleyError;
import com.linshengt.volley.toolbox.JsonObjectRequest;
import com.linshengt.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by linshengt on 2016/8/2.
 */
public class WeatherService {

    private String TAG = "WeatherService";
    public WeatherBean mWeatherBean;
    private enum URL_TYPE{
        URL_INDEX,
        URL_FORECASE3H
    }
    private final int REPEAT_MSG = 0x01;
    private final int CALLBACK_OK = 0x02;
    private final int CALLBACK_ERROR = 0x04;


    public void getWeather(final Context mContext, final String city){

        mWeatherBean =new WeatherBean();
        getCityWeather(mContext, city);
        getCityWeatherForecast3h(mContext, city);

    }
    private void getCityWeatherForecast3h(final Context mContext, final String city){

        String JSONUrl = String.format("http://v.juhe.cn/weather/forecast3h.php?cityname=%s&key=c6c6be5f32db353cb3dcbe66c279458e", city);
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSONUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                HLog.i(TAG, ""+response);
                parserCityWeatherForecast3h(response, mContext);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }
    private void getCityWeather(final Context mContext, final String city){

        String JSONUrl = String.format("http://v.juhe.cn/weather/index?format=2&cityname=%s&key=c6c6be5f32db353cb3dcbe66c279458e", city);
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSONUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parserCityWeather(response, mContext);
                HLog.i(TAG, ""+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }
    private void parserCityWeather(JSONObject jsonObject, Context mContext) {

        int resultcode, errorcode;
        try {
            resultcode = jsonObject.getInt("resultcode");
            errorcode = jsonObject.getInt("error_code");
            if(errorcode == 0 && resultcode == 200){
                JSONObject resultJson = jsonObject.getJSONObject("result");

                JSONObject todayJson = resultJson.getJSONObject("today");
                mWeatherBean.setCity(todayJson.getString("city"));
                mWeatherBean.setWeather_str(todayJson.getString("weather"));
                mWeatherBean.setTempHL(todayJson.getString("temperature"));



                JSONObject skJson = resultJson.getJSONObject("sk");
                mWeatherBean.setNow_temp(skJson.getString("temp"));
                mWeatherBean.setHumdity(skJson.getString("humidity"));
                mWeatherBean.setWindDirection(skJson.getString("wind_direction"));
                mWeatherBean.setWindStrenth(skJson.getString("wind_strength"));

                JSONArray futureArray = resultJson.getJSONArray("future");
                List<FutureWeatherBean>  mFutureWeatherBeanList = new ArrayList<>();
                for (int i = 0; i < futureArray.length(); i++) {
                    JSONObject futureJson = futureArray.getJSONObject(i);

                    FutureWeatherBean mFutreWeatherBean = new FutureWeatherBean();
                    mFutreWeatherBean.setWeather_str(futureJson.getString("weather"));
                    mFutreWeatherBean.setWeekk(futureJson.getString("week"));
                    mFutreWeatherBean.setTemp(futureJson.getString("temperature"));

                    JSONObject futureWeather_id = futureJson.getJSONObject("weather_id");
                    mFutreWeatherBean.setWeather_id_fa(futureWeather_id.getString("fa"));
                    mFutreWeatherBean.setWeather_id_fb(futureWeather_id.getString("fb"));
                    mFutureWeatherBeanList.add(mFutreWeatherBean);
                }

                mWeatherBean.setFutureWeatherBeanList(mFutureWeatherBeanList);
                mHandler.sendEmptyMessage(CALLBACK_OK);
                
            }else {
                Toast.makeText(mContext, "WEATHER_ERROR", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parserCityWeatherForecast3h(JSONObject jsonObject, Context mContext){
        
        int resultcode, errorcode;
        try {
            resultcode = jsonObject.getInt("resultcode");
            errorcode = jsonObject.getInt("error_code");
            if(errorcode == 0 && resultcode == 200){

                JSONArray  jsonArray = jsonObject.getJSONArray("result");
                List<Future24HWeatherBean>  mFuture24HWeatherBeanList = new ArrayList<>();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd:HH");
                Date curData = new Date(System.currentTimeMillis());
                String str = formatter.format(curData);

                String a[] = str.split(":");
                String dd = a[0];
                String HH = a[1];

                Log.i(TAG, "jsonArray.length()=" + jsonArray.length());
                for(int i = 0, j = 0; i < jsonArray.length(); i ++, j++){

                    if(j == 9){
                        break;
                    }
                    JSONObject future24HJson = jsonArray.getJSONObject(i);


                    if(Integer.parseInt(future24HJson.getString("sh")) >= Integer.parseInt(HH)-2 || Integer.parseInt(future24HJson.getString("date")) > Integer.parseInt(dd)){

                        Future24HWeatherBean mFuture24HWeatherBean = new Future24HWeatherBean();
                        mFuture24HWeatherBean.setWeather_id(future24HJson.getString("weatherid"));
                        mFuture24HWeatherBean.setTime(Integer.parseInt(future24HJson.getString("sh")) % 24 + "");
                        mFuture24HWeatherBean.setTemp(future24HJson.getString("temp1"));
                        mFuture24HWeatherBean.setDate(future24HJson.getString("date"));
                        mFuture24HWeatherBeanList.add(mFuture24HWeatherBean);

                        Future24HWeatherBean mFuture24HWeatherBean1 = new Future24HWeatherBean();
                        mFuture24HWeatherBean1.setWeather_id(future24HJson.getString("weatherid"));
                        mFuture24HWeatherBean1.setTime((Integer.parseInt(future24HJson.getString("sh")) + 1 ) % 24 + "");
                        mFuture24HWeatherBean1.setTemp((Integer.parseInt(future24HJson.getString("temp1")) + Integer.parseInt(future24HJson.getString("temp2"))) / 2  + "");
                        mFuture24HWeatherBean1.setDate(future24HJson.getString("date"));
                        mFuture24HWeatherBeanList.add(mFuture24HWeatherBean1);

                        Future24HWeatherBean mFuture24HWeatherBean2 = new Future24HWeatherBean();
                        mFuture24HWeatherBean2.setWeather_id(future24HJson.getString("weatherid"));
                        mFuture24HWeatherBean2.setTime((Integer.parseInt(future24HJson.getString("sh")) + 2 ) % 24 + "");
                        mFuture24HWeatherBean2.setTemp(future24HJson.getString("temp2"));
                        mFuture24HWeatherBean2.setDate(future24HJson.getString("date"));
                        mFuture24HWeatherBeanList.add(mFuture24HWeatherBean2);

                    }
                }
                mWeatherBean.setFuture24HWeatherBeanList(mFuture24HWeatherBeanList);
                mHandler.sendEmptyMessage(CALLBACK_OK);

            }else {
                Toast.makeText(mContext, "WEATHER_ERROR", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private int CALLBACK_OK_TIME = 0;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REPEAT_MSG:
                    sendEmptyMessageDelayed(REPEAT_MSG, 30 * 60 * 1000);
                    break;
                case CALLBACK_OK:
                    CALLBACK_OK_TIME ++;
                    HLog.i(TAG, "CALLBACK_OK_TIME=" + CALLBACK_OK_TIME);
                     if(CALLBACK_OK_TIME == 2)
                    {
                        CALLBACK_OK_TIME = 0;
                        callBack.OnParserComplete(mWeatherBean, true);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private OnParserCallBack callBack;

    public interface OnParserCallBack {
        public void OnParserComplete(WeatherBean mWeatherBean, boolean flag);
    }
    public void setCallBack(OnParserCallBack callback) {
        this.callBack = callback;
    }

}
