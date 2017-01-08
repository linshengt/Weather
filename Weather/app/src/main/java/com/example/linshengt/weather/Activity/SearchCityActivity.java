package com.example.linshengt.weather.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.linshengt.weather.Adapter.CityAdapter;
import com.example.linshengt.weather.Adapter.LetterAdapter;
import com.example.linshengt.weather.Bean.CityBean;
import com.example.linshengt.weather.Dao.CityDao;
import com.example.linshengt.weather.R;
import com.example.linshengt.weather.Utils.HLog;
import com.example.linshengt.weather.Utils.PreferenceUtil;
import com.example.linshengt.weather.Utils.StringUtil;
import com.example.linshengt.weather.Views.CustomSearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchCityActivity extends AppCompatActivity {
    private String TAG = "SearchCityActivity";
    private CustomSearchView mCustomSearch;
    private List<String> dbData;
    private List<String> dbDataLetter = new ArrayList<>();
    private List<String> autoCompleteData;
    private static int DEFAULT_HINT_SIZE = 4;
    private static int hintSize = DEFAULT_HINT_SIZE;
    private ArrayAdapter<String> autoCompleteAdapter;
    private CityDao mCityDao;
    private Context mContext;
    private List<String> letterList = new ArrayList<>();
    private List<String> dbDataShow = new ArrayList<>();
    private String[] letter={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    List<String> letterToCity=new ArrayList<String>();
    private RecyclerView mRecyclerViewCity;
    private RecyclerView mRecyclerViewLetter;
    private LetterAdapter mLetterAdapter;
    private CityAdapter mCityAdapter;
    private RelativeLayout rl;
    private LinearLayoutManager linearLayoutManager;
    private ScrollSpeedLinearLayoutManger linearLayoutManger1;
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
        mRecyclerViewCity = (RecyclerView) findViewById(R.id.recyclerViewCiy);
        mRecyclerViewLetter = (RecyclerView) findViewById(R.id.recyclerViewLetter);
        rl = (RelativeLayout) findViewById(R.id.cityList);
    }

    private void initData(){
        mCityDao = new CityDao(mContext);
        getDbData();
        getAutoCompleteData(null);

        for (int i = 0; i < letter.length; i ++){
            letterList.add(letter[i]);
        }

        String str="";
        for(int i=0;i<letter.length;i++){
            str=letter[i].toLowerCase();
            boolean isAddLetter=false;
            for(int j=0;j<dbDataLetter.size();j++){
                if(str.equals(dbDataLetter.get(j))){
                    if(!isAddLetter){
                        letterToCity.add(str);
                        isAddLetter=true;
                    }
                      letterToCity.add(dbDataShow.get(j));
                }
            }
        }
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

            @Override
            public void onEditext(boolean isEmpty) {
                if (isEmpty == true){
                    rl.setVisibility(View.VISIBLE);
                }else {
                    rl.setVisibility(View.INVISIBLE);
                }
            }
        });
        mCustomSearch.setmAutoCompleteAdapter(autoCompleteAdapter);


        linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerViewLetter.setLayoutManager(linearLayoutManager);
        mLetterAdapter = new LetterAdapter(letterList);
        mRecyclerViewLetter.setAdapter(mLetterAdapter);

        mLetterAdapter.setmLetterListener(new LetterAdapter.LetterListener() {
            @Override
            public void onItemClick(int position) {
                String letter1 = letterList.get(position).toLowerCase();
                HLog.i(TAG, letter1);
                boolean isExit = false;
                int movePosition = 0;
                for (int i = 0; i < letterToCity.size(); i ++){

                    String letter2 = StringUtil.getSpells(letterToCity.get(i).substring(0,1));

                    if(letter2.equals(letter1)){
                        HLog.i(TAG, letter2);
                        movePosition = i;
                        isExit = true;
                        break;
                    }
                }
                if (isExit == true)
                    mRecyclerViewCity.smoothScrollToPosition(movePosition);
            }
        });


        linearLayoutManger1 = new ScrollSpeedLinearLayoutManger(mContext);
        mRecyclerViewCity.setLayoutManager(linearLayoutManger1);
        mCityAdapter = new CityAdapter(letterToCity, dbDataLetter);
        mRecyclerViewCity.setAdapter(mCityAdapter);
        mRecyclerViewCity.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mCityAdapter.setmCityRecyclerListener(new CityAdapter.CityRecyclerListener() {
            @Override
            public void onItemClick(int position) {
               PreferenceUtil.write(mContext, "cityId", mCityDao.GetCityID(letterToCity.get(position)));
                finish();
            }
        });
    }


    private void getDbData() {
        dbData = new ArrayList<>();
        List<CityBean> cityList;
        CityDao mCityDao = new CityDao(mContext);
        cityList = mCityDao.GetCityList();
        for(int i = 0; i < cityList.size(); i ++ ){
            dbData.add(cityList.get(i).getDistrict());
            dbDataShow.add(cityList.get(i).getCity());
        }

        removeDuplicate(dbDataShow);
        for(int i = 0; i <dbDataShow.size(); i ++){
            dbDataLetter.add(StringUtil.getSpells(dbDataShow.get(i).substring(0,1)));
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
    public static void removeDuplicate(List list) {
        for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {
            for ( int j = list.size() - 1 ; j > i; j -- ) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
    }

    public class ScrollSpeedLinearLayoutManger extends LinearLayoutManager {
        private float MILLISECONDS_PER_INCH = 0.02f;
        private Context contxt;

        public ScrollSpeedLinearLayoutManger(Context context) {
            super(context);
            this.contxt = context;
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        @Override
                        public PointF computeScrollVectorForPosition(int targetPosition) {
                            return ScrollSpeedLinearLayoutManger.this
                                    .computeScrollVectorForPosition(targetPosition);
                        }

                        //This returns the milliseconds it takes to
                        //scroll one pixel.
                        @Override
                        protected float calculateSpeedPerPixel
                        (DisplayMetrics displayMetrics) {
                            return MILLISECONDS_PER_INCH / displayMetrics.density;
                            //返回滑动一个pixel需要多少毫秒
                        }

                    };
            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }


        public void setSpeedSlow() {
            //自己在这里用density去乘，希望不同分辨率设备上滑动速度相同
            //0.3f是自己估摸的一个值，可以根据不同需求自己修改
            MILLISECONDS_PER_INCH = contxt.getResources().getDisplayMetrics().density * 0.3f;
        }

        public void setSpeedFast() {
            MILLISECONDS_PER_INCH = contxt.getResources().getDisplayMetrics().density * 0.03f;
        }
    }
}
