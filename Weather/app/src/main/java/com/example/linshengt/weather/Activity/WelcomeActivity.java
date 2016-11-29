package com.example.linshengt.weather.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.linshengt.weather.Bean.CityBean;
import com.example.linshengt.weather.Dao.CityDao;
import com.example.linshengt.weather.MainActivity;
import com.example.linshengt.weather.R;
import com.example.linshengt.weather.Service.CityService;
import com.example.linshengt.weather.Utils.HLog;
import com.example.linshengt.weather.Utils.PreferenceUtil;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private String TAG = "WelcomeActivity";
    private TextView tvProgress;
    private CityService mCityService;
    private Context mContext;
    private CityDao mCityDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (PreferenceUtil.readBoolean(this, "isfirstEnter") == false){
            initCityList();
            PreferenceUtil.write(this, "isfirstEnter", true);
        }else {
            Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }
    private void initCityList(){
        mContext = this;
        mCityDao = new CityDao(mContext);
        mCityService = new CityService();
        mCityService.setCallback(new CityService.onGetcityCallback() {
            @Override
            public void onGetcityComplete(List<CityBean> CityList) {
                mCityDao.SetCityList(CityList);
                Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        mCityService.getCityList(mContext);
    }
}
