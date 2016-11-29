package com.example.linshengt.weather.Service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.example.linshengt.weather.Bean.CityBean;
import com.linshengt.volley.Request;
import com.linshengt.volley.RequestQueue;
import com.linshengt.volley.Response;
import com.linshengt.volley.VolleyError;
import com.linshengt.volley.toolbox.JsonObjectRequest;
import com.linshengt.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linshengt on 2016/8/18.
 */
public class CityService {
    private String TAG = "CityService";
    private List<CityBean>  CityList;
    private final int REPEAT_MSG = 0x01;
    private final int CALLBACK_OK = 0x02;
    private final int CALLBACK_ERROR = 0x04;
    public void getCityList(final Context mContext){

        CityList = new ArrayList<>();

        String JSONUrl = String.format("http://v.juhe.cn/weather/citys?key=c6c6be5f32db353cb3dcbe66c279458e");
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSONUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parserCityList(response, mContext);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    private void parserCityList(JSONObject jsonObject, Context mContext){
        int resultcode, errorcode;

        try {
            resultcode = jsonObject.getInt("resultcode");
            errorcode = jsonObject.getInt("error_code");

            if(resultcode == 200 && errorcode ==0){
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                Log.i(TAG, "parserCityList: jsonArray.length()=" + jsonArray.length());

                for (int i = 0; i < jsonArray.length(); i ++){
                    JSONObject cityJson = jsonArray.getJSONObject(i);

                    CityBean cityBean = new CityBean();
                    cityBean.setId(cityJson.getString("id"));
                    cityBean.setProvince(cityJson.getString("province"));
                    cityBean.setCity(cityJson.getString("city"));
                    cityBean.setDistrict(cityJson.getString("district"));

                    CityList.add(cityBean);
                }
                handler.sendEmptyMessage(CALLBACK_OK);
            }else {
                Toast.makeText(mContext, "WEATHER_ERROR", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Handler  handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CALLBACK_OK:
                    callback.onGetcityComplete(CityList);
                    break;
                case CALLBACK_ERROR:
                    break;
            }
        }
    };
    private onGetcityCallback  callback;

    public interface onGetcityCallback{
        public void onGetcityComplete(List<CityBean> CityList);
    }

    public void setCallback(onGetcityCallback callback){
        this.callback = callback;
    }
}
