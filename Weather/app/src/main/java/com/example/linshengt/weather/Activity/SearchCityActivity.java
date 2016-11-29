package com.example.linshengt.weather.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.linshengt.weather.Bean.CityBean;
import com.example.linshengt.weather.Dao.CityDao;
import com.example.linshengt.weather.R;
import com.example.linshengt.weather.Utils.HLog;
import com.example.linshengt.weather.Utils.PreferenceUtil;
import com.example.linshengt.weather.Views.CustomSearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchCityActivity extends AppCompatActivity {
    private String TAG = "SearchCityActivity";
    private CustomSearchView mCustomSearch;
    private List<String> dbData;
    private List<String> autoCompleteData;
    private static int DEFAULT_HINT_SIZE = 4;
    private static int hintSize = DEFAULT_HINT_SIZE;
    private ArrayAdapter<String> autoCompleteAdapter;
    private CityDao mCityDao;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        mContext = this;
        findView();
        initData();
        initView();
    }
    private void findView(){
        mCustomSearch = (CustomSearchView) findViewById(R.id.searchView);

    }

    private void initData(){
        mCityDao = new CityDao(mContext);
        getDbData();
        getAutoCompleteData(null);
    }

    private void initView(){

        mCustomSearch.setmSearchViewListener(new CustomSearchView.SearchViewListener() {
            @Override
            public void onRefreshAutoComplete(String text) {
                getAutoCompleteData(text);
            }

            @Override
            public void onSearch(String text) {
                PreferenceUtil.write(mContext, "cityId", mCityDao.GetCityID(text));
                finish();
            }
        });
        mCustomSearch.setmAutoCompleteAdapter(autoCompleteAdapter);

    }


    private void getDbData() {
        dbData = new ArrayList<>();
        List<CityBean> cityList;
        CityDao mCityDao = new CityDao(mContext);
        cityList = mCityDao.GetCityList();
        for(int i = 0; i < cityList.size(); i ++ ){
            dbData.add(cityList.get(i).getDistrict());
        }
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).contains(text.trim())) {
                    autoCompleteData.add(dbData.get(i));
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }
}
