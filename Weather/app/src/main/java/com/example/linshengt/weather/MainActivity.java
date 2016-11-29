package com.example.linshengt.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.linshengt.weather.Activity.SearchCityActivity;
import com.example.linshengt.weather.Bean.Future24HWeatherBean;
import com.example.linshengt.weather.Bean.FutureWeatherBean;
import com.example.linshengt.weather.Bean.WeatherBean;
import com.example.linshengt.weather.Dao.CityDao;
import com.example.linshengt.weather.Dao.WeatherDao;
import com.example.linshengt.weather.Service.WeatherService;
import com.example.linshengt.weather.Utils.DataUtil;
import com.example.linshengt.weather.Utils.HLog;
import com.example.linshengt.weather.Utils.PreferenceUtil;
import com.example.linshengt.weather.Views.RecyclerView.RecyclerViewAdapter;
import com.example.linshengt.weather.Views.RecyclerView.RecyclerViewHAdapter;
import com.example.linshengt.weather.Views.TitleBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends Activity {

    private String TAG = "MainActivity";
    private PtrFrameLayout  mPtrFrameLayout;
    private RecyclerView mRecyclerView;
    private SlidingMenu mSlidingMenu;
    private TitleBar mTitleBar;
    private WeatherService mWeatherService;
    private Context mContext;
    private List<FutureWeatherBean>  mListFutureBean;
    private List<Future24HWeatherBean> mListFuture24HBean;
    private RecyclerViewAdapter mRecyclerAdapter;
    private RecyclerViewHAdapter mRecyclerHAdapter;
    private RecyclerView mRecyclerViewH;
    private TextView tvDate, tvTemp,tvHLTemp,tvWind,tvHumdity, tvWeather;
    private WeatherDao mWeatherDao;
    private String mCityId;
    private String mCity;
    private ScrollView mScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);


        findView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        CityDao cityDao = new CityDao(mContext);
        mCityId = PreferenceUtil.readString(mContext, "cityId");
        mCity = cityDao.GetCity(mCityId);

        HLog.i(TAG, "onResume-->ID:" + mCityId);
        HLog.i(TAG, "onResume-->mCity:" + mCity);
        mCity = "上海";
        initData();
        initView();
    }

    private void findView(){
        mTitleBar = (TitleBar) findViewById(R.id.titleBar);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_frame);
        mScrollView = (ScrollView) findViewById(R.id.scrollview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerViewH = (RecyclerView) findViewById(R.id.recyclerViewhour);

        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTemp = (TextView) findViewById(R.id.tvTemperature);
        tvHLTemp = (TextView) findViewById(R.id.tvHLTemperature);
        tvWind = (TextView) findViewById(R.id.tvWind);
        tvHumdity = (TextView) findViewById(R.id.tvHumdity);
        tvWeather = (TextView) findViewById(R.id.tvWeather);

    }

    private void setView(WeatherBean weatherBean){

        tvTemp.setText(weatherBean.getNow_temp()+"°");
        tvHLTemp.setText(weatherBean.getTempHL());
        tvWind.setText(weatherBean.getWindDirection()+ "\n" + weatherBean.getWindStrenth());
        tvHumdity.setText("湿度\n"+weatherBean.getHumdity());
        tvWeather.setText(weatherBean.getWeather_str());

        mListFutureBean.clear();
        mListFutureBean.addAll(weatherBean.getFutureWeatherBeanList());
        mRecyclerAdapter.notifyItemRangeChanged(0, mListFutureBean.size()-1);

        mListFuture24HBean.clear();
        mListFuture24HBean.addAll(weatherBean.getFuture24HWeatherBeanList());
        mRecyclerHAdapter.notifyItemRangeChanged(0, mListFuture24HBean.size()-1);

    }

    private void initData(){

        mWeatherDao = new WeatherDao(mContext);
        mListFutureBean = new ArrayList<>();
        mListFuture24HBean = new ArrayList<>();
        mWeatherService = new WeatherService();
        mWeatherService.setCallBack(new WeatherService.OnParserCallBack(){

            @Override
            public void OnParserComplete(WeatherBean mWeatherBean, boolean flag) {
                setView(mWeatherBean);
                if(mWeatherDao.isExists(mWeatherBean.getCity())==true){
                    mWeatherDao.updateWeatherBean(mWeatherBean);
                }else {
                    mWeatherDao.addWeatherBean(mWeatherBean);
                }
                PreferenceUtil.write(mContext, mCity, true);
                mPtrFrameLayout.refreshComplete();
            }
        });
    }

    private void initView(){
        mTitleBar.setCommonTitle(View.VISIBLE);
        mTitleBar.setTitleBarTile(mCity);

        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean onRefreshBegin(PtrFrameLayout frame) {
                mWeatherService.getWeather(mContext, DataUtil.gbk2UTF8URL(mCity));
                return true;
            }

        });

        mScrollView.smoothScrollTo(0, 0);
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(true);
            }
        }, 1000);

        mRecyclerAdapter = new RecyclerViewAdapter(mListFutureBean);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mPtrFrameLayout.disableWhenHorizontalMove(true);
            }
        });

        mRecyclerHAdapter = new RecyclerViewHAdapter(mListFuture24HBean);
        mRecyclerViewH.setAdapter(mRecyclerHAdapter);
        LinearLayoutManager linearLayoutManagerH = new LinearLayoutManager(mContext);
        linearLayoutManagerH.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewH.setLayoutManager(linearLayoutManagerH);

        mRecyclerViewH.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mPtrFrameLayout.disableWhenHorizontalMove(true);
            }
        });


        if(PreferenceUtil.readBoolean(mContext, mCity) == true){
             WeatherBean mWeatherBean;
             mWeatherBean = mWeatherDao.getWeatherBean(mCity);
             setView(mWeatherBean);
        }
        /*


        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true)


         */
        mPtrFrameLayout.autoRefresh(true);
          initSlidingMenu();
    }

    private void initSlidingMenu(){
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mSlidingMenu.setMenu(R.layout.slidingmenu);


        mSlidingMenu.findViewById(R.id.rlCityChoice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchCityActivity.class);
                startActivity(intent);
            }
        });

    }

}
