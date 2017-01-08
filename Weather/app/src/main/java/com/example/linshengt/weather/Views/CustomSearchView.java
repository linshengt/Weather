package com.example.linshengt.weather.Views;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.linshengt.weather.R;

/**
 * Created by 94437 on 2016/11/9.
 */
public class CustomSearchView extends LinearLayout implements View.OnClickListener {

    private String TAG = "CustomSearchView";
    private ImageView imageViewBack, imageViewDelete;
    private EditText editText;
    private ListView listView;
    private Context context;
    private ArrayAdapter<String> mAutoCompleteAdapter;

    public CustomSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.customsearchview, this);
        imageViewBack = (ImageView) findViewById(R.id.search_iv_back);
        imageViewDelete = (ImageView) findViewById(R.id.search_clear_content_iv);
        editText = (EditText) findViewById(R.id.et_search_content);
        listView = (ListView) findViewById(R.id.search_lv_tips);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //set edit text
                String text = listView.getAdapter().getItem(i).toString();
                editText.setText(text);
                editText.setSelection(text.length());
                //hint list view gone and result list view show
                listView.setVisibility(View.GONE);
                notifyStartSearching(text);
            }
        });


        imageViewBack.setOnClickListener(this);
        imageViewDelete.setOnClickListener(this);

        editText.addTextChangedListener(new EditChangedListener());
    }
    /**
     * 通知监听者 进行搜索操作
     * @param text
     */
    private void notifyStartSearching(String text){
        if (mSearchViewListener != null) {
            mSearchViewListener.onSearch(editText.getText().toString());
        }
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public class EditChangedListener implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if("".equals(charSequence.toString())){
                imageViewDelete.setVisibility(INVISIBLE);
                listView.setVisibility(GONE);
                mSearchViewListener.onEditext(true);
            }else {
                imageViewDelete.setVisibility(VISIBLE);
                mSearchViewListener.onRefreshAutoComplete(""+charSequence);
                listView.setVisibility(VISIBLE);
                listView.setAdapter(mAutoCompleteAdapter);
                mSearchViewListener.onEditext(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_iv_back:
                ((Activity) context).finish();
                break;
            case R.id.search_clear_content_iv:
                editText.setText("");
                listView.setVisibility(GONE);
                break;
            default:
                break;
        }
    }

    public void setmSearchViewListener(SearchViewListener mSearchViewListener) {
        this.mSearchViewListener = mSearchViewListener;
    }

    public void setmAutoCompleteAdapter(ArrayAdapter<String> mAutoCompleteAdapter) {
        this.mAutoCompleteAdapter = mAutoCompleteAdapter;
    }

    private SearchViewListener mSearchViewListener;
    /**
     * search view回调方法
     */
    public interface SearchViewListener {

        /**
         * 更新自动补全内容
         *
         * @param text 传入补全后的文本
         */
        void onRefreshAutoComplete(String text);

        /**
         * 开始搜索
         *
         * @param text 传入输入框的文本
         */
        void onSearch(String text);

        void onEditext(boolean isEmpty);
    }
}
