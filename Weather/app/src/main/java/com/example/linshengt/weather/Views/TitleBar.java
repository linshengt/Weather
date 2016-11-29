package com.example.linshengt.weather.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.linshengt.weather.R;


/**
 * Created by linshengt on 2016/9/6.
 */
public class TitleBar extends RelativeLayout {
    private Context mContext;
    private TextView tv_title;
    private TextView tv_left;
    private ImageView imageViewLeft;
    private ImageView imageViewRight;
    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        findView();
    }

    private void findView(){
        LayoutInflater.from(mContext).inflate(R.layout.titlebar_layout, this);
        tv_title = (TextView) findViewById(R.id.tv_titleBar_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        imageViewLeft = (ImageView) findViewById(R.id.image_left);
        imageViewRight = (ImageView) findViewById(R.id.image_right);
    }
    public void setTitleBarTile(String Title){
        tv_title.setText(Title);
    }
    public void setTv_left(String str){
        tv_left.setText(str);
    }
    public void setOnTv_leftClickListener(OnClickListener onTv_leftClickListener){
        tv_left.setOnClickListener(onTv_leftClickListener);
    }
    public void setImageLeftClickListener(OnClickListener onImageClickListener){
        imageViewLeft.setOnClickListener(onImageClickListener);
    }

    public void setCommonTitle(int TitleVisibility){
        tv_title.setVisibility(TitleVisibility);
    }

    public void setCommonTitle(int TitleVisibility, int LeftTextVisibility, int LeftImageVisibility, int RightImageVisibility){

        tv_left.setVisibility(LeftTextVisibility);
        imageViewLeft.setVisibility(LeftImageVisibility);
        imageViewRight.setVisibility(RightImageVisibility);
    }

    public void setImage_rightClickListener(OnClickListener onClickListener){
        imageViewRight.setOnClickListener(onClickListener);
    }

}
